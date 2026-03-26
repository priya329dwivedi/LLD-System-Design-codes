/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceQuestions.BookMyShow.models;

import java.util.UUID;

public class Movie {
    String name;
    String id;
    long duration;

    public Movie(String name, String id, long duration) {
        this.name = name;
        this.id = UUID.randomUUID().toString().substring(0,8);
        this.duration = duration;
    }
}
