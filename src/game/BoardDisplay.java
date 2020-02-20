package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardDisplay  extends JPanel implements ActionListener {

    // properties
    BoardCanvas gameGrid;

    GameBoard gameBoard;
    JPanel bottomInfo;
    JPanel buttons;
    String player1;
    String player2;
    Timer timer;
//    JRadioButton s;
//    JRadioButton o;
    ButtonGroup group;
    JLabel firstPlayer;
    JLabel secondPlayer;
    //JLabel time;
    int remainingTime;

    //constructors
    public BoardDisplay(String player1, String player2, GameBoard gameBoard) {

        this.player1 = player1;
        this.player2 = player2;
        //gameModel = inSOS;

        setLayout( new BorderLayout());

        this.gameBoard = gameBoard;

        initUI();

        add( gameGrid, BorderLayout.CENTER);
        //add( time, BorderLayout.NORTH);
        add( bottomInfo, BorderLayout.SOUTH);

        setPreferredSize
                ( new Dimension
                        ( BoardCanvas.BOX_SIZE * 8 + 1,
                                BoardCanvas.BOX_SIZE * 8 + 50));

    }

    // methods
    /**
     * Initializes game's variables.
     */
    public void initUI() {
        remainingTime = 30;
        //time = new JLabel( "" + remainingTime);

        //timer = new Timer( 1000, this);
        //timer.start();

        gameGrid = new BoardCanvas( gameBoard);
        gameGrid.addMouseListener( new MyMouseListener());

        group = new ButtonGroup();
//        group.add( s);
//        group.add( o);

        buttons = new JPanel();
        buttons.setLayout( new FlowLayout());
//        buttons.add( s);
//        buttons.add( o);

        bottomInfo = new JPanel();
        bottomInfo.setLayout( new GridLayout( 0,3));

        firstPlayer = new JLabel( " " + this.player1 + " : " + 0);
        firstPlayer.setBorder( BorderFactory.createLineBorder(Color.BLACK, 2));
        firstPlayer.setFont( new Font("Arial", Font.PLAIN, 16));
        firstPlayer.setBackground( Color.GREEN);
        firstPlayer.setOpaque( true);
        bottomInfo.add( firstPlayer);

        bottomInfo.add( buttons);

        secondPlayer = new JLabel( this.player2 + " : " + 0 + " ",
                SwingConstants.RIGHT);
        secondPlayer.setBorder( BorderFactory.createLineBorder(Color.BLACK, 2));
        secondPlayer.setFont( new Font("Arial", Font.PLAIN, 16));
        secondPlayer.setOpaque( true);
        bottomInfo.add( secondPlayer);
    }

    /**
     * Checks if remaining time is equal to zero. If time becomes equal to zero
     * while a player's turn, other player wins.
     * @param e Timer's paramater
     */
    public void actionPerformed( ActionEvent e) {

//        if( remainingTime == 0 && gameModel.getTurn() == 1) {
//            JOptionPane.showMessageDialog( this,
//                    player2 + " is the winner!");
//            System.exit( 0);
//        }
//        else if( remainingTime == 0 && gameModel.getTurn() == 2) {
//            JOptionPane.showMessageDialog( this,
//                    player1 + " is the winner!");
//            System.exit( 0);
//        }

        remainingTime--;
        //time.setText( "" + remainingTime);

    }

    public void toggleColor( boolean player) {
        // toggle colors
        if( player) {
            firstPlayer.setBackground( Color.GREEN);
            secondPlayer.setBackground( null);
        }
        else {
            firstPlayer.setBackground( null);
            secondPlayer.setBackground( Color.GREEN);
        }
    }


    public void updateGame( int playerNum) {



        // updates scores
//        firstPlayer.setText( " " + this.player1 +
//                " : " + gameModel.getPlayerScore1());
//        secondPlayer.setText( this.player2 + " : " +
//                gameModel.getPlayerScore2() + " ");
    }

    /**
     * Checks if any player won. If a player has won prints a message dialog.
     */
    public void checkGameWin() {

        // prints the winner if game is oversssss
//        if( gameModel.isGameOver()) {
//            if( gameModel.getPlayerScore1() > gameModel.getPlayerScore2()) {
//                JOptionPane.showMessageDialog( this,
//                        player1 + " is the winner!");
//            }
//            else if( gameModel.getPlayerScore2() > gameModel.getPlayerScore1()) {
//                JOptionPane.showMessageDialog( this,
//                        player2 + " is the winner!");
//            }
//            else {
//                JOptionPane.showMessageDialog( this, " It's a draw!");
//            }
//
//            System.exit(0);
//        }

    }

    private class MyMouseListener extends MouseAdapter {

        /**
         * Tries to play the game in clicked mouse location and updates game info.
         * @param e mouse input
         */
        @Override
        public void mouseClicked( MouseEvent e) {
            int col;
            int row;

            col = e.getX() / BoardCanvas.BOX_SIZE;
            row = (8 - 1) - e.getY() / BoardCanvas.BOX_SIZE;

            System.out.println("X: " + col);
            System.out.println("Y: " + row);

            gameBoard.select(new Point(col, row));

            toggleColor(gameBoard.whiteTurn);

            gameGrid.repaint();
            checkGameWin();

        }
    }
}