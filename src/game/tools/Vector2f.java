package game.tools;

public class Vector2f {

    public float x, y;

    public static float worldX, worldY;

    public Vector2f(){
        x = 0;
        y = 0;
    }

    public Vector2f(Vector2f vec){
        new Vector2f(vec.x, vec.y);
    }

    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void addX(float i){ x += i;}

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void addY(float i){ y += i;}

    public void setVector(Vector2f vec){
        x = vec.x;
        y = vec.y;
    }

    public void setVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static void setWorldVar(float x, float y){
        worldX = x;
        worldY = y;
    }

    public Vector2f getWorldVar(){
        return new Vector2f(x - worldX, y - worldY);
    }

    @Override
    public String toString(){
        return x+","+y;
    }

}
