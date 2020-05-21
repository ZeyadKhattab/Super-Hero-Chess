package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.EmptyStackException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exceptions.InvalidMovementException;
import exceptions.InvalidPowerUseException;
import exceptions.PowerAlreadyUsedException;
import exceptions.WrongTurnException;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import model.pieces.heroes.ActivatablePowerHero;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKick;

@SuppressWarnings("serial")
public class  Board2 implements ActionListener,KeyListener{
	private Game game;
	private Player pl1,pl2;
	private JPanel fpanel,board,dead1,dead2,payLoad1,payLoad2;
	private JButton[][] buttonsList ;
	private boolean revive,teleport1,teleport2,hackOrRestore,move,power;
	private Piece powerUser,victim;
	private JButton[] deadButton1, deadButton2;
	private int w, h,wBoard,hBoard,wDead,hDead,wPayLoad,hPayLoad;
	private boolean done;
	
	public Board2(String p1Name, String p2Name, int w, int h){
		//initialise players and game
		pl1 = new Player(p1Name);
		pl2 = new Player(p2Name);
		game = new Game(pl1,pl2);
		
		this.w = w;
		this.h = h;
		
		//setTitle("Game");
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setVisible(true);
		
		//for(long i=0;i<999999999;i++){
		//}
		//w = getWidth();
		//h = getHeight();
		
		//Components
		deadButton1 = new JButton[7];
		deadButton2 = new JButton[7];
		
		//Background
		fpanel =new JPanel()
		{  
			@Override
			public void paintComponent(Graphics g) {
				ImageIcon bgImage = new ImageIcon("Board.png");
				Image bg = bgImage.getImage();
			    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
			}
				
		};
		fpanel.setPreferredSize(new Dimension(w, h));
		fpanel.setLayout(null);
		//add(fpanel);
		
		// dead buttons action listener
		DeadButtons db = new DeadButtons();
		
		//Buttons
		wBoard = w*53/120;
		hBoard = h*37/45;
		wDead = w*6/75;
		hDead = h*9/20;
		wPayLoad = w*11/144;
		hPayLoad = h*4/9;
		board = new JPanel();
		dead1 = new JPanel();
		dead2 = new JPanel();
		payLoad1 = new JPanel()
		{  
			@Override
			public void paintComponent(Graphics g) {
				ImageIcon bgImage = new ImageIcon("1.png");
				Image bg = bgImage.getImage();
			    g.drawImage(bg, 0, 0, wPayLoad, hPayLoad, this);
			}
				
		};
		payLoad2 = new JPanel()
		{  
			@Override
			public void paintComponent(Graphics g) {
				ImageIcon bgImage = new ImageIcon("1.png");
				Image bg = bgImage.getImage();
			    g.drawImage(bg, 0, 0, wPayLoad, hPayLoad, this);
			}
				
		};
		
		//Board
		board.setBounds(w/2-wBoard*197/400, h/2-hBoard*177/320, wBoard, hBoard);
		board.setOpaque(false);
		board.setLayout(new GridLayout(game.getBoardHeight(),game.getBoardWidth()));
		buttonsList = new JButton[game.getBoardHeight()][game.getBoardWidth()];
		for(int i=0;i<game.getBoardHeight();i++)
			for(int j=0;j<game.getBoardWidth();j++){
				JButton b = new JButton();
				b.addKeyListener(this);
				if(game.getCellAt(i,j).getPiece()!=null){
					String s = game.getCellAt(i,j).getPiece().toString();
					String s2 = "<html>";
					for(int c=0;c<s.length();c++){
						s2+=s.charAt(c)=='\n'?"<br>":s.charAt(c);
					}
					s2 +="</p></html>";
					b.setToolTipText(s2);
				}
				b.setOpaque(false);
				b.setContentAreaFilled(false);
				b.setBorderPainted(false);
				b.addActionListener(this);
				try{
					setImage(b,i,j);
				}catch(IOException e){
					JOptionPane.showMessageDialog(board, "Could not find image");
				}
				board.add(b);
				buttonsList[i][j]=(b);
			}
		
		//Player 1 Dead Characters
		dead1.setBounds(w/12-wDead*19/40, h*2/3-hDead/2, wDead, hDead);
		dead1.setOpaque(false);
		dead1.setLayout(new GridLayout(7,1));
		String [] heroChars = new String[]  {"super","ranged","medic","armored","speedster","tech","sidekick"};
		for(int i=0;i<7;i++)
		{
			JButton button = new JButton();
			button.setText("\tx0");
			try{
				setImage(button, heroChars[i],1);
			}catch(IOException e){
				JOptionPane.showMessageDialog(board, "Could not find image");
			}
			button.addActionListener(db);
			button.addKeyListener(db);
			dead1.add(button);
			deadButton1[i]=button;
		}
		
		//Player 2 Dead Characters
		dead2.setBounds(w*11/12-wDead*21/40, h*2/3-hDead*21/40, wDead, hDead);
		dead2.setOpaque(false);
		dead2.setLayout(new GridLayout(7,1));
		for(int i=0;i<7;i++)
		{
			JButton button =new JButton();
			button.setText("\tx0");
			try{
				setImage(button, heroChars[i],2);
			}catch(IOException e){
				JOptionPane.showMessageDialog(board, "Could not find image");
			}
			button.addActionListener(db);
			button.addKeyListener(db);
			dead2.add(button);
			deadButton2[i]=button;
		}
		
		//PayLoad Panels
		payLoad1.setBounds(w/5-wPayLoad/2, h*2/3-hPayLoad*7/16, wPayLoad, hPayLoad);
		payLoad2.setBounds(w*4/5-wPayLoad*4/9, h*2/3-hPayLoad*7/16, wPayLoad, hPayLoad);
		
		//Add Components
		fpanel.add(board);
		fpanel.add(dead1);
		fpanel.add(dead2);
		fpanel.add(payLoad1);
		fpanel.add(payLoad2);
	}
	
	public void setImage(JButton button,int i,int j) throws IOException
	{
		Piece p=game.getCellAt(i, j).getPiece();
		String s;
		
		if(p instanceof Armored)
			s="armored";
		else if(p instanceof Super)
			s="super";
		else if(p instanceof Ranged)
			s="ranged";
		else if(p instanceof Speedster)
			s="speedster";
		else if(p instanceof Medic)
			s="medic";
		else if(p instanceof Tech)
			s="tech";
		else if(p instanceof SideKick)
			s="sidekick";
		else{
			button.setIcon(null);
			return;
		}
		/*if(p instanceof ActivatablePowerHero)
		{
			if(((ActivatablePowerHero) p).isPowerUsed())
				s=s+"PUsed";
			else
				s=s+"PNotUsed";
		}
		if(p instanceof Armored)
		{
			if(((Armored) p).isArmorUp())
				s=s+"Up";
			else
				s=s+"Down";
		}*/
		if(p.getOwner()==game.getPlayer1())
			s=s+"p1";
		else
			s=s+"p2";
		
		//Image image=ImageIO.read(getClass().getResource("/images/"+s+".gif"));
		Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s+".gif"));
		image = image.getScaledInstance(-1, h/10, java.awt.Image. SCALE_DEFAULT);
		button.setIcon(new ImageIcon(image));
	}
	
	public void setImage(JButton button, String s,int player) throws IOException
	{
			switch(s){
			case "armored":break;
			case "super":;
			case "ranged":;
			case "medic":;
			case "tech":break;
			case "speedster":;
			case "sidekick":;break;
			default:button.setIcon(null);return;
			}
		s=s+"p"+player;
		Image image=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s+".gif"));
		image = image.getScaledInstance(-1, h/20, java.awt.Image. SCALE_DEFAULT);
		button.setIcon(new ImageIcon(image));
	}
	
	public  Direction stringToDirection(String s){
		if(s==null)
			return null;
		s=s.toUpperCase();
		
		if(s.equals("UP"))
			return Direction.UP;
		else if(s.equals("DOWN"))
			return(Direction.DOWN);
		if(s.equals("RIGHT"))
			return(Direction.RIGHT);
		if(s.equals("LEFT"))
			return(Direction.LEFT);
		if(s.equals("UPLEFT"))
			return(Direction.UPLEFT);
		if(s.equals("UPRIGHT"))
			return(Direction.UPRIGHT);
		if(s.equals("DOWNLEFT"))
			return(Direction.DOWNLEFT);
		if(s.equals("DOWNRIGHT"))
			return(Direction.DOWNRIGHT);
		return null;
		
		
	}
	
	public String pieceToString(Piece p)
	{
		
		if(p instanceof Armored)
			return "armored";
		if(p instanceof Super)
			return "super";
		if(p instanceof Speedster)
			return "speedster";
		if(p instanceof Ranged)
			return "ranged";
		
		if(p instanceof Medic)
			return "medic";
		if(p instanceof Tech)
			return "tech";
		if(p instanceof SideKick)
			return "sidekick";
		return "";
	}
	
	public void update(){
		for(int i=0;i<game.getBoardHeight();i++)
			for(int j=0;j<game.getBoardWidth();j++){
				JButton b = buttonsList[i][j];
				b.setIcon(null);
				try{
					setImage(b,i,j);
				}catch(IOException e){
					JOptionPane.showMessageDialog(board, "Could not find image");
				}
				if(game.getCellAt(i,j).getPiece()!=null){
					String s = game.getCellAt(i,j).getPiece().toString();
					String s2 = "<html>";
					for(int c=0;c<s.length();c++){
						s2+=s.charAt(c)=='\n'?"<br>":s.charAt(c);
					}
					s2 +="</p></html>";
					b.setToolTipText(s2);
				}
				else
					b.setToolTipText(null);
			}
		int x1=game.getPlayer1().getPayloadPos()+1;
		int x2=game.getPlayer2().getPayloadPos()+1;
		fpanel.remove(payLoad1);
		payLoad1 = new JPanel()
		{  
			@Override
			public void paintComponent(Graphics g) {
				ImageIcon bgImage = new ImageIcon(x1+".png");
				Image bg = bgImage.getImage();
			    g.drawImage(bg, 0, 0, wPayLoad, hPayLoad, this);
			}
				
		};
		payLoad1.setBounds(w/5-wPayLoad/2, h*2/3-hPayLoad*7/16, wPayLoad, hPayLoad);
		fpanel.add(payLoad1);
		
		fpanel.remove(payLoad2);
		payLoad2 = new JPanel()
		{  
			@Override
			public void paintComponent(Graphics g) {
				ImageIcon bgImage = new ImageIcon(x2+".png");
				Image bg = bgImage.getImage();
			    g.drawImage(bg, 0, 0, wPayLoad, hPayLoad, this);
			}
				
		};
		payLoad2.setBounds(w*4/5-wPayLoad*4/9, h*2/3-hPayLoad*7/16, wPayLoad, hPayLoad);
		fpanel.add(payLoad2);
		String [] heroChars = new String[]  {"super","ranged","medic","armored","speedster","tech","sidekick"};
		for(int i=0;i<7;i++)
		{
			deadButton1[i].setText(null);
			deadButton1[i].setText("\tx"+game.getPlayer1().getDeadCharacters2()[i].size());
			deadButton1[i].setIcon(null);
			try{
				setImage(deadButton1[i], heroChars[i],1);
			}catch(IOException e){
				JOptionPane.showMessageDialog(board, "Could not find image");
			}
		}
		for(int i=0;i<7;i++)
		{
			deadButton2[i].setText(null);
			deadButton2[i].setText("\tx"+game.getPlayer2().getDeadCharacters2()[i].size());
			deadButton2[i].setIcon(null);
			try{
				setImage(deadButton2[i], heroChars[i],2);
			}catch(IOException e){
				JOptionPane.showMessageDialog(board, "Could not find image");
			}
		}
		
		fpanel.revalidate();
		fpanel.repaint();
		if(game.getWinner()!=null){
			done = true;
			Player winner=game.getWinner();
			int x=winner==game.getPlayer1()?1:2;
			for(int i=0;i<game.getBoardHeight();i++)
				for(int j=0;j<game.getBoardWidth();j++)
					if(game.getCellAt(i, j).getPiece()!=null && game.getCellAt(i, j).getPiece().getOwner()==winner)
					{
						String s=pieceToString(game.getCellAt(i, j).getPiece())+"Winp"+x;
						if(game.getCellAt(i,j).getPiece() instanceof SideKick)
							continue;
						Image image=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s+".gif"));
						image = image.getScaledInstance(-1, h/10, java.awt.Image. SCALE_DEFAULT);
						buttonsList[i][j].setIcon(new ImageIcon(image));
					}
			/*setVisible(false);
			dispose();*/
		}
	}
	
	public int getI(Piece p)
	{
		for(int i=0;i<game.getBoardHeight();i++)
			for(int j=0;j<game.getBoardWidth();j++)
				if(game.getCellAt(i, j).getPiece()==p)
					return i;
		return -1;
		
	}
	public int getJ(Piece p)
	{
		for(int i=0;i<game.getBoardHeight();i++)
			for(int j=0;j<game.getBoardWidth();j++)
				if(game.getCellAt(i, j).getPiece()==p)
					return j;
		return -1;
		
	}
	
	public Piece getPieceAt(JButton b){
		for(int i=0;i<game.getBoardHeight();i++)
			for(int j=0;j<game.getBoardWidth();j++)
				if(buttonsList[i][j]==b)
					return game.getCellAt(i, j).getPiece();
		return null;
	}
	
	public Game getGame(){
		return game;
	}
	
	public boolean isDone(){
		return done;
	}

	public JPanel getPanel(){
		return fpanel;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(game.getWinner()==null && e!=null){
			JButton b = (JButton) e.getSource();
			// Trying to revive a piece on the board
			if(revive) {
				// do nothing, it is handled in keyListener
			}
			
			//Choosing the piece to teleport
			else if(teleport1){
				victim = getPieceAt(b);
				if(victim==null)
					return;
				teleport1 = false;
				teleport2 = true;
			}
			
			//Choosing the destination of the teleportation
			else if(teleport2){
				Point point = null;
				for(int i=0;i<game.getBoardHeight();i++)
					for(int j=0;j<game.getBoardWidth();j++)
						if(buttonsList[i][j]==b)
							point = new Point(i,j);
				try {
					
					
					
					((Tech)powerUser).usePower(null,victim,point);
					String s="techPowerp";
					if(powerUser.getOwner()==game.getPlayer1())
						s=s+1;
					else
						s=s+2;
					Image image=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s+".gif"));
					
					new Animation(image,w,h);
					update();
				}catch (PowerAlreadyUsedException e1){
					JOptionPane.showMessageDialog(board, "Power already used");
				}
				catch(InvalidPowerUseException e2){
					JOptionPane.showMessageDialog(board, e2.getMessage());
					((ActivatablePowerHero)powerUser).setPowerUsed(false);
				}
				catch(WrongTurnException e3){
					JOptionPane.showMessageDialog(board, "This is "+game.getCurrentPlayer().getName()+"'s turn");
					((ActivatablePowerHero)powerUser).setPowerUsed(false);
				}
				
				teleport2 = false;
			}
			
			//Hacking or Restoring
			else if(hackOrRestore){
				victim = getPieceAt(b);
				if(victim==null)
					return;
				
				try {
					
					
					
					String s="techPowerp";
					if(powerUser.getOwner()==game.getPlayer1())
						s=s+1;
					else
						s=s+2;
					Image image=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s+".gif"));
					
					((Tech)powerUser).usePower(null,victim,null);
					
					new Animation(image,w,h);
					update();
				}catch (PowerAlreadyUsedException e1){
					JOptionPane.showMessageDialog(board, "Power already used");
				}
				catch(InvalidPowerUseException e2){
					JOptionPane.showMessageDialog(board, e2.getMessage());
					((ActivatablePowerHero)powerUser).setPowerUsed(false);
				}
				catch(WrongTurnException e3){
					JOptionPane.showMessageDialog(board, "This is "+game.getCurrentPlayer().getName()+"'s turn");
					((ActivatablePowerHero)powerUser).setPowerUsed(false);
				}
				//update();
				hackOrRestore = false;
			}
			
			// Otherwise
			/*else
			{
				Piece p=getPieceAt(b);
				powerUser = p;
				String moveOrPower,tHR;
				if(p!=null)
				{
					if(!(p instanceof ActivatablePowerHero))
						moveOrPower = "1";
					else{
						do 
						{
							moveOrPower=JOptionPane.showInputDialog(this, "Enter 1 for move and 2 for power");
							if(moveOrPower==null)
								return;
						}while(!moveOrPower.equals("1") && ! moveOrPower.equals("2"));
					}
					
					if(moveOrPower.equals("1")){
						String directionString;
						Direction direction;
						do{	
							directionString=JOptionPane.showInputDialog("Enter Direction");
							if(directionString==null)
								return;
							direction = stringToDirection(directionString);
						}while(direction==null);
						while(true){
							try{
								game.setAttacker(null);
								game.setDefender(null);	
								p.move(direction);
								Piece attacker=game.getAttacker();
								Piece defender =game.getDefender();
								if(attacker!=null && !(attacker instanceof SideKick)  && !(defender instanceof SideKick))
								{
									String s1=pieceToString(attacker)+"Attackp";
									String s2=pieceToString(defender)+"Attackedp";
									if(attacker.getOwner()==game.getPlayer1())
									{
										s1=s1+1;
										s2=s2+2;
									}
									else
									{
										s1=s1+2;
										s2=s2+1;
									}
									
									Image image1=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s1+".gif"));
									Image image2=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s2+".gif"));
									Animation animation=new Animation(image1,image2);
									
								}
								
								
								update();
								break;
							}catch(WrongTurnException e1){
								JOptionPane.showMessageDialog(board, "This is "+game.getCurrentPlayer().getName()+"'s turn");
								return;
							}catch(InvalidMovementException e2){
								do
								{
									directionString=JOptionPane.showInputDialog("Enter a valid direction");
									if(directionString==null)
										return;
									direction = stringToDirection(directionString);
								}
								while(direction==null);
							}
						}
						
					}
					else if(moveOrPower.equals("2")){
						
						if(p instanceof Super || p instanceof Ranged){
							String directionString;
							Direction direction;
							do{	
								directionString=JOptionPane.showInputDialog("enter Direction");
								if(directionString==null)
									return;
								direction = stringToDirection(directionString.toUpperCase());
							}
							while(direction==null);
							ActivatablePowerHero h = (ActivatablePowerHero)p;
							try{
								int iUser=getI(h),jUser=getJ(h);
								h.usePower(direction, null,null);
								String s;
								if(p instanceof Super)
									s="super";
								else
									s="ranged";
								s=s+"Powerp";
								if(p.getOwner()==game.getPlayer1())
									s=s+1;
								else
									s=s+2;
								Image image=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s+".gif"));
								Animation animation=new Animation(image);
								
								
							}catch (PowerAlreadyUsedException e1){
								JOptionPane.showMessageDialog(board, "Power already used");
							}
							catch(InvalidPowerUseException e2){
								JOptionPane.showMessageDialog(board, e2.getMessage());
							}
							catch(WrongTurnException e3){
								JOptionPane.showMessageDialog(board, "This is "+game.getCurrentPlayer().getName()+"'s turn");
							}
						}
						else if(p instanceof Medic)
							revive = true;
						else if(p instanceof Tech){
							do 
							{
								tHR=JOptionPane.showInputDialog(this, "enter 1 for teleport and 2 for hack/restore");
								
							}while(!tHR.equals("1")&&!tHR.equals("2"));
							switch(tHR){
							case "1": teleport1 = true;break;
							case "2": hackOrRestore = true;
							}
						}
					}
						
					update();
				}
				
				
			}*/
		
		
		}
	}

private class DeadButtons implements ActionListener,KeyListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			char c=e.getKeyChar();
			if(c=='.')
				terminate();
			JButton b=(JButton) e.getSource();	
			Direction direction=getDirection(c);
			if(direction==null)
				return;
			if(game.getWinner()==null && e!=null){
				if(revive) 
			{
					
					
					Piece target = null;
					boolean pl1 = true;
					int type = 0;
					
					revive=false;
					for(int i=0;i<game.getBoardHeight();i++) {
							if(deadButton1[i]==b)
							{
								try{
									target = game.getPlayer1().getDeadCharacters2()[i].pop();
									type = i;
								} catch(EmptyStackException e1){
									JOptionPane.showMessageDialog(board, "No pieces of this type have been killed");
									
									return;
								}
							}
							if(deadButton2[i]==b)
							{
								try{
									target = game.getPlayer2().getDeadCharacters2()[i].pop();
									type = i;
									pl1 = false;
								} catch(EmptyStackException e1){
									JOptionPane.showMessageDialog(board, "No pieces of this type have been killed");
									
									return;
								}
							}
					}
						
					try {
						
						
						((Medic)powerUser).usePower(direction,target,null);
						String s="medicPowerp";
						if(powerUser.getOwner()==game.getPlayer1())
							s=s+"1";
						else
							s=s+2;
						Image image=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s+".gif"));
						new Animation(image,w,h);
						update();
					}catch (PowerAlreadyUsedException e1){
						JOptionPane.showMessageDialog(board, "Power already used");
						
						if(pl1)
							game.getPlayer1().getDeadCharacters2()[type].push(target);
						else
							game.getPlayer2().getDeadCharacters2()[6-type].push(target);
					}
					catch(InvalidPowerUseException e2){
						JOptionPane.showMessageDialog(board, e2.getMessage());
						
						if(pl1)
							game.getPlayer1().getDeadCharacters2()[type].push(target);
						else
							game.getPlayer2().getDeadCharacters2()[6-type].push(target);
					}
					catch(WrongTurnException e3){
						JOptionPane.showMessageDialog(board, "This is "+game.getCurrentPlayer().getName()+"'s turn");
						if(pl1)
							game.getPlayer1().getDeadCharacters2()[type].push(target);
						else
							game.getPlayer2().getDeadCharacters2()[6-type].push(target);
					} 
					
				}
				
				
			}

			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

@Override
public void keyPressed(KeyEvent e) {
	
	JButton b=(JButton) e.getSource();
	
	Piece target=getPieceAt(b);
	
	char c=e.getKeyChar();
	if(c=='.') //terminate all initiated actions
	
		terminate();
	
		
	
	if(revive)
	{
	
		Direction direction=getDirection(c);
		
		if(direction!=null)
		{	
		
			try {
				((Medic)powerUser).usePower(direction,target,null);
				update();
			}catch (PowerAlreadyUsedException e1){
				JOptionPane.showMessageDialog(board, "Power already used");
			}
			catch(InvalidPowerUseException e2){
				JOptionPane.showMessageDialog(board, e2.getMessage());
			}
			catch(WrongTurnException e3){
				JOptionPane.showMessageDialog(board, "This is "+game.getCurrentPlayer().getName()+"'s turn");
			}
		}
		revive=false;
		
		
	}
	 if(move && getPieceAt(b)==powerUser)
	{
		
		Direction direction=getDirection(c);
		if(direction==null)
			return;
		move=false;
		Piece p=getPieceAt(b);
		/*while(true)*/
		{
			try
			{
				game.setAttacker(null);
				game.setDefender(null);	
				p.move(direction);
				Piece attacker=game.getAttacker();
				Piece defender =game.getDefender();
				if(attacker!=null && !(attacker instanceof SideKick)  && !(defender instanceof SideKick))
				{
					String s1=pieceToString(attacker)+"Attackp";
					String s2=pieceToString(defender)+"Attackedp";
					if(attacker.getOwner()==game.getPlayer1())
					{
						s1=s1+1;
						s2=s2+2;
					}
					else
					{
						s1=s1+2;
						s2=s2+1;
					}
					
					Image image1=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s1+".gif"));
					Image image2=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s2+".gif"));
					new Animation(image1,image2,w,h);
					
				}
				update();
				/*break;*/
			}catch(WrongTurnException e1){
				JOptionPane.showMessageDialog(board, "This is "+game.getCurrentPlayer().getName()+"'s turn");
				//return;
			}catch(InvalidMovementException e2){
				JOptionPane.showMessageDialog(board, "This is not a valid direction");
			}
		}
		
	}
	 if (power)
	{
	
		Piece p=getPieceAt(b);
		if(p instanceof Super || p instanceof Ranged){
			Direction direction=getDirection(c);
			if(direction==null)
				return;
			power=false;
			ActivatablePowerHero hero = (ActivatablePowerHero)p;
			try{
				
				hero.usePower(direction, null,null);
				String s;
				if(p instanceof Super)
					s="super";
				else
					s="ranged";
				s=s+"Powerp";
				if(p.getOwner()==game.getPlayer1())
					s=s+1;
				else
					s=s+2;
				Image image=Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/"+s+".gif"));
				new Animation(image,w,h);
				update();
				
			}catch (PowerAlreadyUsedException e1){
				JOptionPane.showMessageDialog(board, "Power already used");
			}
			catch(InvalidPowerUseException e2){
				JOptionPane.showMessageDialog(board, e2.getMessage());
			}
			catch(WrongTurnException e3){
				JOptionPane.showMessageDialog(board, "This is "+game.getCurrentPlayer().getName()+"'s turn");
			}
		}
		/*else if(p instanceof Medic)
			revive = true;*/
		/*else if(p instanceof Tech){
			do 
			{
				tHR=JOptionPane.showInputDialog(this, "enter 1 for teleport and 2 for hack/restore");
				
			}while(!tHR.equals("1")&&!tHR.equals("2"));
			switch(tHR){
			case "1": teleport1 = true;break;
			case "2": hackOrRestore = true;
			}
		}*/
		update();
	}
	if(c=='p' || c=='m')
	{
		
		Piece p =getPieceAt(b);
		
		powerUser=p;
		if(c=='m')
		{	
			move=true;
			power=false;
		}	
		if(c=='p' && p instanceof ActivatablePowerHero)
		{	
			move=false;
			
			if(p instanceof Medic)
			{
				revive=true;
			}
			else if(p instanceof Tech)
			{
				
				String tHR;
				do 
				{
					tHR=JOptionPane.showInputDialog("Enter 1 for teleport and 2 for hack/restore");
					if(tHR==null)
						break;
					
				}while(!tHR.equals("1")&&!tHR.equals("2"));
				switch(tHR){
				case "1": teleport1 = true;break;
				case "2": hackOrRestore = true;
				}
			}
			else
				power=true;
		}	
		
		
	}

	
	
	
	
}

@Override
public void keyReleased(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}	
public Direction getDirection (char c)
{
	Direction direction=null;
	switch (c)
	{
		case 'w':direction=Direction.UP;break;
		case 's':direction=Direction.DOWN;break;
		case 'a':direction=Direction.LEFT;break;
		case 'd':direction=Direction.RIGHT;break;
		case 'z':direction=Direction.DOWNLEFT;break;
		case 'c':direction=Direction.DOWNRIGHT;break;
		case 'q':direction=Direction.UPLEFT;break;
		case 'e':direction=Direction.UPRIGHT;break;
	}
	return direction;
}
public void terminate()
{
	powerUser=victim=null;
	revive=teleport1=teleport2=hackOrRestore=move=power=false;

}
}
