package bhs.client.main;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

import javax.swing.JPanel;

public class AvatarDisplayPanel extends JPanel{

	BasicStroke stroke = new BasicStroke(2.0f);
	Color color = new Color(255,255,255);
	String username;
	FontMetrics metrics;
	
	public AvatarDisplayPanel(String username) {
		System.out.println("Avatar display panel created");
		this.username = username;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(stroke);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		
		g2.setFont(new Font("SansSerif", Font.PLAIN, 26));
		
		metrics = g2.getFontMetrics(g2.getFont());
		int usernameWidth = metrics.stringWidth(username);
		
		g2.setColor(Color.WHITE);
		g2.drawString(username, panelWidth/2 - usernameWidth/2, panelHeight/2-20);
		
		g2.setColor(color);
		int xOffset = 30;
		int yOffset = 30;
		int xPoints[] = {panelWidth/2 -30 -xOffset, panelWidth/2-xOffset, panelWidth/2-xOffset + 90, panelWidth/2-xOffset};
		int yPoints[] = {panelHeight/2 + yOffset, panelHeight/2-30 + yOffset, panelHeight/2 + yOffset, panelHeight/2+30 + yOffset};

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
