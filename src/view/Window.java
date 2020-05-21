package view;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame{
	private double w,h;
	
	public Window(){
		setTitle("Marvel Chess");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		pack();
		Rectangle size = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		w = size.getWidth();
		h = size.getHeight() - getInsets().top - getInsets().bottom;;
	}
	
	public double getW(){
		return w;
	}
	
	public double getH(){
		return h;
	}
}
