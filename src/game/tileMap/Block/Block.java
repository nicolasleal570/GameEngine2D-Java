package game.tileMap.Block;

import game.tools.AABB;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Block {

    protected int w, h;

    protected BufferedImage img;
    protected Point pos;

    public Block(BufferedImage img, Point pos,int w, int h) {
        this.img = img;
        this.pos = pos;
        this.w = w;
        this.h = h;
    }

    public abstract boolean update(AABB p);

    public void render(Graphics2D g) {
        g.drawImage(img, pos.x, pos.y, w, h, null);
    }

}
