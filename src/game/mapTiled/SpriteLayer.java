package game.mapTiled;

public class SpriteLayer extends TileLayer {

    private int[] idSprites;

    public SpriteLayer(int width, int height, int x, int y, int[] idSprites) {
        super(width, height, x, y);
        this.idSprites = idSprites;
    }

    public int[] getIdSprites() {
        return idSprites;
    }
}
