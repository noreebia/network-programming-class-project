package bhs.client.main;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

import javax.swing.JPanel;

public class PanelWithDrawing extends JPanel{

	BasicStroke stroke = new BasicStroke(2.0f);
	Color color = new Color(0,255,255);
	
	public PanelWithDrawing() {
		System.out.println("panel with drawing created");
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("painting...");
		Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//g2.setStroke(stroke);
		//g2.draw(new Ellipse2D.Double(50, 50, 250, 250));
		
		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		
		int smallerValue;
		
		if(panelWidth >= panelHeight) {
			smallerValue = panelHeight;
		}else {
			smallerValue = panelWidth;
		}
		
		g2.setColor(color);
		int percentage = 45;
		int circleRadius = smallerValue * percentage/100;
		
		Shape circle = new Ellipse2D.Double(panelWidth/2 - circleRadius/2, panelHeight/2 - circleRadius/2, circleRadius, circleRadius);
		//g2.fill(circle);
		/*
		g2.fillOval(panelWidth/2 - circleRadius/2, panelHeight/2 - circleRadius/2, circleRadius, circleRadius);
		*/
		int rectWidth = circleRadius;
		int rectHeight = circleRadius / 5;

		//g2.fillRect(panelWidth/2, panelHeight/2 - rectHeight/2, rectWidth, rectHeight);
		System.out.println("panelWidth: " + panelWidth + "panelHeight: " + panelHeight + "rectWidth: " + rectWidth + "rectHeight: " + rectHeight );
		
		
		//int xPoints[] = {panelWidth/2 - 30, panelWidth/2, panelWidth/2+30, panelWidth/2};
		//int yPoints[] = {panelHeight/2, panelHeight/2-30, panelHeight/2, panelHeight/2+70};
		
		int xPoints[] = {panelWidth/2 - 30, panelWidth/2, panelWidth/2 + 90, panelWidth/2};
		int yPoints[] = {panelHeight/2, panelHeight/2-30, panelHeight/2, panelHeight/2+30};

		g2.fillPolygon(xPoints, yPoints, xPoints.length);
		/*
		Shape rect = new Rectangle2D.Double(panelWidth/2 - rectWidth/2, panelHeight/2, rectWidth, rectHeight);
		g2.fill(rect);
		*/
	}
	
	public void setAvatarColor(int r, int g, int b) {
		color = new Color(r,g,b);
		repaint();
	}
}
