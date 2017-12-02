package bhs.client.main;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

import javax.swing.JPanel;

public class PanelWithDrawing extends JPanel{

	BasicStroke stroke = new BasicStroke(2.0f);
	Color color = new Color(255,255,255);
	
	public PanelWithDrawing() {
		System.out.println("panel with drawing created");
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("painting...");
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(stroke);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		
		g2.setColor(color);
				
		int xPoints[] = {panelWidth/2 - 30, panelWidth/2, panelWidth/2 + 90, panelWidth/2};
		int yPoints[] = {panelHeight/2, panelHeight/2-30, panelHeight/2, panelHeight/2+30};

		g2.fillPolygon(xPoints, yPoints, xPoints.length);
	}
	
	public void setAvatarColor(int r, int g, int b) {
		color = new Color(r,g,b);
		repaint();
	}
	
	public int[] getAvatarColor() {
		int[] avatarColor = {color.getRed(), color.getGreen(), color.getBlue()};
		return avatarColor;
	}
}
