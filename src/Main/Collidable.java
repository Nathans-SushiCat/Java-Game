package Main;

public interface Collidable {
    Collision createCollisionWith(Collidable other);
    void handleCollision(Collision collision);
}