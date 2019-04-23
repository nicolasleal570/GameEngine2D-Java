package game.tileMap;

import game.graphics.SpriteSheet;
import game.tileMap.Block.Block;
import game.tileMap.Block.NormBlock;

import java.awt.*;
import java.util.ArrayList;

public class TileMapNorm extends TileMap {

    private ArrayList<Block> blocks;

    public TileMapNorm(String data, SpriteSheet sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        super(data, sprite, width, height, tileWidth, tileHeight, tileColumns);

        blocks = new ArrayList<>();

        String[] block = data.split(",");

        for (int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));

            if (temp != 0){
                int x = (i % width) * tileWidth;
                int y = (i / height) * tileHeight;

                blocks.add(new NormBlock(sprite.getSpriteInArray((temp - 1) % tileColumns, (temp - 1) / tileColumns),
                        new Point(x, y),tileWidth, tileHeight));
            }

        }
    }

    @Override
    public void render(Graphics2D g) {
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).render(g);
        }
    }
}
