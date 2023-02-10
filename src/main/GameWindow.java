package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {

    private JFrame jframe;
    public GameWindow(GamePanel gamePanel){

        jframe = new JFrame();
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.pack();
        jframe.setResizable(false);
        jframe.setTitle("Plattformer Tutorial");
        jframe.setLocationRelativeTo(null);
        jframe.addWindowFocusListener(new WindowFocusListener(){
            // prevent game from updating when windows focus is lost
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });

        jframe.setVisible(true);
    }

}
