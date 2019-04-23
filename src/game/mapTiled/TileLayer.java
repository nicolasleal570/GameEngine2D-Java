package game.mapTiled;

public abstract class TileLayer {

    protected int width;
    protected int height;
    protected int x;
    protected int y;

    public TileLayer(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

}
