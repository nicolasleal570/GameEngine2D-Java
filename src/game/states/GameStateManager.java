package game.states;

import game.controls.KeyHandler;
import game.controls.MouseHandler;
import game.states.differentStates.GameOverState;
import game.states.differentStates.MenuState;
import game.states.differentStates.PauseState;
import game.states.differentStates.PlayState;

import java.awt.*;

public class GameStateManager {

    private GameState[] states; // Estados del juego


    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;

    public int onTopState = 0;

    public GameStateManager(){

        states = new GameState[4]; // Numero de estados

        states[0] = new PlayState(this); // Estado de inicio del juego
    }

    /**
     * Verifica si existe el estado como estado actual
     * @param state
     * @return
     */
    public boolean getState(int state){
        return states[state] != null;
    }

    /**
     * Eliminar un estado
     *
     * @param state = id del estado
     */
    public void pop(int state) {
        states[state] = null;
    }

    /**
     * Controlando los estados agregando nuevos segun el id
     *
     * @param state = id del estado
     */
    public void add(int state) {
        if (states[state] != null){
            return;
        }
        if (state == PLAY) {
            states[PLAY] = new PlayState(this);
        }
        if (state == MENU) {
            states[MENU] = new MenuState(this);
        }
        if (state == PAUSE) {
            states[PAUSE] = new PauseState(this);
        }
        if (state == GAMEOVER) {
            states[GAMEOVER] = new GameOverState(this);
        }
    }

    /**
     * Elimina y agrega un nuevo estado
     * @param state
     */
    public void addAndPop(int state) {
        states[0] = null;
        add(state);
    }

    /**
     * Elimina y agrega un estado
     * @param state
     * @param remove
     */
    public void addAndPop(int state, int remove) {
        pop(remove);
        add(state);
    }

    /**
     * Actualizacion de los estados
     */
    public void update() {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null){
                states[i].update();
            }
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].input(mouse, key);
            }
        }
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].render(g);
            }
        }

    }



}
