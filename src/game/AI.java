package game;


import java.util.List;

public class AI {
    private static final int MAX_DEPTH = 5;
    private GameBoard gameBoard;

    public AI(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

     boolean play() {
        if(!gameBoard.isAITurn()) {
            return false;
        }

        Piece[][] newBoard = new Piece[gameBoard.board.length][];
        for (int i = 0; i < gameBoard.board.length; i++)
            newBoard[i] = gameBoard.board[i].clone();

        State initial = new State( newBoard);

        String move = getBestMove(initial, true, 0).bestMove;

        System.out.println("AI PLAYING");
        System.out.println(move);
        gameBoard.aiPlay(move, gameBoard.board, true);

        System.out.println("----------------");


        return true;
    }

    // TODO: 21.02.2020 Implement pruning
    private State getBestMove(State state, boolean max, int depth) {
        state.value = -21;

        if(depth == MAX_DEPTH) {
            state.value = state.evaluateBoard();
            return state;
        }
        else {

            List<String> poss = gameBoard.calculateMoves(state.board);
            for (String possibleMove : poss) {

                //New board for child
                Piece[][] newBoard = new Piece[state.board.length][];
                for (int i = 0; i < state.board.length; i++)
                    newBoard[i] = state.board[i].clone();

                gameBoard.aiPlay(possibleMove, newBoard,max);
                State childState = new State(newBoard);

                gameBoard.changeTurn();
                getBestMove(childState, !max, depth + 1);
                gameBoard.changeTurn();

                System.out.println("this: " + childState.value);
                if (childState.value >= state.value && max) {
                    state.bestMove = possibleMove;
                    state.value = childState.value;
                } else if (((childState.value <= state.value) || state.value == -21) && !max) {
                    state.bestMove = possibleMove;
                    state.value = childState.value;
                }

            }
        }
        return state;
    }

    private class State {
        String bestMove;
        final Piece[][] board;
        int value;

        public State(Piece[][] board) {
            this.board = board;
        }

        public int evaluateBoard() {
            int counter = 0;
            for(Piece[] px: board) {
                for(Piece p: px) {
                    if(p == Piece.black) {
                        counter++;
                    }
                    else if(p == Piece.BLACK) {
                        counter += 5;
                    }
                }
            }
            return counter;
        }
    }
}