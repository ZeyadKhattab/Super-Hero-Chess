package view;

import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		Window win = new Window();
		int w = (int) win.getW();
		int h = (int) win.getH();
		Start st = new Start(w, h);
		win.add(st.getPanel());
		win.revalidate();
		win.repaint();
		win.setVisible(true);
		while (!st.isDone()) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while (true) {
			PlayerName pn = new PlayerName(w, h);
			win.remove(st.getPanel());
			win.add(pn.getPanel());
			win.revalidate();
			win.repaint();
			while (!pn.isDone()) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Board2 b = new Board2(pn.getP1Name(), pn.getP2Name(), w, h);
			win.remove(pn.getPanel());
			win.add(b.getPanel());
			win.revalidate();
			win.repaint();
			while (!b.isDone()) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (b.getGame().getWinner() == b.getGame().getPlayer1()) {
				Winner1 w1 = new Winner1(w, h);
				win.remove(b.getPanel());
				win.add(w1.getPanel());
				win.revalidate();
				win.repaint();
				while (!w1.isDone()) {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				win.remove(w1.getPanel());
			} else {
				Winner2 w2 = new Winner2(w, h);
				win.remove(b.getPanel());
				win.add(w2.getPanel());
				win.revalidate();
				win.repaint();
				while (!w2.isDone()) {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				win.remove(w2.getPanel());
			}
		}
	}
}
