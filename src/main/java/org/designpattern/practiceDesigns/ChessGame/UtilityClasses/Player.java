/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.ChessGame.UtilityClasses;

import java.util.UUID;

public class Player {
    String name;
    String id;
    boolean isWhite;

    public Player(String name, boolean isWhite) {
        this.name = name;
        this.id = UUID.randomUUID().toString().substring(0,8);
        this.isWhite = isWhite;
    }
}
