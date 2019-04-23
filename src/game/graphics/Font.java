package game.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Font {

    private BufferedImage FONTSHEET = null;
    private BufferedImage[][] fontArray;
    private final int TILE_SIZE = 32;
    public int w, h;
    private int wLetter, hLetter;

    public Font(String file) {
        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("Loading..." + file);

        FONTSHEET = loadFont(file);

        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() / h;
        loadFontArray();
    }

    public Font(String file, int w, int h) {
        this.w = w;
        this.h = h;

        System.out.println("Loading..." + file);
        FONTSHEET = loadFont(file);

        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() / h;
        loadFontArray();
    }

    public int getWidth() {
        return w;
    }

    public void setWidth(int w) {
        this.w = w;
        this.wLetter = FONTSHEET.getWidth() / w;
    }

    public int getHeight() {
        return h;
    }

    public void setHeight(int h) {
        this.h = h;
        this.hLetter = FONTSHEET.getHeight() / h;
    }

    public void setSize(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    public BufferedImage getFontSheet() {
        return FONTSHEET;
    }

    /**
     * Carga una imagen invidual
     *
     * @param file = Ruta de la Imagen
     * @return
     */
    private BufferedImage loadFont(String file) {

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
    private void loadFontArray() {

        fontArray = new BufferedImage[wLetter][hLetter];

        for (int x = 0; x < wLetter; x++) {
            for (int y = 0; y < hLetter; y++) {

                fontArray[x][y] = getLetter(x, y);

            }
        }

    }

    /**
     * DEVUELVE UNA FUENTE DE IMG SEGUN LA COLUMNA Y LA FILA
     *
     * @param x = fila
     * @param y = columna
     * @return BufferedImage
     */
    public BufferedImage getLetter(int x, int y) {
        return FONTSHEET.getSubimage(x * w, y * h, w, h);
    }

    /**
     * Obtener una fuente depende del caracter
     * @param letter
     * @return
     */
    public BufferedImage getFont(char letter) {
        int value = letter;

        int x = value % wLetter;
        int y = value / wLetter;

        return getLetter(x, y);
    }

}
