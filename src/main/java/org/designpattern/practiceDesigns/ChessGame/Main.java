/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.ChessGame;

import org.designpattern.practiceDesigns.ChessGame.UtilityClasses.Player;

public class Main {
    public static void main(String[] args) {
        Player pruna = new Player("Pruna",true);
        Player pruni = new Player("Pruni",false);
        ChessGame chessGame = new ChessGame(pruna,pruni);
        chessGame.startGame();
    }
}
