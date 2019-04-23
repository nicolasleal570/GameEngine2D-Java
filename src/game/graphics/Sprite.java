package game.graphics;

import java.awt.image.BufferedImage;

public class Sprite {

    private final BufferedImage spriteImg;

    private final int width;
    private final int height;

    private int wSprite;
    private int hSprite;

    public Sprite(final BufferedImage imagen) {
        this.spriteImg = imagen;

        width = imagen.getWidth();
        height = imagen.getHeight();
    }

    public BufferedImage getImage() {
        return spriteImg;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}