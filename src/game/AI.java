package game;


import java.util.List;

public class AI {
    private static final int MAX_DEPTH = 10;
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

        System.out.println("Current Val: " + initial.evaluateBoard());
        String move = getBestMove(initial, true, MAX_DEPTH, -999999, 999999).bestMove;

        System.out.println("BEST VALUE FOUND IS: " + initial.value);
        System.out.println("AI PLAYING");
        System.out.println(move);
        gameBoard.aiPlay(move, gameBoard.board, true);

        System.out.println("----------------");


        return true;
    }

    // TODO: 21.02.2020 Implement pruning
    private State getBestMove(State state, boolean max, int depth, int alpha, int beta) {
        if(depth == 0) {
            state.value = state.evaluateBoard();
            return state;
        }
        else {

            List<String> poss = gameBoard.calculateMoves(state.board);
            if(poss.size() <= 1) {
                if(!poss.isEmpty()) {
                    if(depth == MAX_DEPTH) {
                        state.bestMove = poss.get(0);
                        return state;
                    }
                }
                else {
                    state.value = state.evaluateBoard();
                    return state;
                }
            }



            if(max) {
                state.value = -9999999;
                for (String possibleMove: poss) {
                    //New board for child
                    Piece[][] newBoard = new Piece[state.board.length][];
                    for (int i = 0; i < state.board.length; i++)
                        newBoard[i] = state.board[i].clone();

                    gameBoard.aiPlay(possibleMove, newBoard, true);
                    State childState = new State(newBoard);

                    gameBoard.changeTurn();
                    getBestMove(childState, false, depth - 1, alpha, beta);
                    gameBoard.changeTurn();

                    if ((childState.value > state.value)) {
                        state.value = childState.value;
                        state.bestMove = possibleMove;
                    }

                    alpha = Math.max(alpha, state.value);
                    if(beta <= alpha) {
                        break;
                    }

                }
            } else {
                state.value = 9999999;
                for (String possibleMove: poss) {
                    //New board for child
                    Piece[][] newBoard = new Piece[state.board.length][];
                    for (int i = 0; i < state.board.length; i++)
                        newBoard[i] = state.board[i].clone();

                    gameBoard.aiPlay(possibleMove, newBoard, false);
                    State childState = new State(newBoard);

                    gameBoard.changeTurn();
                    getBestMove(childState, true, depth - 1, alpha, beta);
                    gameBoard.changeTurn();

                    if ((childState.value < state.value)) {
                        state.value = childState.value;
                        state.bestMove = possibleMove;
                    }
                    beta = Math.min(beta, state.value);
                    if(beta <= alpha) {
                        break;
                    }

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
            int weight = 0;
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    if(board[i][j] == Piece.black) {
                        weight = ((6 - j) * 10) / 6;
                        counter += 10 + weight;
                    }
                    else if(board[i][j] == Piece.white) {
                        weight = ((j - 1) * 10) / 6;
                        counter -= (10 + weight);
                    }
                    else if(board[i][j] == Piece.BLACK) {
                        counter += 50;
                    }
                    else if(board[i][j] == Piece.WHITE) {
                        counter -= 50;
                    }
                }
            }
            //if (counter == 0)
            return counter;
        }
    }
}