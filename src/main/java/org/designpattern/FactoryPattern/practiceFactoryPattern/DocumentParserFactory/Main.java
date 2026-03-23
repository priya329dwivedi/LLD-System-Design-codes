/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.FactoryPattern.practiceFactoryPattern.DocumentParserFactory;

public class Main {
    public static void main(String[] args) {
        DocumentParser pdfParser = DocumentParserFactory.getParser("pdf");
        pdfParser.parse("report.pdf");

        DocumentParser wordParser = DocumentParserFactory.getParser("word");
        wordParser.parse("resume.docx");

        DocumentParser csvParser = DocumentParserFactory.getParser("csv");
        csvParser.parse("data.csv");
    }
}
