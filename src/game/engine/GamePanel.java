package game.engine;

import game.controls.KeyHandler;
import game.controls.MouseHandler;
import game.states.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class GamePanel extends JPanel implements Runnable {

    public static int width;
    public static int height;

    public static int oldFrameCount;
    public static double TARGET_FPS = 60;

    private Thread thread;
    private boolean running = false;

    private BufferedImage img;
    private Graphics2D g;

    private MouseHandler mouse;
    private KeyHandler key;

    private GameStateManager gsm;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;

        Dimension screenSize = new Dimension(width, height);

        mouse = new MouseHandler(this);
        key = new KeyHandler(this);

        this.setPreferredSize(screenSize);
        this.setMaximumSize(screenSize);
        this.setMinimumSize(screenSize);
        this.setFocusable(true);
        this.requestFocus();

    }

    /**
     * Notifica cundo el thread falla
     */
    public void addNotify() {

        super.addNotify();
        // Se crea el thread del juego
        if (thread == null) {
            thread = new Thread(this, "Game Thread");
            thread.start();
        }

    }

    /**
     * INICIO DE VARIABLES
     */
    private void init() {
        running = true;

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // Imagen del panel
        g = (Graphics2D) img.getGraphics(); // Procesando la img con los graphics

        gsm = new GameStateManager();

    }

    /**
     * BUCLE PRINCIPAL DEL JUEGO
     */
    public void run() {
        this.init();

        final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ; // Time before update

        final int MUBR = 5; // Must Update Before Render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        oldFrameCount = 0;

        while (running) {

            double now = System.nanoTime();
            int updateCount = 0;

            while (((now - lastUpdateTime) > TBU) && (updateCount < MUBR)) {
                update();
                input(mouse, key);
                lastUpdateTime += TBU;
                updateCount++;
            }

            if (now - lastUpdateTime > TBU) {
                lastUpdateTime = now - TBU;
            }

            input(mouse, key);
            render();
            draw();

            lastRenderTime = now;
            frameCount++;

            int thisSecond = (int) (lastUpdateTime / 1000000000);

            if (thisSecond > lastSecondTime) {
                if (frameCount != oldFrameCount) {
                    System.out.println("FPS: " + thisSecond + " - " + frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while (now - lastRenderTime < TTBR && now - lastUpdateTime < TBU) {
                Thread.yield();

                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println("Error en el Yield del Thread");
                    e.printStackTrace();
                }

                now = System.nanoTime();
            }

        }

    }

    /**
     * ACTUALIZACIONES POR SEGUNDO
     */
    private void update() {
        gsm.update();
    }

    /**
     * RENDER POR SEGUNDO
     */
    private void render() {
        if (g != null) {
            g.setColor(new Color(154, 240, 78));
            g.fillRect(0, 0, width, height); // Fondo del panel

            gsm.render(g); // Dibujando el estado actual del juego
        }
    }

    /**
     * DIBUJO POR SEGUNDO
     */
    private void draw() {
        Graphics g2 = (Graphics) this.getGraphics();
        g2.drawImage(img, 0, 0, width, height, null); // Dibujando en el Panel
        g2.dispose();
    }

    private void input(MouseHandler mouse, KeyHandler key) {
        gsm.input(mouse, key);
    }

}
