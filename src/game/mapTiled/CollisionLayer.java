package game.mapTiled;

import java.awt.*;

public class CollisionLayer extends TileLayer{

    private Rectangle[] collisions;

    public CollisionLayer(int width, int height, int x, int y, Rectangle[] collisions) {
        super(width, height, x, y);
        this.collisions = collisions;
    }

    public Rectangle[] getCollisions() {
        return collisions;
    }
}
