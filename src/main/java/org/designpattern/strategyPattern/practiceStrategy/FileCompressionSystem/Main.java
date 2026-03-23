/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.strategyPattern.practiceStrategy.FileCompressionSystem;

import org.designpattern.strategyPattern.practiceStrategy.FileCompressionSystem.ConcreteClasses.C7zCompression;
import org.designpattern.strategyPattern.practiceStrategy.FileCompressionSystem.ConcreteClasses.RARCompression;
import org.designpattern.strategyPattern.practiceStrategy.FileCompressionSystem.ConcreteClasses.ZipCompressionStretegy;

public class Main {
    public static void main(String[] args) {
        RARCompression rarCompression= new RARCompression();
        C7zCompression c7zCompression= new C7zCompression();
        ZipCompressionStretegy zipCompression= new ZipCompressionStretegy();
        File file= new File(rarCompression," :compression Successful !!!");
        file.compressFile();

        // Switch strategy at runtime to 7z
        file.setCompressionStrategy(c7zCompression);
        file.compressFile();

        // Switch strategy at runtime to ZIP
        file.setCompressionStrategy(zipCompression);
        file.compressFile();
    }
}
