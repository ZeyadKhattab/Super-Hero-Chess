package view;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Animation  extends JFrame{

	public Animation(Image image, int w, int h)
	{
		
		setTitle("Animation");
		
		JLabel label=new JLabel(new ImageIcon(image));
		this.add(label);
		int width = getContentPane().getPreferredSize().width+50;
		int height = getContentPane().getPreferredSize().height+50;
		setBounds(w/2-width/2,h/2-height/2,width<w?width:w,height<h?height:h);
		setVisible(true);
		/*try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispose();*/
		
	}
	public Animation(Image image1,Image image2, int w,int h)
	{
		
		setTitle("Animation");
		this.setLayout(new BorderLayout());
		
		JLabel label1=new JLabel(new ImageIcon(image2));
		this.add(BorderLayout.EAST,label1);
		JLabel label2=new JLabel(new ImageIcon(image1));
		this.add(BorderLayout.CENTER,label2);
		
		int width = getContentPane().getPreferredSize().width+50;
		int height = getContentPane().getPreferredSize().height+50;
		setBounds(w/2-width/2,h/2-height/2,width<w?width:w,height<h?height:h);
		setVisible(true);
		/*try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispose();*/
	}

}
