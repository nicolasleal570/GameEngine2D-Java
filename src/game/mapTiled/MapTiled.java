package game.mapTiled;

import game.engine.GamePanel;
import game.entity.Player;
import game.graphics.Sprite;
import game.graphics.SpriteSheet;
import game.tools.FileLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.awt.*;
import java.util.ArrayList;

public class MapTiled {

    private int mapWidthInTiles;
    private int mapHeightInTiles;

    private Point playerSpawn;
    private int playerSize;

    private ArrayList<CollisionLayer> collisionLayers;
    private ArrayList<SpriteLayer> spriteLayers;

    private ArrayList<Rectangle> originalCollisions;
    private ArrayList<Rectangle> updatedCollisions;

    private Sprite[] spriteCollection;

    public MapTiled(String file, int playerSize) {

        this.playerSize = playerSize;

        String txtContent = FileLoader.loadTxtFile(file);
        JSONObject jsonObject = getJSONObject(txtContent);

        loadMapDimension(jsonObject);
        loadPlayerSpawn(jsonObject);
        loadMapLayers(jsonObject);
        loadMapCollision(jsonObject);
        loadTileCount(jsonObject);
    }

    private void setSpriteCollection(JSONObject jsonObject, JSONArray spriteCollect) {
        for (int i = 0; i < spriteCollect.size(); i++) {

            JSONObject dataGroup = getJSONObject(spriteCollect.get(i).toString());

            String imgName = dataGroup.get("name").toString();
            int tileWidth = getJSONInteger(dataGroup, "tilewidth");
            int tileHeight = getJSONInteger(dataGroup, "tileheight");
            SpriteSheet spriteSheet = new SpriteSheet("res/map/" + imgName + ".png", tileWidth, tileHeight);

            int firstSprite = getJSONInteger(dataGroup, "firstgid") - 1;
            int lastSprite = getJSONInteger(dataGroup, "tilecount") - 1;

            for (int j = 0; j < spriteLayers.size(); j++) {
                SpriteLayer spriteLayer = spriteLayers.get(j);
                int[] idSprites = spriteLayer.getIdSprites();

                for (int k = 0; k < idSprites.length; k++) {
                    int idActual = idSprites[k];

                    if (idActual >= firstSprite && idActual <= lastSprite) {
                        if (spriteCollection[idActual] == null) {
                            spriteCollection[idActual] = spriteSheet.getSprite(idActual - firstSprite);
                        }
                    }
                }
            }

        }
    }

    private void loadTileCount(JSONObject jsonObject) {
        JSONArray spriteCollect = getJSONArray(jsonObject.get("tilesets").toString());
        int spriteCount = 0;

        for (int i = 0; i < spriteCollect.size(); i++) {
            JSONObject groupData = getJSONObject(spriteCollect.get(i).toString());
            spriteCount += getJSONInteger(groupData, "tilecount");
        }

        spriteCollection = new Sprite[spriteCount];

        setSpriteCollection(jsonObject, spriteCollect);
    }

    private void loadMapCollision(JSONObject jsonObject) {
        originalCollisions = new ArrayList<>();
        for (int i = 0; i < collisionLayers.size(); i++) {
            Rectangle[] rectangles = collisionLayers.get(i).getCollisions();

            for (int j = 0; j < rectangles.length; j++) {
                originalCollisions.add(rectangles[j]);
            }
        }
    }

    private void loadMapLayers(JSONObject jsonObject) {
        JSONArray layers = getJSONArray(jsonObject.get("layers").toString());

        collisionLayers = new ArrayList<CollisionLayer>();
        spriteLayers = new ArrayList<SpriteLayer>();

        for (int i = 0; i < layers.size(); i++) {
            JSONObject layerData = getJSONObject(layers.get(i).toString());

            String type = layerData.get("type").toString();
            int widthLayer = 0;
            int heightLayer = 0;

            if (!type.equals("objectgroup")) {
                widthLayer = getJSONInteger(layerData, "width");
                heightLayer = getJSONInteger(layerData, "height");

            }
            int xLayer = getJSONInteger(layerData, "x");
            int yLayer = getJSONInteger(layerData, "y");

            switch (type) {
                case "tilelayer":
                    JSONArray sprites = getJSONArray(layerData.get("data").toString());
                    int[] spritesLayer = new int[sprites.size()];

                    for (int j = 0; j < spritesLayer.length; j++) {
                        int idSprite = Integer.parseInt(sprites.get(j).toString());
                        spritesLayer[j] = idSprite - 1;
                    }
                    spriteLayers.add(new SpriteLayer(widthLayer, heightLayer, xLayer, yLayer, spritesLayer));

                    break;
                case "objectgroup":
                    JSONArray rect = getJSONArray(layerData.get("objects").toString());
                    Rectangle[] rectLayer = new Rectangle[rect.size()];

                    for (int j = 0; j < rectLayer.length; j++) {
                        JSONObject dataRect = getJSONObject(rect.get(j).toString());

                        int x = getJSONInteger(dataRect, "x");
                        int y = getJSONInteger(dataRect, "y");
                        int width = getJSONInteger(dataRect, "width");
                        int height = getJSONInteger(dataRect, "height");

                        if (x == 0) x = 1;
                        if (y == 0) y = 1;
                        if (width == 0) width = 1;
                        if (height == 0) height = 1;

                        Rectangle rectangle = new Rectangle(x, y, width, height);
                        rectLayer[j] = rectangle;
                    }

                    collisionLayers.add(new CollisionLayer(widthLayer, heightLayer, xLayer, yLayer, rectLayer));

                    break;
            }

        }
    }

    /**
     * Establece la ubicacion del jugador en coordenadas por tile
     *
     * @param jsonObject
     */
    private void loadPlayerSpawn(JSONObject jsonObject) {
        JSONObject spawn = getJSONObject(jsonObject.get("start").toString());
        playerSpawn = new Point(getJSONInteger(spawn, "x"), getJSONInteger(spawn, "y"));
    }

    /**
     * Establece las dimensiones del mapa en tiles
     *
     * @param jsonObject
     */
    private void loadMapDimension(JSONObject jsonObject) {
        mapWidthInTiles = getJSONInteger(jsonObject, "width");
        mapHeightInTiles = getJSONInteger(jsonObject, "height");
    }

    private JSONObject getJSONObject(final String codigoJSON) {
        JSONParser lector = new JSONParser();
        JSONObject objetoJSON = null;

        try {
            Object recuperado = lector.parse(codigoJSON);
            objetoJSON = (JSONObject) recuperado;
        } catch (ParseException e) {
            System.out.println(e);
        }

        return objetoJSON;
    }

    private JSONArray getJSONArray(final String codigoJSON) {
        JSONParser lector = new JSONParser();
        JSONArray arrayJSON = null;

        try {
            Object recuperado = lector.parse(codigoJSON);
            arrayJSON = (JSONArray) recuperado;
        } catch (ParseException e) {

            System.out.println(e);
        }

        return arrayJSON;
    }

    private int getJSONInteger(final JSONObject objetoJSON, final String clave) {
        return Integer.parseInt(objetoJSON.get(clave).toString());
    }

    public void update() {

    }

    public void render(Graphics2D g) {

        for (int i = 0; i < spriteLayers.size(); i++) {
            int[] idSprites = spriteLayers.get(i).getIdSprites();

            for (int y = 0; y < mapHeightInTiles; y++) {
                for (int x = 0; x < mapWidthInTiles; x++) {

                    int idActual = idSprites[x + y * mapWidthInTiles];

                    if (idActual != -1) {
                        int xOffset = (GamePanel.width / 2 - playerSize / 2);
                        int yOffset = (GamePanel.height / 2 - playerSize / 2);

                        int puntoX = (int) (x * playerSize - (Player.pos.x * playerSize) + xOffset);
                        int puntoY = (int) (y * playerSize - (Player.pos.y * playerSize) + yOffset);

                        if (puntoX < 0 - playerSize ||
                                puntoX > GamePanel.width ||
                                puntoY < 0 - playerSize ||
                                puntoY > GamePanel.height) {
                            continue;
                        }

                        g.drawImage(spriteCollection[idActual].getImage(), puntoX, puntoY, playerSize, playerSize, null);
                        g.setColor(Color.red);
                        //g.drawRect(puntoX, puntoY, playerSize, playerSize);

                    }

                }
            }

        }

    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }
}
