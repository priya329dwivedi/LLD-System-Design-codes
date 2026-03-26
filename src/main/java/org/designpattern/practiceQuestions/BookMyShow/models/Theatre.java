/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceQuestions.BookMyShow.models;

import java.util.ArrayList;
import java.util.List;

public class Theatre {
    String id;
    String name;
    List<Screen> screens;

    public Theatre(String id, String name) {
        this.id = id;
        this.name = name;
        this.screens= new ArrayList<>();
    }
}
