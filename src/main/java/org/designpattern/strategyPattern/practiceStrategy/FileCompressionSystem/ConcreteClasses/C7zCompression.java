/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.strategyPattern.practiceStrategy.FileCompressionSystem.ConcreteClasses;

import org.designpattern.strategyPattern.practiceStrategy.FileCompressionSystem.CompressionStreategyInterface;

public class C7zCompression implements CompressionStreategyInterface {
    @Override
    public void compressionFile(String file) {
        System.out.println("File is compressed using 7z" + file);
    }
}
