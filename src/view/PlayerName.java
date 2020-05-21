package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PlayerName implements ActionListener {
	private JPanel fpanel;
	private JButton insertP1Name,insertP2Name,go;
	private String p1Name, p2Name;
	private boolean done;
	
	public PlayerName(int w, int h){
		
		//setTitle("Game");
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setVisible(true);
		
		//for(long i=0;i<999999999;i++){
		//}
		//int w = getWidth();
		//int h = getHeight();
		
		p1Name = "";
		p2Name = "";
		
		//Background
		fpanel =new JPanel()
		{  
			@Override
			public void paintComponent(Graphics g) {
				ImageIcon bgImage = new ImageIcon("Insert player.png");
				Image bg = bgImage.getImage();
			    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
			}
				
		};
		fpanel.setPreferredSize(new Dimension(w, h));
		fpanel.setLayout(null);
		//add(fpanel);
		
		//Buttons
		insertP1Name = new JButton();
		insertP2Name = new JButton();
		go = new JButton();
		int wInsert = w*5/18;
		int hInsert = h*5/72;
		int wGo = w/9;
		int hGo = h/18;
		
		insertP1Name.setBounds(w/2-wInsert/9, h/2+hInsert/9, wInsert, hInsert);
		insertP1Name.setOpaque(false);
		insertP1Name.setContentAreaFilled(false);
		insertP1Name.setBorderPainted(false);
		insertP1Name.addActionListener(this);
		
		insertP2Name.setBounds(w/2-wInsert/9, h*2/3+hInsert/9, wInsert, hInsert);
		insertP2Name.setOpaque(false);
		insertP2Name.setContentAreaFilled(false);
		insertP2Name.setBorderPainted(false);
		insertP2Name.addActionListener(this);
		
		go.setBounds(w/2-wGo/2, h*5/6+hGo/4, wGo, hGo);
		go.setOpaque(false);
		go.setContentAreaFilled(false);
		go.setBorderPainted(false);
		go.addActionListener(this);
		
		fpanel.add(insertP1Name);
		fpanel.add(insertP2Name);
		fpanel.add(go);
	}
	
	public String getP1Name(){
		return p1Name;
	}
	
	public String getP2Name(){
		return p2Name;
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
		if(b==insertP1Name){
			p1Name = JOptionPane.showInputDialog("Enter player 1 name");
		}
		else if(b==insertP2Name){
			p2Name = JOptionPane.showInputDialog("Enter player 2 name");
		}
		else{
			done = true;
		}
	}

}
