package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class BoardCanvas extends JPanel {

    // properties
    final Color BACKGROUND_COLOR = new Color( 146, 168, 209);
    final Color RECTANGLE_COLOR_DARK = new Color( 0, 0, 100);
    final Color RECTANGLE_COLOR_LIGHT = new Color( 213, 255, 237);
    final Color TEXT_COLOR = new Color( 253, 102, 0);
    final static int BOX_SIZE = 120;




    GameBoard gameBoard;
    int dimension;

    private BufferedImage blackPiece;
    private BufferedImage whitePiece;
    private BufferedImage blackDamaPiece;
    private BufferedImage whiteDamaPiece;

    // constructors

    public BoardCanvas( GameBoard gameBoard) {
//        sosGame = inSOS;

        try {
            URL imgUrl = getClass().getClassLoader().getResource("images/siyah.png");
            blackPiece = ImageIO.read(imgUrl);
            imgUrl = getClass().getClassLoader().getResource("images/beyaz.png");
            whitePiece = ImageIO.read(imgUrl);
            imgUrl = getClass().getClassLoader().getResource("images/beyazDama.png");
            whiteDamaPiece = ImageIO.read(imgUrl);
            imgUrl = getClass().getClassLoader().getResource("images/siyahDama.png");
            blackDamaPiece = ImageIO.read(imgUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }



        this.gameBoard = gameBoard;
        dimension = 8;

    }

    // methods
    @Override
    public void paintComponent( Graphics g) {
        super.paintComponent( g);

        g.setColor( BACKGROUND_COLOR);
        g.setFont( new Font("Arial", Font.PLAIN, 32));

        // to paint the background of game
        g.fillRect( 0, 0, dimension * BOX_SIZE, dimension * BOX_SIZE);

        char[] gameChars = {'B' , 'S', 'b', 's'};


        for( int i = 0; i < dimension; i++) {

            for( int j = 0; j < dimension; j++) {
                if((i + j) % 2 == 0) {
                    g.setColor(RECTANGLE_COLOR_LIGHT);
                }
                else {
                    g.setColor(RECTANGLE_COLOR_DARK);
                }
                if(gameBoard.isHighlighted(j, 7 - i)) {
                    g.setColor(g.getColor().darker().darker());
                }

                g.fillRect( j * BOX_SIZE, i * BOX_SIZE,
                        BOX_SIZE, BOX_SIZE);

                g.setColor( TEXT_COLOR);
            }
        }

        g.setColor( TEXT_COLOR);
        for( int i = 0; i < dimension; i++) {

            for( int j = 0; j < dimension; j++) {

                if( gameBoard.getPiece(j, i) == Piece.black) {
                    g.drawImage( blackPiece, (j * BOX_SIZE), ((7 - i) * BOX_SIZE), this);

//                    g.drawChars( gameChars, 3, 1, (j * BOX_SIZE +
//                            BOX_SIZE / 2), ((7 - i) * BOX_SIZE + BOX_SIZE / 2) );
                }
                else if( gameBoard.getPiece(j, i) == Piece.white) {

                    g.drawImage( whitePiece, (j * BOX_SIZE), ((7 - i) * BOX_SIZE), this);

//                    g.drawChars( gameChars, 2, 1, (j * BOX_SIZE +
//                            BOX_SIZE / 2), ((7 - i) * BOX_SIZE + BOX_SIZE / 2) );

                }
                else if( gameBoard.getPiece(j, i) == Piece.WHITE) {

                    g.drawImage( whiteDamaPiece, (j * BOX_SIZE), ((7 - i) * BOX_SIZE), this);
//                    g.drawChars( gameChars, 0, 1, j * BOX_SIZE +
//                            BOX_SIZE / 2, ((7 - i) * BOX_SIZE + BOX_SIZE / 2 ));
                }
                else if( gameBoard.getPiece(j, i) == Piece.BLACK) {
                    g.drawImage( blackDamaPiece, (j * BOX_SIZE), ((7 - i) * BOX_SIZE), this);
//                    g.drawChars( gameChars, 1, 1, j * BOX_SIZE +
//                            BOX_SIZE / 2,  (7 - i) * BOX_SIZE + BOX_SIZE / 2 );
                }

            }

        }
    }
}