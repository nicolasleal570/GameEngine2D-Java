package game.controls;

import game.engine.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyHandler implements KeyListener {

    public static List<Key> keys = new ArrayList<Key>(); // Lista de las teclas del teclado

    public class Key {

        public int presses;
        public int absorbs;

        public boolean down;
        public boolean clicked;

        public Key() {
            keys.add(this);
        }

        public void toggle(boolean pressed) {
            if (pressed != down) {
                down = pressed;
            }
            if (pressed) {
                presses++;
            }
        }

        public void tick() {
            if (absorbs < presses) {
                absorbs++;
                clicked = true;
            } else {
                clicked = false;
            }
        }

    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();

    public Key attack = new Key();
    public Key enter = new Key();
    public Key escape = new Key();
    public Key menu = new Key();

    public KeyHandler(GamePanel game) {

        game.addKeyListener(this);

    }

    /**
     * Reinicio de las teclas
     */
    public void releaseAll() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).down = false;
        }
    }

    public void tick() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).tick();
        }
    }

    /**
     * Inicio de las teclas pisadas
     *
     * @param e = Evento de teclado
     * @param pressed = Si esta pisada = true. Si se suelta = false
     */
    public void toggle(KeyEvent e, boolean pressed) {
        // TECLAS PARA MOVER AL JUGADOR
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            down.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right.toggle(pressed);
        }

        // TECLAS INTERACTIVAS
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            attack.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            escape.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enter.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            menu.toggle(pressed);
        }
    }

    public void keyTyped(KeyEvent e) {
        // Nothing to do
    }

    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }

    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }

}
