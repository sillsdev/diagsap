/**
 * Copyright (c) 2021 SIL International
 * This software is licensed under the LGPL, version 2.1 or later
 * (http://www.gnu.org/licenses/lgpl-2.1.html)
 */

package org.sil.diagsap.service;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;

import org.sil.diagsap.Constants;
import org.sil.diagsap.model.DiagSapTree;

/**
 * @author Andy Black
 * Singleton pattern
 */
public class GraphicImageSaver {

    private static GraphicImageSaver instance;
    String sFilePath;
    
    private GraphicImageSaver() {
    }

    public void setFile(File treeFile) throws IOException{
		sFilePath = treeFile.getCanonicalPath();
		if (sFilePath.endsWith("." + Constants.DIAGSAP_DATA_FILE_EXTENSION)) {
			int len = sFilePath.length();
			sFilePath = sFilePath.substring(0, len - 4);
		}
    }
    
    public static GraphicImageSaver getInstance(){
        if(instance == null){
            instance = new GraphicImageSaver();
        }
        return instance;
    }
    
    public void saveAsSVG(TreeDrawer drawer) throws IOException {
		StringBuilder sb = drawer.drawAsSVG();
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				sFilePath + ".svg"), "UTF-8"));
		try {
			out.write(sb.toString());
		} finally {
			out.close();
		}
    }
    
    public void saveAsPNG(Pane drawingArea, DiagSapTree dsTree) {
		WritableImage wim = new WritableImage((int) dsTree.getXSize() + 10,
				(int) (dsTree.getYSize() + 10));
		drawingArea.snapshot(null, wim);
		File filePng = new File(sFilePath + ".png");
		try {
			BufferedImage bi = SwingFXUtils.fromFXImage(wim, null);
			ImageIO.write(bi, "png", filePng);
		} catch (Exception s) {
			System.out.println("saveAsPNG exception: " + s.getMessage());
		}
    }

}
