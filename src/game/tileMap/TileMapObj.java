package game.tileMap;

import game.graphics.SpriteSheet;
import game.tileMap.Block.Block;
import game.tileMap.Block.ObjBlock;

import java.awt.*;
import java.util.HashMap;

public class TileMapObj extends TileMap {

    public static HashMap<String, Block> tmo_blocks;


    public TileMapObj(String data, SpriteSheet sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        super(data, sprite, width, height, tileWidth, tileHeight, tileColumns);

        Block tempBlock = null;
        tmo_blocks = new HashMap<>();

        String[] block = data.split(",");

        for (int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));

            if (temp != 0){
                int x = (i % width) * tileWidth;
                int y = (i / height) * tileHeight;

                tempBlock = new ObjBlock(sprite.getSpriteInArray((temp - 1) % tileColumns, (temp - 1) / tileColumns),
                        new Point(x, y), tileWidth, tileHeight);
            }
            String txt = (i % width)+","+(i / height);
            tmo_blocks.put(txt, tempBlock);

        }

    }

    @Override
    public void render(Graphics2D g) {
        for (Block block : tmo_blocks.values()){
            block.render(g);
        }
    }
}
