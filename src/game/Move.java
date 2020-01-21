package game;

import java.awt.*;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Move {
    Node move;
    Node endPoint;

    public Move(int oldX, int oldY, int newX, int newY) {
        move = new Node(oldX, oldY, newX, newY);
        endPoint = move;
    }

    public Move() {
    }

    private class Node{
        int x;
        int y;
        int nX;
        int nY;

        Node next;

        private Node(int x, int y, int nX, int nY) {
            this.x = x;
            this.y = y;
            this.nX = nX;
            this.nY = nY;
        }
    }

    void addStep(int oldX, int oldY, int newX, int newY) {
        endPoint.next = new Node(oldX, oldY, newX, newY);
        endPoint = endPoint.next;
    }

    void reset(int oldX, int oldY, int newX, int newY) {
        move = new Node(oldX, oldY, newX, newY);
        endPoint = move;
    }

    boolean isEmpty() {
        return (move == null);
    }

    Point getFirstStep() {
        return  new Point(move.nX, move.nY);
    }

}
