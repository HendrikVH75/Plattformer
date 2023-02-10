package main;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

import java.awt.*;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private Playing playing;
    private Menu menu;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 1.5f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;


    public Game() {
        // Konstruktor

        // init all game logic parts e.g. players, enemy, levels
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        // start game here
        startGameLoop();

    }

    public void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {

        switch (Gamestate.state) {

            case PLAYING:
                playing.update();
                break;
            case MENU:
                menu.update();
                break;
            default:
                break;
        }


    }

    public void render(Graphics g) {

        switch (Gamestate.state) {

            case PLAYING:
                playing.draw(g);
                break;
            case MENU:
                menu.draw(g);
                break;
            default:
                break;
        }


    }


    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastCheck = System.currentTimeMillis();
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;

        double deltaU = 0;
        double deltaF = 0;

        // Loop starts here
        while (true) {

            // UPS logic updates
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / timePerUpdate;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            // FPS render updates
            deltaF += (currentTime - previousTime) / timePerFrame;

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // reset time variable
            previousTime = currentTime;

            // print out FPS and UPS values
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }

        }

    }

    public void windowFocusLost() {
        // do some actions if windows focus is lost
        if (Gamestate.state == Gamestate.PLAYING) {
            playing.getPlayer().resetDirBooleans();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }
}
