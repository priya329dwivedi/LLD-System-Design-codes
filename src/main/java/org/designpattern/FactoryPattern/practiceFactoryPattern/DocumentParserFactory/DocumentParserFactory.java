/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.FactoryPattern.practiceFactoryPattern.DocumentParserFactory;

import org.designpattern.FactoryPattern.practiceFactoryPattern.DocumentParserFactory.ConcreteClass.CsvParser;
import org.designpattern.FactoryPattern.practiceFactoryPattern.DocumentParserFactory.ConcreteClass.PdfParser;
import org.designpattern.FactoryPattern.practiceFactoryPattern.DocumentParserFactory.ConcreteClass.WordParser;

public class DocumentParserFactory {
    public static DocumentParser getParser(String fileType){
        if(fileType.equals("pdf")){
            return new PdfParser();
        }
        else if(fileType.equals("word")){
            return new WordParser();
        }
        else if(fileType.equals("csv")){
            return new CsvParser();
        }
        else{
            throw new IllegalArgumentException("Unknown document type: " + fileType);
        }
    }
}
