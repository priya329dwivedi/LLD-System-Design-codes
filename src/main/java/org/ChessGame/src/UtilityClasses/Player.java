package org.ChessGame.src.UtilityClasses;

import lombok.Getter;

@Getter
public class Player {
    private String name;
    private boolean isWhiteSide;
    public Player(String name, boolean isWhiteSide){
        this.name=name;
    }
}
