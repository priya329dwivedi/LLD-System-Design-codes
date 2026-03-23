/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.FactoryPattern.practiceFactoryPattern.DocumentParserFactory.ConcreteClass;

import org.designpattern.FactoryPattern.practiceFactoryPattern.DocumentParserFactory.DocumentParser;

public class PdfParser implements DocumentParser {
    @Override
    public void parse(String fileName) {
        System.out.println("Parsing PDF document: " + fileName);
    }
}
