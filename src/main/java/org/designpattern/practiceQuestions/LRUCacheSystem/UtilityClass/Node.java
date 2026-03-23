/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceQuestions.LRUCacheSystem.UtilityClass;


public class Node {
    int key;
    int value;

    public Node(int key,int value) {
        this.key = key;
        this.value = value;
    }
}
