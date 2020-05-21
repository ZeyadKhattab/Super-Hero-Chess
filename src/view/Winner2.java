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
public class Winner2 implements ActionListener {
	private JPanel fpanel;
	private JButton playAgain;
	private boolean done;
	
	public Winner2(int w, int h){
		
		/*setTitle("Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		
		for(long i=0;i<999999999;i++){
		}
		int w = getWidth();
		int h = getHeight();
		*/
		
		//Background
		fpanel =new JPanel()
		{  
			@Override
			public void paintComponent(Graphics g) {
				ImageIcon bgImage = new ImageIcon("Winner2.png");
				Image bg = bgImage.getImage();
			    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
			}
				
		};
		fpanel.setPreferredSize(new Dimension(w, h));
		fpanel.setLayout(null);
		//add(fpanel);
		
		//Buttons
		playAgain = new JButton();
		int wPlayAgain = w*2/7;
		int hPlayAgain = h/18;
		
		playAgain.setBounds(w/2-wPlayAgain*21/44, h*5/6-hPlayAgain, wPlayAgain, hPlayAgain);
		playAgain.setOpaque(false);
		playAgain.setContentAreaFilled(false);
		//playAgain.setBorderPainted(false);
		playAgain.addActionListener(this);
		
		
		fpanel.add(playAgain);
	}
	
	public boolean isDone(){
		return done;
	}
	
	public JPanel getPanel(){
		return fpanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		done = true;
	}

}
