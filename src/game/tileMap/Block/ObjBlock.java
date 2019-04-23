package game.tileMap.Block;

import game.tools.AABB;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjBlock extends Block {

    public ObjBlock(BufferedImage img, Point pos, int w, int h) {
        super(img, pos, w, h);
    }

    @Override
    public boolean update(AABB p) {
        return true;
    }

    public void render(Graphics2D g){
        super.render(g);
        g.setColor(Color.red);
        g.drawRect(pos.x, pos.y, w, h);
    }

}
