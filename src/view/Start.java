package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Start implements ActionListener {
	private JPanel fpanel;
	private JButton play,quit;
	private boolean done;
	
	public Start(int w, int h){
		
		//setTitle("Game");
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setVisible(true);
		
		//for(long i=0;i<999999999;i++){
		//}
		
		//Background
		fpanel =new JPanel()
		{  
			@Override
			public void paintComponent(Graphics g) {
				ImageIcon bgImage = new ImageIcon("Start.png");
				Image bg = bgImage.getImage();
			    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
			}
				
		};
		fpanel.setPreferredSize(new Dimension(w, h));
		fpanel.setLayout(null);
		//add(fpanel);
		
		//Buttons
		play = new JButton();
		quit = new JButton();
		int wPlay = w*13/80;
		int hPlay = h/15;
		int wQuit = w*5/48;
		int hQuit = h*11/180;
		
		play.setBounds(w*59/140, h*45/72, wPlay, hPlay);
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		play.addActionListener(this);
		
		quit.setBounds(w*261/576, h*310/360, wQuit, hQuit);
		quit.setOpaque(false);
		quit.setContentAreaFilled(false);
		quit.setBorderPainted(false);
		quit.addActionListener(this);
		
		fpanel.add(play);
		fpanel.add(quit);
		
		fpanel.revalidate();
		fpanel.repaint();
	}
	
	public boolean isDone(){
		return done;
	}
	
	public JPanel getPanel(){
		return fpanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		if(b==play){
			done = true;
			//setVisible(false);
			//dispose();
		}
		else{
			System.exit(0);
		}
	}

}
