package game.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage SPRITESHEET = null;

    private int spriteSheetWidthPixel;
    private int spriteSheetHeightPixel;

    private int widthSheetInSprites;
    private int heightSheetInSprites;

    private int widthSprite;
    private int heightSprite;

    private Sprite[] sprites;
    private BufferedImage[][] spriteArray;

    private int wSprite;
    private int hSprite;

    public static Font currentFont;

    public SpriteSheet(String file, int widthSprite, int heightSprite) {
        BufferedImage imagen = loadImage(file);
        this.SPRITESHEET = imagen;

        spriteSheetWidthPixel = imagen.getWidth();
        spriteSheetHeightPixel = imagen.getHeight();

        wSprite = imagen.getWidth() / widthSprite;
        hSprite = imagen.getHeight() / heightSprite;

        widthSheetInSprites = spriteSheetWidthPixel / widthSprite;
        heightSheetInSprites = spriteSheetHeightPixel / heightSprite;

        this.widthSprite = widthSprite;
        this.heightSprite = heightSprite;

        sprites = new Sprite[widthSheetInSprites * heightSheetInSprites];

        fillSpritesFromImage(imagen);
        loadSpriteArray();
    }

    /**
     * Carga la imagen con la hoja de sprites
     *
     * @param file
     * @return
     */
    private BufferedImage loadImage(String file) {
        BufferedImage sprite = null;

        try {

            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));

        } catch (Exception e) {
            System.out.println("ERROR: " + file + " No se pudo cargar");
        }
        return sprite;
    }

    /**
     * Rellena un arreglo con todas las imagenes de la hoja de spriteForAnimation
     *
     * @param img
     */
    private void fillSpritesFromImage(final BufferedImage img) {
        for (int y = 0; y < heightSheetInSprites; y++) {
            for (int x = 0; x < widthSheetInSprites; x++) {
                final int posX = x * widthSprite;
                final int posY = y * heightSprite;

                sprites[x + y * widthSheetInSprites] = new Sprite(img.getSubimage(posX, posY, widthSprite, heightSprite));
            }
        }
    }

    /**
     * Carga toda una hoja de Sprites como matriz
     */
    private void loadSpriteArray() {

        spriteArray = new BufferedImage[hSprite][wSprite];

        for (int y = 0; y < hSprite; y++) {
            for (int x = 0; x < wSprite; x++) {

                spriteArray[y][x] = getSpriteInArray(x, y);

            }
        }

    }

    public BufferedImage getSpriteInArray(int x, int y) {
        return SPRITESHEET.getSubimage(x * widthSprite, y * heightSprite, widthSprite, heightSprite);
    }

    public Sprite getSprite(final int indice) {
        return sprites[indice];
    }

    public Sprite[] getSprite() {
        return sprites;
    }

    public Sprite getSprite(final int x, final int y) {
        return sprites[x + y * widthSheetInSprites];
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

    public int getSpriteSheetWidth() {
        return spriteSheetWidthPixel;
    }

    public int getSpriteSheetHeight() {
        return spriteSheetHeightPixel;
    }
}
