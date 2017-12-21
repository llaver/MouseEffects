package com.mouseeffects.effects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import java.util.LinkedList;

public class trail extends JComponent implements ActionListener {
	
	private Timer trailTimer = new Timer(1, this);
	private Timer paintTimer = new Timer(25, this);
	private Point p = MouseInfo.getPointerInfo().getLocation();
	private Point current;
	private Point previous;
	private Color c;
	
	
	public trail() {
		trailTimer.start();
		paintTimer.start();
	}

    private final Deque<Point> mouseTrail = new LinkedList<>();
	
	private Color getColor(int size, int index) {
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
		g.setColor(Color.BLUE);
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

		g2.setColor(Color.RED);
        g2.drawLine(p.x - 15, p.y, p.x + 15, p.y);
        g2.drawLine(p.x, p.y - 15, p.x, p.y + 15);

        g2.setColor(Color.YELLOW);
        g2.drawLine(p.x - 7, p.y - 7, p.x + 7, p.y + 7);
        g2.drawLine(p.x - 7, p.y + 7, p.x + 7, p.y - 7);

	}
	
	public Dimension getPreferredSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == paintTimer) {
			repaint();
			System.out.println("paintTimer");
		}
		if(e.getSource() == trailTimer) {
			p = MouseInfo.getPointerInfo().getLocation();
            int size = 300;
            while(mouseTrail.size() >= size) {
				mouseTrail.pop();
			}
			mouseTrail.offer(p);
            System.out.println("trailTimer");
		}
	}
}
