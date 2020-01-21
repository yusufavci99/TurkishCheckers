package game;

public class Game {
    private static Game game;
    private GameBoard gameBoard;

    private Game () {
        game = this;
        initGame();
    }

    private void initGame() {
        this.gameBoard = new GameBoard();
    }

    public static Game getGame() {
        if(game == null) {
            return new Game();
        }
        else {
            return game;
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
