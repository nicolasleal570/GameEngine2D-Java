package game.controls;

import game.engine.GamePanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private static int mouseX = -1;
    private static int mouseY = -1;
    private static int mouseB = -1; // Mouse scroll button

    public MouseHandler(GamePanel game) {
        game.addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        mouseB = e.getButton();
    }

    public void mouseReleased(MouseEvent e) {
        mouseB = -1;
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public static int getMouseX() {
        return mouseX;
    }

    public static void setMouseX(int mouseX) {
        MouseHandler.mouseX = mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }

    public static void setMouseY(int mouseY) {
        MouseHandler.mouseY = mouseY;
    }

    public static int getButton() {
        return mouseB;
    }

    public static void setButton(int mouseB) {
        MouseHandler.mouseB = mouseB;
    }

}
