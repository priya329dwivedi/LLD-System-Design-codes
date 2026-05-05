package concepts.selfPractice.SnakeFoodGame.model;

import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Getter
public class Snake {
    private final LinkedList<Position> body;
    private final Set<Position> bodySet;

    public Snake(Position start){
        this.body= new LinkedList<>();
        this.bodySet= new HashSet<>();
        body.addFirst(start);
        bodySet.add(start);
    }

    public void move(Position newHead){
        Position tail = body.removeLast();
        bodySet.remove(tail);
        body.addFirst(newHead);
        bodySet.add(newHead);
    }

    public void grow(Position newHead){
        body.addFirst(newHead);
        bodySet.add(newHead);
    }

    public boolean selfHit(Position pos){
       return bodySet.contains(pos);
    }

    public Position getHead(){
        return body.getFirst();
    }

}
