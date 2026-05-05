package concepts.practiceQuestions.SnakeGame;

import concepts.practiceQuestions.SnakeGame.model.Position;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Snake {
    // LinkedList as Deque: addFirst (new head) and removeLast (old tail) are both O(1)
    private final LinkedList<Position> body;
    // Parallel Set for O(1) self-collision detection instead of O(n) list scan
    private final Set<Position> bodySet;

    public Snake(Position start) {
        body    = new LinkedList<>();
        bodySet = new HashSet<>();
        body.addFirst(start);
        bodySet.add(start);
    }

    // Move without eating: slide forward — add new head, drop tail
    public void move(Position newHead) {
        Position tail = body.removeLast();
        bodySet.remove(tail);
        body.addFirst(newHead);
        bodySet.add(newHead);
    }

    // Move after eating: add new head, keep tail (snake grows by 1)
    public void grow(Position newHead) {
        body.addFirst(newHead);
        bodySet.add(newHead);
    }

    public boolean occupies(Position pos) { return bodySet.contains(pos); }
    public Position getHead()             { return body.getFirst(); }
    public int      size()                { return body.size(); }
    public Set<Position> getBodySet()     { return bodySet; }

    @Override
    public String toString() { return "Snake" + body; }
}
