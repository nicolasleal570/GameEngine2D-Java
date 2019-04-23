package game.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteForAnimation {

    private BufferedImage SPRITESHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 32;
    public int w, h;
    private int wSprite, hSprite;

    public static Font currentFont;

    public SpriteForAnimation(String file) {
        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("Loading..." + file);

        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }

    public SpriteForAnimation(String file, int w, int h) {
        this.w = w;
        this.h = h;

        System.out.println("Loading..." + file);
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }

    public int getWidth() {
        return w;
    }

    public void setWidth(int w) {
        this.w = w;
        this.wSprite = SPRITESHEET.getWidth() / w;
    }

    public int getHeight() {
        return h;
    }

    public void setHeight(int h) {
        this.h = h;
        this.hSprite = SPRITESHEET.getHeight() / h;
    }

    public void setSize(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    /**
     * Carga una imagen invidual
     *
     * @param file = Ruta de la Imagen
     * @return
     */
    private BufferedImage loadSprite(String file) {

        BufferedImage sprite = null;

        try {

            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));

        } catch (Exception e) {
            System.out.println("ERROR: " + file + " No se pudo cargar");
        }
        return sprite;
    }

    /**
     * Carga toda una hoja de Sprites como matriz
     */
    private void loadSpriteArray() {

        spriteArray = new BufferedImage[hSprite][wSprite];

        for (int y = 0; y < hSprite; y++) {
            for (int x = 0; x < wSprite; x++) {

                spriteArray[y][x] = getSprite(x, y);

            }
        }

    }

    /**
     * DEVUELVE EL SPRITE SEGUN LA COLUMNA Y LA FILA
     *
     * @param x = fila
     * @param y = columna
     * @return BufferedImage
     */
    public BufferedImage getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x * w, y * h, w, h);
    }

    /**
     * Devuelve una secuencia para animaciones
     *
     * @param i = indice
     * @return BuuferedImage[] = Arreglo de imagenes para animacion
     */
    public BufferedImage[] getSpriteArray(int i) {
        return spriteArray[i];
    }

    public BufferedImage[][] getSpriteArray2() {
        return spriteArray;
    }

    public BufferedImage getSpriteSheet() {
        return SPRITESHEET;
    }

    /**
     * DIBUJA UN FUENTE EN LA PANTALLA
     *
     * @param g       = graficos
     * @param f       = Fuente
     * @param word    = Palabra que se quiere dibujar
     * @param pos     = posicion en pantalla
     * @param width   = ancho de la palabra
     * @param height  = alto de la palabra
     * @param xOffset = posicion en x
     * @param yOffset = posicion en y
     */
    public static void drawArray(Graphics2D g, Font f, String word, Point pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        currentFont = f;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != 32) { // Si el caracter es distinto al espacio ' '

                g.drawImage(f.getFont(word.charAt(i)), (int) x, (int) y, width, height, null);
            }


            x += xOffset;
            y += yOffset;
        }

    }

}
