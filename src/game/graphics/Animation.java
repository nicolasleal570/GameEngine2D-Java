package game.graphics;

import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;
    private int numFrames;

    private int count;
    private int delay; // Tiempo entre frame y frame

    private int timesPlayed;

    /**
     * Se le pasa un conjunto de animaciones
     *
     * @param frames
     */
    public Animation(BufferedImage[] frames) {
        timesPlayed = 0;
        setFrames(frames);
    }

    public Animation() {
        timesPlayed = 0;
    }

    /**
     * Establece el conjunto de animaciones
     *
     * @param frames
     */
    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        count = 0;
        timesPlayed = 0;
        delay = 2;
        numFrames = frames.length;
    }

    public void setDelay(int i) {
        delay = i;
    }

    public void setFrame(int i) {
        currentFrame = i;
    }

    public void setNumFrames(int i) {
        numFrames = i;
    }

    public void update() {
        if (delay == -1) {
            return;
        }

        count++;

        if (count == delay) {
            currentFrame++;
            count = 0;
        }
        if (currentFrame == numFrames) {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    /**
     * Obtener la imagen del frame actual
     *
     * @return
     */
    public BufferedImage getImage() {
        return frames[currentFrame];
    }

    public int getFrame() {
        return currentFrame;
    }

    public int getNumFrames() {
        return numFrames;
    }

    public int getCount() {
        return count;
    }

    public int getDelay() {
        return delay;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public boolean hasPlayedOnce() {
        return timesPlayed > 0;
    }

    public boolean hasPlayed(int i) {
        return timesPlayed == i;
    }
}
