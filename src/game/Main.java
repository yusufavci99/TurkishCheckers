package game;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Game.getGame();

        JFrame gameFrame = new JFrame();
        gameFrame.setLayout(new FlowLayout());
        gameFrame.setTitle("Turkish Checkers");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameFrame.setSize( 8 * BoardCanvas.BOX_SIZE + 20,
                (8 + 1) * BoardCanvas.BOX_SIZE);


        String firstPlayer = JOptionPane.showInputDialog
                ( gameFrame, "Type First Player's Name");

        String secondPlayer = JOptionPane.showInputDialog
                ( gameFrame, "Type Second Player's Name");

        gameFrame.add( new BoardDisplay( firstPlayer, secondPlayer, Game.getGame().getGameBoard()));
        gameFrame.setVisible( true);
    }
}
