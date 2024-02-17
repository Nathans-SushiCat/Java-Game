package entity;

import Main.*;
import World.KeyButton;
import World.TextField;

import java.awt.*;
import java.util.ArrayList;

public class Keyboard extends Entity {
        KeyButton[] keys = new KeyButton[27];
        String[][] rowStrings = new String[3][10];
        KeyHandler keyH;
        TextField textField;

        public Keyboard(GamePanel gp, KeyHandler keyH, int x, int y, TextField textField) {
                this.textField = textField;
                this.x = x;
                this.y = y;
                this.gp = gp;
                this.keyH = keyH;
                solid = false;
                setupKeyboard();
        }

        public void setupKeyboard() {
                String[] row1 = new String[]{"Q", "W", "E", "R", "T", "Z", "U", "I", "O", "P"};
                String[] row2 = new String[]{"A", "S", "D", "F", "G", "H", "J", "K", "L"};
                String[] row3 = new String[]{"Y", "X", "C", "V", "B", "N", "M"};

                rowStrings[0] = row1;
                rowStrings[1] = row2;
                rowStrings[2] = row3;

                int dist = GamePanel.scale * 20;

                int index = 0;
                for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < rowStrings[i].length; j++) {
                                if (rowStrings[i][j] != null) {
                                        keys[index++] = new KeyButton(gp, keyH, x + (i * 3 * GamePanel.scale) + dist * j, y + dist * i, rowStrings[i][j], textField);
                                } else {
                                        i++;
                                }
                        }
                }

                keys[26] = (new KeyButton(gp, keyH, x + (3 * GamePanel.scale) + dist * 9, y + dist, "←", textField));
        }

        @Override
        public void update() {
                for (int i = 0; i < keys.length; i++)
                        keys[i].update();

                ArrayList<Player> players = new ArrayList<>();
                for (Entity entity : gp.entities) {
                        if (entity instanceof Player)
                                players.add((Player) entity);
                }


                // Detect collisions and handle them
                for (int j = 0; j < players.size(); j++) {
                        Player currentPlayer = players.get(j);
                        for (int i = 0; i < keys.length; i++) {
                                Collision collision = keys[i].createCollisionWith(currentPlayer);
                                currentPlayer.collisions.add(collision);
                                if (collision.collided) {
                                        keys[i].handleCollision(collision);
                                }
                        }

                }
        }

        @Override
        public void draw(Graphics2D g2) {
                g2.setFont(gp.boldFont);

                for(int i = 0; i < keys.length; i++)
                        keys[i].draw(g2);

                g2.setFont(gp.mainFont);

        }

}