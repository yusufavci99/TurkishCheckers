package game;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MoveList {

    private List<Pair<String, Integer>> moves = new ArrayList<>();
    private List<String> movesList = new ArrayList<>();
    private boolean changed = false;
    private int depth = 0;

    public boolean add(String s, int depth) {
        if(depth >= this.depth) {
            this.depth = depth;
            changed = true;
            return moves.add(new Pair<>(s,depth));
        }
        return false;
    }

    public List<String> getUpdated() {

        if(changed) {
            movesList = new ArrayList<>();
            for (Pair move : moves) {
                String aMove = (String) move.getKey();
                if ((int) (move.getValue()) >= depth && !aMove.isEmpty()) {
                    movesList.add(aMove);
                }
            }
            changed = false;
        }
        return movesList;
    }
}
