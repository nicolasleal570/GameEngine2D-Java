package game.tileMap;

import game.graphics.SpriteSheet;

import java.awt.*;

public abstract class TileMap {

    public TileMap(String data, SpriteSheet sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns){

    }

    public abstract void render(Graphics2D g);



}
