package game.tileMap.Block;

import game.tools.AABB;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NormBlock extends Block {

    public NormBlock(BufferedImage img, Point pos, int w, int h) {
        super(img, pos, w, h);
    }

    @Override
    public boolean update(AABB p) {
        return false;
    }

    public void render(Graphics2D g) {
        super.render(g);
        g.setColor(Color.magenta);
        g.drawRect(pos.x, pos.y, w, h);
    }

}
