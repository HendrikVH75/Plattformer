package gamestates;

import main.Game;
import ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;

    public boolean isIn(MouseEvent e, MenuButton mb) {
        // contains checks, if x and y coordinates from an event
        // are inside its rectangle
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
