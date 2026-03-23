/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.strategyPattern.practiceStrategy.FileCompressionSystem;

import lombok.Setter;

import java.util.UUID;

public class File {
    @Setter
    CompressionStreategyInterface compressionStrategy;
    String fileContent;
    UUID fileId;
    public File(CompressionStreategyInterface compressionStrategy, String Content){
        this.fileId= new UUID(5,5);
        this.fileContent= Content;
        this.compressionStrategy=compressionStrategy;
    }
    public void compressFile(){
        compressionStrategy.compressionFile(fileContent);
    }
}
