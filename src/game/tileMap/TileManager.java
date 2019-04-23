package game.tileMap;

import game.graphics.SpriteSheet;
import game.tools.FileLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class TileManager {

    public static ArrayList<TileMap> tm;

    public TileManager(String path, int size) {
        tm = new ArrayList<>();
        loadTileMap(path, size, size);
    }

    /**
     * Carga el objeto JSON y crea el mapa por capas
     * @param path
     * @param blockWidth
     * @param blockHeight
     */
    private void loadTileMap(String path, int blockWidth, int blockHeight) {
        String txtContent = FileLoader.loadTxtFile(path);
        JSONObject content = getJSONObject(txtContent);

        int width = getJSONInteger(content, "width");
        int height = getJSONInteger(content, "height");

        SpriteSheet sprite = null;

        String[] data = new String[10]; // Todas las capas del mapa. 10 = Numero maximo de capas

        JSONArray tilesets = getJSONArray(content.get("tilesets").toString());
        for (int i = 0; i < tilesets.size(); i++) {
            JSONObject tileSet = getJSONObject(tilesets.get(i).toString());

            String imgName = tileSet.get("name").toString();
            int tileColumns = getJSONInteger(tileSet, "columns");
            int tileWidth = getJSONInteger(tileSet, "tilewidth");
            int tileHeight = getJSONInteger(tileSet, "tileheight");

            sprite = new SpriteSheet("res/map/" + imgName + ".png", tileWidth, tileHeight);

            JSONArray layers = getJSONArray(content.get("layers").toString());
            for (int j = 0; j < layers.size(); j++) {
                JSONObject layer = getJSONObject(layers.get(j).toString());
                String type = layer.get("name").toString();

                JSONArray dataLayer = getJSONArray(layer.get("data").toString());
                String tempData = "";
                for (int k = 0; k < dataLayer.size(); k++) {
                        tempData += dataLayer.get(k) + ", ";
                }
                data[j] = tempData;

                if (!type.startsWith("Solid")){
                    tm.add(new TileMapNorm(data[j], sprite, width, height, blockWidth, blockHeight, tileColumns)); // Normal
                } else {
                    tm.add(new TileMapObj(data[j], sprite, width, height, blockWidth, blockHeight, tileColumns)); // Solido
                }

            }

        }
    }

    private void addTileMap(String path, int blockWidth, int blockHeight) {

        String imagePath;

        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileCount;
        int tileColumns;
        int layers = 0;
        SpriteSheet sprite;

        String[] data = new String[10]; // Todas las capas del mapa. 10 = Numero maximo de capas

        try {

            // Leyendo el archivo XML
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset"); // Etiqueta tileset
            Node node = list.item(0);
            Element element = (Element) node;

            imagePath = element.getAttribute("name"); // Obteniendo el nombre de la imagen
            tileWidth = getXMLInteger(element, "tilewidth"); // Ancho de la hoja de tiles
            tileHeight = getXMLInteger(element, "tileheight"); // Alto de la hoja de tiles
            sprite = new SpriteSheet("res/map/" + imagePath + ".png", tileWidth, tileHeight);

            tileCount = getXMLInteger(element, "tilecount"); // Numero total de tiles del mapa
            tileColumns = sprite.getSpriteSheetWidth() / tileWidth;

            list = doc.getElementsByTagName("layer"); // Obteniendo cada capa del mapa
            String typeLayer = "";
            layers = list.getLength(); // Numero de capas

            for (int i = 0; i < layers; i++) {
                node = list.item(i);
                element = (Element) node;

                if (i <= 0) { // La primera capa
                    width = Integer.parseInt(element.getAttribute("width")); // Ancho de las capas
                    height = Integer.parseInt(element.getAttribute("height")); // Alto de las capas
                }
                typeLayer = element.getAttribute("name");

                data[i] = element.getElementsByTagName("data").item(0).getTextContent(); // ID de cada tile del mapa
                if (!typeLayer.startsWith("Solid")) { // Capas Layer1 y Layer 2 (No solidas)
                    tm.add(new TileMapNorm(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns)); // Normal
                } else {
                    tm.add(new TileMapObj(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns)); // Solido
                }

            }


        } catch (Exception e) {
            System.out.println("ERROR: " + path + " No se pudo cargar");
            e.printStackTrace();
        }


    }

    private int getXMLInteger(Element element, String clave) {
        return Integer.parseInt(element.getAttribute(clave));
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < tm.size(); i++) {
            tm.get(i).render(g);
        }
    }

    private JSONObject getJSONObject(final String codigoJSON) {
        JSONParser lector = new JSONParser();
        JSONObject objetoJSON = null;

        try {
            Object recuperado = lector.parse(codigoJSON);
            objetoJSON = (JSONObject) recuperado;
        } catch (ParseException e) {
            System.out.println("Posicion: " + e.getPosition());
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
            System.out.println("Posicion: " + e.getPosition());
            System.out.println(e);
        }

        return arrayJSON;
    }

    private int getJSONInteger(final JSONObject objetoJSON, final String clave) {
        return Integer.parseInt(objetoJSON.get(clave).toString());
    }

}
