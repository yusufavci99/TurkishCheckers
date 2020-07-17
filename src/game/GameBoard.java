package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameBoard {
    static Piece[][] board = {

            {Piece.empty,Piece.white,Piece.white,Piece.empty,Piece.empty,Piece.black,Piece.black,Piece.empty},
            {Piece.empty,Piece.white,Piece.white,Piece.empty,Piece.empty,Piece.black,Piece.black,Piece.empty},
            {Piece.empty,Piece.white,Piece.white,Piece.empty,Piece.empty,Piece.black,Piece.black,Piece.empty},
            {Piece.empty,Piece.white,Piece.white,Piece.empty,Piece.empty,Piece.black,Piece.black,Piece.empty},
            {Piece.empty,Piece.white,Piece.white,Piece.empty,Piece.empty,Piece.black,Piece.black,Piece.empty},
            {Piece.empty,Piece.white,Piece.white,Piece.empty,Piece.empty,Piece.black,Piece.black,Piece.empty},
            {Piece.empty,Piece.white,Piece.white,Piece.empty,Piece.empty,Piece.black,Piece.black,Piece.empty},
            {Piece.empty,Piece.white,Piece.white,Piece.empty,Piece.empty,Piece.black,Piece.black,Piece.empty}
    };

    AI opponent;
    boolean whiteTurn = true;
    Point selected;
    List<Point> highlighted = new ArrayList<>();

    List<String> highlightM = new ArrayList<>();
    boolean locked = false;


    public GameBoard() {
        opponent = new AI(this);
        System.out.println(calculateMoves());
    }

    public boolean isAITurn() {
        return !whiteTurn;
    }

    public Piece getPiece(int x , int y) {
        //turn the board
        return board[x][y];
    }

    List<String> calculateMoves() {

        int direction;
        if(whiteTurn) {
            direction = 1;
        }
        else {
            direction = -1;
        }

        MoveList possibles = new MoveList();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                Piece piece = board[x][y];

                if(turnCheck(piece)) {
                    moveCheck(x, y, direction, possibles);
                }

            }
        }
        return possibles.getUpdated();
    }

    // TODO: 21.02.2020 Fix Duplication
    List<String> calculateMoves(Piece[][] useBoard) {
        Piece[][] temp = board;
        board = useBoard;

        int direction;
        if(whiteTurn) {
            direction = 1;
        }
        else {
            direction = -1;
        }

        MoveList possibles = new MoveList();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                Piece piece = board[x][y];

                if(turnCheck(piece)) {
                    moveCheck(x, y, direction, possibles);
                }

            }
        }

        board = temp;
        return possibles.getUpdated();
    }

    List<Point> getPoints(List<String> moves) {
        List<Point> movesx = new ArrayList<>();

        if(!moves.isEmpty()) {
            for (String move : moves) {
                for (int i = 0; i < move.length(); i++) {
                    if (move.charAt(i) == '-') {
                        System.out.println("MOVE: " + move);
                        System.out.println(move.charAt(i + 2) - '1');
                        movesx.add(new Point(move.charAt(i + 1) - 'A', move.charAt(i + 2) - '1'));
                        break;
                    }
                }
            }
        }

        return movesx;
    }

    List<String> moveCheck(int x, int y) {
        MoveList possibles = new MoveList();
        int direction;
        if(whiteTurn) {
            direction = 1;
        }
        else {
            direction = -1;
        }
        System.out.println("Direction:" + direction);
        moveCheck(x, y, direction, possibles);

        List<String> moves = possibles.getUpdated();
        List<String> allPossible = calculateMoves();



        List<String> depthFiltered = moves.stream()
                .distinct()
                .filter(allPossible::contains)
                .collect(Collectors.toList());



        return depthFiltered;
    }

    List<Point> eatCheck(int x, int y) {
        MoveList possibles = new MoveList();
        int direction;
        if(whiteTurn) {
            direction = 1;
        }
        else {
            direction = -1;
        }
        eatCheck(x, y, direction, possibles);

        List<String> moves = possibles.getUpdated();
        List<Point> movesx = new ArrayList<>();

        if(!moves.isEmpty()) {
            for (String move : moves) {
                for (int i = 0; i < move.length(); i++) {
                    if (move.charAt(i) == '-') {
                        System.out.println("MOVE: " + move);
                        System.out.println(move.charAt(i + 2) - '1');
                        movesx.add(new Point(move.charAt(i + 1) - 'A', move.charAt(i + 2) - '1'));
                        break;
                    }
                }
            }
        }

        return movesx;
    }

    void moveCheck(int x, int y, int direction, MoveList possibles) {

        if(PieceControl.isDama(board[x][y])) {
            if(!eatCheckDama(x, y, possibles, "", 0, -1)) {
                for(int i = 1; x - i >= 0; i++) {
                    if(board[x - i][y] == Piece.empty) {
                        possibles.add(nameConstructor(x, y, x - i, y, ""), 0);
                    }
                    else {
                        break;
                    }
                }
                for(int i = 1; x + i <= 7; i++) {
                    if(board[x + i][y] == Piece.empty) {
                        possibles.add(nameConstructor(x, y, x + i, y, ""), 0);
                    }
                    else {
                        break;
                    }
                }
                for(int i = 1; y - i >= 0; i++) {
                    if(board[x][y - i] == Piece.empty) {
                        possibles.add(nameConstructor(x, y, x, y - i, ""), 0);
                    }
                    else {
                        break;
                    }
                }
                for(int i = 1; y + i <= 7; i++) {
                    if(board[x][y + i] == Piece.empty) {
                        possibles.add(nameConstructor(x, y, x, y + i, ""), 0);
                    }
                    else {
                        break;
                    }
                }

            }
        }
        else if(PieceControl.isNormal(board[x][y])) {
            if(!eatCheck(x, y, direction, possibles, "", 0)) {
                if(x > 0 && board[x - 1][y] == Piece.empty) {
                    possibles.add(nameConstructor(x, y, x - 1, y, ""), 0);
                }
                if(x < 7 && board[x + 1][y] == Piece.empty) {
                    possibles.add(nameConstructor(x, y, x + 1, y, ""), 0);
                }

                try {
                    if (board[x][y + direction] == Piece.empty) {
                        possibles.add(nameConstructor(x, y, x, y + direction, ""), 0);
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    void eatCheck(int x, int y, int direction, MoveList possibles) {

        if(PieceControl.isDama(board[x][y])) {
            if(!eatCheckDama(x, y, possibles, "", 0, -1)) {
                System.out.println("HELLO");

            }
        }
        else if(PieceControl.isNormal(board[x][y])) {
            eatCheck(x, y, direction, possibles, "", 0);
        }
    }

    boolean eatCheck(int x, int y, int direction, MoveList possibles, String current, int depth) {

        boolean last = true;
        // Check left
        if( x >= 2 && (board[x - 2][y] == Piece.empty) && PieceControl.isRival(board[x][y],board[x - 1][y])) {
            board[x - 2][y] = board[x][y];
            Piece temp = board[x - 1][y];
            board[x][y] = Piece.empty;
            board[x - 1][y] = Piece.empty;

            eatCheck(x - 2, y, direction, possibles, nameConstructor(x, y, x - 2, y, current),depth + 1);

            board[x][y] = board[x - 2][y];
            board[x - 2][y] = Piece.empty;
            board[x - 1][y] = temp;
            last = false;

        }

        // Check right
        if( x <= 5 && (board[x + 2][y] == Piece.empty) && PieceControl.isRival(board[x][y],board[x + 1][y])) {
            board[x + 2][y] = board[x][y];
            Piece temp = board[x + 1][y];
            board[x][y] = Piece.empty;
            board[x + 1][y] = Piece.empty;

            eatCheck(x + 2, y, direction, possibles, nameConstructor(x, y, x + 2, y, current), depth + 1);

            board[x][y] = board[x + 2][y];
            board[x + 2][y] = Piece.empty;
            board[x + 1][y] = temp;
            last = false;
        }

        // Check vertical
        if((direction == 1 && y > 5) || (direction == -1 && y < 2)) {

        }
        else if( (board[x][y + (2 * (direction))] == Piece.empty) &&
                PieceControl.isRival(board[x][y], board[x][y + direction])) {
            board[x][y + (2 * direction)] = board[x][y];
            Piece temp = board[x][y + direction];
            board[x][y] = Piece.empty;
            board[x][y + direction] = Piece.empty;


            eatCheck(x, y + (2 * direction), direction, possibles, nameConstructor(x, y, x, y + (2 * direction), current), depth + 1);

            board[x][y] = board[x][y + (2 * direction)];
            board[x][y + (2 * direction)] = Piece.empty;
            board[x][y + direction] = temp;

            last = false;
        }

        if(last) {
            possibles.add(current, depth);
        }

        return !last;
    }

    boolean eatCheckDama(int x, int y, MoveList possibles, String current, int depth, int disabled) {
        boolean last = true;

        // Check left

        if(disabled != 0) {
            int toLeft = 1;
            while (x - toLeft >= 1) {
                if(PieceControl.isRival(board[x][y], board[x - toLeft][y])) {
                    if ((board[x - (toLeft + 1)][y] == Piece.empty)) {

                        for (int leftEatPosition = toLeft + 1; (x - leftEatPosition >= 0)
                                && (board[x - leftEatPosition][y]) == Piece.empty; leftEatPosition++) {
                            board[x - leftEatPosition][y] = board[x][y];
                            Piece temp = board[x - toLeft][y];
                            board[x][y] = Piece.empty;
                            board[x - toLeft][y] = Piece.empty;

                            eatCheckDama(x - leftEatPosition, y, possibles,
                                    nameConstructor(x, y, x - leftEatPosition, y, current), depth + 1, 1);

                            board[x][y] = board[x - leftEatPosition][y];
                            board[x - leftEatPosition][y] = Piece.empty;
                            board[x - toLeft][y] = temp;
                        }

                        last = false;

                    }
                    break;
                }

                toLeft++;
            }
        }

        // Check right
        if(disabled != 1) {
            int toRight = 1;
            while (x + toRight <= 6) {
                if(PieceControl.isRival(board[x][y], board[x + toRight][y])) {
                    if ((board[x + (toRight + 1)][y] == Piece.empty)) {

                        for (int rightEatPosition = toRight + 1; (x + rightEatPosition <= 7)
                                && (board[x + rightEatPosition][y]) == Piece.empty; rightEatPosition++) {
                            board[x + rightEatPosition][y] = board[x][y];
                            Piece temp = board[x + toRight][y];
                            board[x][y] = Piece.empty;
                            board[x + toRight][y] = Piece.empty;

                            eatCheckDama(x + rightEatPosition, y, possibles,
                                    nameConstructor(x, y, x + rightEatPosition, y, current), depth + 1, 0);

                            board[x][y] = board[x + rightEatPosition][y];
                            board[x + rightEatPosition][y] = Piece.empty;
                            board[x + toRight][y] = temp;
                        }

                        last = false;

                    }
                    break;
                }

                toRight++;
            }
        }

        // Check up
        if(disabled != 2) {
            int toUp = 1;
            while (y + toUp <= 6) {
                if(PieceControl.isRival(board[x][y], board[x][y + toUp])) {
                    if ((board[x][y + (toUp + 1)] == Piece.empty)) {
                        for (int upEatPosition = toUp + 1; (y + upEatPosition <= 7)
                                && (board[x][y + upEatPosition]) == Piece.empty; upEatPosition++) {
                            board[x][y + upEatPosition] = board[x][y];
                            Piece temp = board[x][y + toUp];
                            board[x][y] = Piece.empty;
                            board[x][y + toUp] = Piece.empty;

                            eatCheckDama(x, y + upEatPosition, possibles,
                                    nameConstructor(x, y, x, y + upEatPosition, current), depth + 1, 3);

                            board[x][y] = board[x][y + upEatPosition];
                            board[x][y + upEatPosition] = Piece.empty;
                            board[x][y + toUp] = temp;
                        }

                        last = false;

                    }
                    break;
                }

                toUp++;
            }
        }

        // Check down
        if(disabled != 3) {
            int toDown = 1;
            while (y - toDown >= 1) {
                if(PieceControl.isRival(board[x][y], board[x][y - toDown])) {
                    if ((board[x][y - (toDown + 1)] == Piece.empty)) {
                        for (int downEatPosition = toDown + 1; (y - downEatPosition >= 0)
                                && (board[x][y - downEatPosition]) == Piece.empty; downEatPosition++) {
                            board[x][y - downEatPosition] = board[x][y];
                            Piece temp = board[x][y - toDown];
                            board[x][y] = Piece.empty;
                            board[x][y - toDown] = Piece.empty;

                            eatCheckDama(x, y - downEatPosition, possibles,
                                    nameConstructor(x, y, x, y - downEatPosition, current), depth + 1, 2);

                            board[x][y] = board[x][y - downEatPosition];
                            board[x][y - downEatPosition] = Piece.empty;
                            board[x][y - toDown] = temp;
                        }

                        last = false;
                    }
                    break;
                }

                toDown++;
            }
        }

        if(last) {
            possibles.add(current, depth);
        }

        return !last;
    }

    // Maps nums 1 to 8 to letters A to H.
    public static char numToLetter(int number) {
        return (char)((int)'A' + number);
    }

    public String nameConstructor(int oldX, int oldY, int newX, int newY, String current) {
        oldY++;
        newY++;

        if(current.isEmpty()) {
            return current + numToLetter(oldX) + oldY + "-" + numToLetter(newX) + newY;
        }
        else  {
            return current + "&"+ numToLetter(oldX) + oldY + "-" + numToLetter(newX) + newY;
        }
    }

    public boolean turnCheck(Piece piece) {
        if(whiteTurn) {
            return PieceControl.isWhite(piece);
        }
        else {
            return PieceControl.isBlack(piece);
        }
    }

    enum PlayResult {
        PLAYED, ATE, NO;
    }

    PlayResult tryToPlay(Point clicked) {
        for (Point point : highlighted) {
            if (point.x == clicked.x && point.y == clicked.y) {
                if(play(selected, clicked)) {
                    return PlayResult.ATE;
                }
                else {
                    return PlayResult.PLAYED;
                }
            }
        }
        return PlayResult.NO;
    }

    public void select(Point clicked, BoardCanvas gameGrid) {

        PlayResult playResult = tryToPlay(clicked);

        if(playResult != PlayResult.NO) {

            trimFirst(clicked.x, clicked.y);
            highlighted = getPoints(highlightM);

            if(!highlighted.isEmpty() && playResult == PlayResult.ATE) {
                selected = clicked;
                locked = true;
            }
            else {
                locked = false;
                if(board[clicked.x][clicked.y] == Piece.white && clicked.y == 7) {
                    board[clicked.x][clicked.y] = Piece.WHITE;
                }
                else if(board[clicked.x][clicked.y] == Piece.black && clicked.y == 0) {
                    board[clicked.x][clicked.y] = Piece.BLACK;
                }
                selected = null;
                highlighted = new ArrayList<>();
                gameGrid.repaint();
                changeTurn();

                opponent.play();

                changeTurn();
            }
        }
        else if(!locked) {
            if (turnCheck(board[clicked.x][clicked.y])) {
                selected = new Point(clicked.x, clicked.y);
                System.out.println("TURN CHECK FOR " + selected);

                highlightM = moveCheck(clicked.x ,clicked.y);
                highlighted = getPoints(highlightM);

                System.out.println(highlighted);
            } else {
                selected = null;
                highlighted = new ArrayList<>();
            }
            if (highlighted.isEmpty()) {
                selected = null;
            }
        }
    }

    void changeTurn() {
        if(whiteTurn){
            whiteTurn = false;
        }
        else {
            whiteTurn = true;
        }
    }

    private boolean play(Point selected, Point clicked) {
        board[clicked.x][clicked.y] = board[selected.x][selected.y];
        board[selected.x][selected.y] = Piece.empty;
        if(selected.x == clicked.x) {
            for(int i = selected.y + 1; i < clicked.y; i++) {
                if(board[clicked.x][i] != Piece.empty) {
                    board[clicked.x][i] = Piece.empty;
                    return true;
                }
            }
            for(int i = clicked.y + 1; i < selected.y; i++) {
                if(board[clicked.x][i] != Piece.empty) {
                    board[clicked.x][i] = Piece.empty;
                    return true;
                }
            }
        }
        else {
            for(int i = selected.x + 1; i < clicked.x; i++) {
                if(board[i][clicked.y] != Piece.empty) {
                    board[i][clicked.y] = Piece.empty;
                    return true;
                }
            }
            for(int i = clicked.x + 1; i < selected.x; i++) {
                if(board[i][clicked.y] != Piece.empty) {
                    board[i][clicked.y] = Piece.empty;
                    return true;
                }
            }
        }
        return false;
    }

    // TODO: 21.02.2020 Fix Duplication 
    public void aiPlay(String move, Piece[][] useBoard, boolean black) {
        Piece[][] temp = board;
        board = useBoard;

        if(move.length() == 5) {
            int x1 = move.charAt(0) - 'A';
            int y1 = move.charAt(1) - '1';
            int x2 = move.charAt(3) - 'A';
            int y2 = move.charAt(4) - '1';

            if(y2 == 0 && black) {
                board[x2][y2] = Piece.BLACK;
            }
            else if(y2 == 7 && !black) {
                board[x2][y2] = Piece.WHITE;
            }
            else {
                board[x2][y2] = board[x1][y1];
            }
            board[x1][y1] = Piece.empty;
            if(Math.abs(x2 - x1) > 1) {
                int min, max;
                if( x2 > x1) {
                    max = x2;
                    min = x1;
                }
                else {
                    max = x1;
                    min = x2;
                }
                for (int i= min + 1;i < max; i++) {
                    board[i][y1] = Piece.empty;
                }
            }
            else if(Math.abs(y2 - y1) > 1) {
                int min, max;
                if( y2 > y1) {
                    max = y2;
                    min = y1;
                }
                else {
                    max = y1;
                    min = y2;
                }
                for (int i = min + 1;i < max; i++) {
                    board[x1][i] = Piece.empty;
                }
            }

        }
        else {
            String[] composite = move.split("&");
            for (String s : composite) {
                aiPlay(s, useBoard, black);
            }
        }
        board = temp;
    }

    public boolean isHighlighted(int x, int y) {
        for(Point point: highlighted) {
            if(point.x == x && point.y == y ) {
                return true;
            }
        }
        return false;
    }

    public void trimFirst(int x, int y) {
        List<String> newHighlight = new ArrayList<>();
        for(String move: highlightM) {
            String str = move.substring(move.indexOf("&") + 1);
            if(str.length() != 0 && (str.charAt(0) - 'A') == x && (str.charAt(1) - '1') == y) {
                newHighlight.add(str);
            }
        }

        highlightM = newHighlight;
    }
}