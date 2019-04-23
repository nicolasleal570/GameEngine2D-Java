package game.entity;

import game.controls.KeyHandler;
import game.controls.MouseHandler;
import game.graphics.SpriteSheet;
import game.tools.Vector2f;

import java.awt.*;

public class Player extends Creature {

    public Player(Vector2f position, SpriteSheet spriteSheet, int size) {
        super(position, spriteSheet, size);

    }

    public void update() {
        super.update();
        move();
        pos.x += dx;
        pos.y += dy;
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

        if (mouse.getButton() == 1) {
            System.out.println("Player: (" + pos.x + ", " + pos.y + ")");
        }

        controlWithKeys(key);
    }

    @Override
    public void render(Graphics2D g) {

        g.drawImage(ani.getImage(), (int) pos.x, (int) pos.y, size, size, null);
        g.setColor(Color.blue);
        g.drawRect((int) pos.x, (int) pos.y, size, size);
    }

    /**
     * Moviendo al jugador
     */
    private void move() {
        if (up) {
            dy -= acc;
            if (dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else {
            if (dy < 0) {
                dy += deacc;
                if (dy > 0) {
                    dy = 0;
                }
            }
        }
        if (down) {
            dy += acc;
            if (dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else {
            if (dy > 0) {
                dy -= deacc;
                if (dy < 0) {
                    dy = 0;
                }
            }
        }
        if (left) {
            dx -= acc;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else {
            if (dx < 0) {
                dx += deacc;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }
        if (right) {
            dx += acc;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= deacc;
                if (dx < 0) {
                    dx = 0;
                }
            }
        }
    }

    /**
     * Controlando las teclas para mover al jugador
     *
     * @param key
     */
    private void controlWithKeys(KeyHandler key) {
        if (key.up.down) {
            up = true;
        } else {
            up = false;
        }
        if (key.down.down) {
            down = true;
        } else {
            down = false;
        }
        if (key.left.down) {
            left = true;
        } else {
            left = false;
        }
        if (key.right.down) {
            right = true;
        } else {
            right = false;
        }

        if (key.attack.down) {
            attack = true;
        } else {
            attack = false;
        }
    }

    public int getSize() {
        return size;
    }
}
