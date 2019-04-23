package game.entity;

import game.controls.KeyHandler;
import game.controls.MouseHandler;
import game.graphics.Animation;
import game.graphics.SpriteSheet;
import game.tools.AABB;
import game.tools.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Creature {

    public final int UP = 3;
    public final int DOWN = 2;
    public final int LEFT = 1;
    public final int RIGHT = 0;

    protected int currentAnimation;

    public static Vector2f pos;
    protected SpriteSheet spriteSheet;
    protected Animation ani;
    protected int size;

    protected AABB hitBounds;
    protected AABB bounds; // Hitbox

    protected boolean up;
    protected boolean down;
    protected boolean left;
    protected boolean right;

    protected  boolean attack;
    protected int attackSpeed;
    protected int attackDuration;

    protected float dx, dy;

    // Controles de la aceleracion del jugador
    protected float maxSpeed = 3;
    protected float acc = 2;
    protected float deacc = 1;

    public Creature(Vector2f origin, SpriteSheet spriteSheet, int size) {
        pos = origin;
        this.spriteSheet = spriteSheet;
        this.size = size;

        bounds = new AABB(origin, size, size);
        hitBounds = new AABB(new Vector2f(origin.x + (size / 2), origin.y + (size / 2)), size, size);

        ani = new Animation();
        setAnimation(RIGHT, spriteSheet.getSpriteArray(RIGHT), 10);
    }

    public void update(){
        animate();
        setHitBoxDirection();
        ani.update();
    }

    private void setHitBoxDirection() {
        if (up){
            hitBounds.setyOffset(-size / 2);
            hitBounds.setxOffset(-size / 2);
        }else if (down){
            hitBounds.setyOffset(size / 2);
            hitBounds.setxOffset(-size / 2);
        }else if (left){
            hitBounds.setxOffset(-size);
            hitBounds.setyOffset(0);
        }else if (right){ // Posicion Default
            hitBounds.setxOffset(0);
            hitBounds.setyOffset(0);
        }
    }

    public abstract void input(MouseHandler mouse, KeyHandler key);

    public abstract void render(Graphics2D g);

    /**
     * Animando a la criatura
     */
    protected void animate() {
        if (up) {
            if (currentAnimation != UP || ani.getDelay() == -1) {
                setAnimation(UP, spriteSheet.getSpriteArray(UP), 5);
            }
        } else if (down) {
            if (currentAnimation != DOWN || ani.getDelay() == -1) {
                setAnimation(DOWN, spriteSheet.getSpriteArray(DOWN), 5);
            }
        } else if (left) {
            if (currentAnimation != LEFT || ani.getDelay() == -1) {
                setAnimation(LEFT, spriteSheet.getSpriteArray(LEFT), 5);
            }
        } else if (right) {
            if (currentAnimation != RIGHT || ani.getDelay() == -1) {
                setAnimation(RIGHT, spriteSheet.getSpriteArray(RIGHT), 5);
            }
        }else {
            setAnimation(currentAnimation, spriteSheet.getSpriteArray(currentAnimation), -1);
        }
    }

    /**
     * Establece la animacion de la criatura
     * @param i
     * @param frames
     * @param delay
     */
    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public Animation getAnimtion() { return ani; }
    public void setSpriteSheet(SpriteSheet spriteSheet) { this.spriteSheet = spriteSheet; }
    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public void setAcc(float acc) {
        this.acc = acc;
    }
    public void setDeacc(float deacc) {
        this.deacc = deacc;
    }
    public AABB getBounds() {
        return bounds;
    }
    public void setBounds(AABB bounds) {
        this.bounds = bounds;
    }
    public Vector2f getPosition() {
        return pos;
    }
}
