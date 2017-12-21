package com.mouseeffects.effects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import java.util.LinkedList;

public class trail extends JComponent implements ActionListener {
	
	Timer trailTimer = new Timer(1, this);
	Timer paintTimer = new Timer(1, this);
	Point p = MouseInfo.getPointerInfo().getLocation();
	Point current;
	Point previous;
	Color c;
	
	
	public trail() {
		trailTimer.start();
		paintTimer.start();
	}
	
	private int size = 300;
	private final Deque<Point> mouseTrail = new LinkedList<>();
	
	public Color getColor(int size, int index) {
		int divisionSize = 1450 / size;
		int value = divisionSize * index;
		int red = 0;
		int green = 0;
		int blue = 0;
		switch(value / 255) {
			case 0:
				red = 255;
				blue = value;
				break;
			case 1:
				red = 255 - value % 255;
				blue = 255;
				break;
			case 2:
				green = value % 255;
				blue = 255;
				break;
			case 3:
				green = 255;
				blue = 255 - value % 255;
				break;
			case 4:
				red = value % 255;
				green = 255;
				break;
			default:
				red = 255;
				green = 255;
		}
		return new Color(red, green, blue);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillRect(0, getHeight() / 2 - 10, getWidth(), 20);
		g.fillRect(getWidth() / 2 - 10, 0, 20, getHeight());
		g2.drawString("Mouse x: " + p.x, 100, 100);
		g2.drawString("Mouse y: " + p.y, 100, 150);
		
		if(mouseTrail.size() > 2) {
			for(int i = 1; i < mouseTrail.size() - 1; i++) {
				current = ((LinkedList<Point>) mouseTrail).get(i);
				previous = ((LinkedList<Point>) mouseTrail).get(i - 1);
				c = getColor(mouseTrail.size(), i);
				g2.setColor(c);
				g2.drawLine((int) current.getX(), (int) current.getY(), (int) previous.getX(), (int) previous.getY());
			}
		}
		
	}
	
	public Dimension getPreferredSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == paintTimer) {
			repaint();
		}
		if(e.getSource() == trailTimer) {
			p = MouseInfo.getPointerInfo().getLocation();
			if(mouseTrail.size() >= size) {
				mouseTrail.pop();
			}
			mouseTrail.offer(p);
		}
	}
}
