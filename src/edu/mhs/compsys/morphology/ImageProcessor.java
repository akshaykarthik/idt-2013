package edu.mhs.compsys.morphology;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageProcessor {
	
	
	public static ArrayList<Integer[]> findIn(BufferedImage bigImage, BufferedImage search){
		ArrayList<Integer[]> locs;
			for(int y = 0; y<bigImage.getHeight()-search.getHeight(); y++){
				for(int x = 0; x<bigImage.getWidth() - search.getWidth();x++){
					
				}
				
			}
		
		return locs;
	}
	
	public float imageDist(Image bigImg, Image smallImg){
		float dist = 0.0;
		for(int y = 0; y<smallImg.getHeight(); y++){
			for(int x = 0; x< smallImg.getWidth(); x++){
				
			}
		}
		
		
		return Math.sqrt()
	}
	
}
