package game.states.differentStates;

import game.controls.KeyHandler;
import game.controls.MouseHandler;
import game.engine.GamePanel;
import game.entity.Player;
import game.graphics.Font;
import game.graphics.SpriteSheet;
import game.mapTiled.MapTiled;
import game.states.GameState;
import game.states.GameStateManager;
import game.tileMap.TileManager;
import game.tools.Vector2f;

import java.awt.*;


public class PlayState extends GameState {

    private Font font;

    public static Vector2f mapPos;

    private Player player;
    private MapTiled mapTiled;
    private int playerSize = 64;

    private TileManager tm;

    private SpriteSheet spriteSheetPlayer = new SpriteSheet("res/entity/player.png", 32, 32);

    public PlayState(GameStateManager gsm) {
        super(gsm);

        init(); // Inicia todas las variables para comenzar el juego
    }

    public void init() {

        mapPos = new Vector2f(GamePanel.width / 2 - (playerSize / 2), GamePanel.height / 2 - (playerSize / 2));
        Vector2f.setWorldVar(mapPos.x, mapPos.y);

        font = new Font("font/font.png", 10, 10);


        mapTiled = new MapTiled("res/map/map.json", playerSize);
        player = new Player(new Vector2f(GamePanel.width / 2 - (playerSize / 2), GamePanel.height / 2 - (playerSize / 2)), spriteSheetPlayer, playerSize);
        tm = new TileManager("res/map/map.json", playerSize);


    }

    @Override
    public void update() {
        Vector2f.setWorldVar(mapPos.x, mapPos.y);
        player.update();
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        key.tick();
        if (key.escape.clicked) {
            System.exit(0);
        }

        player.input(mouse, key);
    }

    @Override
    public void render(Graphics2D g) {

        //mapTiled.render(g);
        tm.render(g);
        player.render(g);

        g.setColor(Color.green);
        g.drawLine(0, 0, (int) player.getPosition().x  + (playerSize / 2), (int) player.getPosition().y + (playerSize / 2));
        g.drawRect((int) player.getPosition().x, (int) player.getPosition().y, playerSize, playerSize);

        SpriteSheet.drawArray(g, font, "FPS: " + GamePanel.oldFrameCount, new Point(50, 10), 20, 20, 15, 0);

    }
}
