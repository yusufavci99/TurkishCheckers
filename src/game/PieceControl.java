package game;

public class PieceControl {
    public static boolean isWhite(Piece piece) {
        return (piece == Piece.white || piece == Piece.WHITE);
    }
    public static boolean isBlack(Piece piece) {
        return (piece == Piece.black || piece == Piece.BLACK);
    }
    public static boolean isDama(Piece piece) {
        return (piece == Piece.WHITE || piece == Piece.BLACK);
    }
    public static boolean isNormal(Piece piece) {
        return (piece == Piece.black || piece == Piece.white);
    }
    public static boolean isRival(Piece piece, Piece maybeRival) {
        return (isWhite(piece) && isBlack(maybeRival)) || (isBlack(piece) && isWhite(maybeRival));
    }
}
