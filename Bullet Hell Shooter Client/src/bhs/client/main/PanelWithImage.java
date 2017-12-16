package bhs.client.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class PanelWithImage extends javax.swing.JPanel{
	
	private BufferedImage backgroundImage;
	String desiredImagePart = "";
	
	PanelWithImage(String fileName){
		super();
		try {
			URL url = getClass().getResource(fileName);
			backgroundImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	PanelWithImage(String fileName, String desiredImagePart){
		super();
		this.desiredImagePart = desiredImagePart;
		try {
			URL url = getClass().getResource(fileName);

			backgroundImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void paintComponent(Graphics g) {
		int imageWidth = backgroundImage.getWidth();
		int imageHeight = backgroundImage.getHeight();
		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		if(desiredImagePart == "middle") {
			g.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, imageWidth/2 - (panelWidth * (imageHeight/panelHeight))/2, 0, imageWidth/2 + (panelWidth * (imageHeight/panelHeight))/2, imageHeight, this);
		}else{
			//g.drawImage(backgroundImage, 0, 0, null);
			g.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, 0, 0, imageWidth, imageHeight, this);
		}
		
	}
}
