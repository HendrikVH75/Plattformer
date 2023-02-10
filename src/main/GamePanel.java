package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;


public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game) {

        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

        this.game = game;
        setPanelSize();

    }

    private void setPanelSize() {

        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);

    }

    public Game getGame() {

        return game;

    }

    public void updateGame() {
        // update game logic


    }

    public void paintComponent(Graphics g) {
        // draw game buffer
        super.paintComponent(g);
        game.render(g);

    }

}
