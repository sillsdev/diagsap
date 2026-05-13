/**
 * Copyright (c) 2016-2026 SIL Global
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */
package org.sil.diagsap.model;

import javafx.scene.paint.Color;

/**
 * @author Andy Black
 * Singleton pattern for font information for lexical nodes
 */
public class LexFontInfo extends FontInfo {

    private static LexFontInfo instance;
    
    private LexFontInfo(){
    	super("Arial", 12.0, "Regular");
    	setColor(Color.BLACK);
    }
    
    public static LexFontInfo getInstance(){
        if(instance == null){
            instance = new LexFontInfo();
        }
        return instance;
    }
}
