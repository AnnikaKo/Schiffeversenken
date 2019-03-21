
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fensterpanel extends JPanel implements Serializable {

	private JButton[][] mS;
	private JButton[][] gS;
	private JButton[][] schiff;
	private int schiffe = 0;
	private int zweier=0, dreier=0, vierer=0, schifflänge = 5;
	
	private JFrame buttonSchiffe;
	JButton zweierSchiff,dreierSchiff,viererSchiff,fünferSchiff,richtung;
	private boolean vertikal = false;

	public Fensterpanel() {
		setSize(200, 100);
		setLayout(new GridLayout(26, 16));
		this.gS= new JButton[13][16];
		String[] alf= {" ","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
		for (int i = 0; i < gS.length; i++) {
			for (int j = 0; j < gS[i].length; j++) {
				gS[i][j] = new JButton();
				gS[i][j].setName("0" + i + j);
				gS[i][j].setBackground(Color.BLUE);
				gS[i][j].setForeground(Color.WHITE);
				if (i == 0) {
					gS[i][j].setText(alf[j]);
				}
				else if (j == 0) {
					gS[i][j].setText(""+i);
				}
				gS[i][j].setEnabled(false);
				add(gS[i][j]);
			}
		}
		this.mS= new JButton[13][16];
		for (int i = 0; i < mS.length; i++) {
			for (int j = 0; j < mS[i].length; j++) {
				mS[i][j] = new JButton();
				mS[i][j].setBackground(Color.WHITE);
				mS[i][j].setName("1" + i + j);
				mS[i][j].addActionListener(null);
				if (i == 0) {
					mS[i][j].setText(alf[j]);
				}
				else if (j == 0) {
					mS[i][j].setText(""+i);
				}
				add(mS[i][j]);
			}
		}
		buttonsFürSchiffeSetzen();
		setVisible(true);
	}
	
	public void buttonsFürSchiffeSetzen() {
		buttonSchiffe = new JFrame("Schiffe setzten");
		zweierSchiff = new JButton("2-Feld Schiff");
		dreierSchiff = new JButton("3-Feld Schiff");
		viererSchiff = new JButton("4-Feld Schiff");
		fünferSchiff = new JButton("5-Feld Schiff");
		JLabel lRichtung = new JLabel("Richtung");
		richtung = new JButton("---");

		buttonSchiffe.setSize(150, 200);
		buttonSchiffe.setLocation(1090, 10);
		buttonSchiffe.setLayout(new GridLayout(6, 1));
		buttonSchiffe.getContentPane().add(zweierSchiff);
		buttonSchiffe.getContentPane().add(dreierSchiff);
		buttonSchiffe.getContentPane().add(viererSchiff);
		buttonSchiffe.getContentPane().add(fünferSchiff);
		buttonSchiffe.getContentPane().add(lRichtung);
		buttonSchiffe.getContentPane().add(richtung);

		buttonListener();

		buttonSchiffe.setVisible(true);

	}
	
	public void buttonListener() { //Listener buttens von buttonsFürSchiffeSetzen zuweisen
		JButton b= new JButton();
		JButton[][] sschiff= {	{b,b},				//4x2
					{b,b},
					{b,b},
					{b,b},
					{b,b,b},			//3x3
					{b,b,b},
					{b,b,b},
					{b,b,b,b,b},		//2x4
					{b,b,b,b,b},
					{b,b,b,b,b,b}	};	//1x5
		this.schiff=sschiff;
		zweierSchiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schifflänge = 2;
			}
		});
		dreierSchiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schifflänge = 3;
			}
		});
		viererSchiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schifflänge = 4;
			}
		});
		fünferSchiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schifflänge = 5;
			}
		});
		richtung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (vertikal) {
					vertikal= false;
					richtung.setText("---");
				}
				else {
					vertikal= true;
					richtung.setText("|");
				}
				
			}
		});
	}
	
	public int getSchiffe(){	//Anzahl gesetzter Schiffe
		return schiffe;
	}
	
	public boolean schiffVersenkt(JButton b){
		int boot = 11;
		for (int i = 0; i < schiff.length; i++) {
			for (int j = 0; j < schiff[i].length; j++) {
				if(schiff[i][j].equals(b))					//finde Zeile des schiffs (=boot)
					boot = i;
			}
		}
		for (int j = 0; j < schiff[boot].length; j++) {
			if(schiff[boot][j].getBackground()==Color.BLACK)	//falls noch nicht alle rot sind
				return false;
		}
		return true;
	}
	public boolean verloren() {
		for (int i = 0; i < schiff.length; i++) {
			for (int j = 0; j < schiff[i].length; j++) {
				if(schiff[i][j].getBackground()==Color.BLACK)	//falls ein teil einens Schiffes noch nicht rot (getroffen) ist
					return false;
			}
		}
		return true;
	}
	public void gameOver(boolean g) {		//Game over Bild
		for (int i = 0; i < mS.length; i++) {
			for (int j = 0; j < mS[i].length; j++) {
				mS[i][j].setEnabled(false);
				gS[i][j].setEnabled(false);
				if(g) {
					mS[i][j].setBackground(Color.GREEN);
					gS[i][j].setBackground(Color.GREEN);}
				else {
					gS[i][j].setBackground(Color.RED);
					mS[i][j].setBackground(Color.RED);}
			}
		}
		Color c;
		if(g) 
			c= (Color.BLUE);
		else 
			c= (Color.BLACK);
		gS[2][4].setBackground(c);		//	G
		gS[2][3].setBackground(c);
		gS[2][2].setBackground(c);
		gS[3][1].setBackground(c);
		gS[4][1].setBackground(c);
		gS[5][1].setBackground(c);
		gS[6][1].setBackground(c);
		gS[7][2].setBackground(c);
		gS[7][3].setBackground(c);
		gS[7][4].setBackground(c);
		gS[6][4].setBackground(c);
		gS[5][4].setBackground(c);
		gS[5][3].setBackground(c);
		
		gS[6][6].setBackground(c);		//	A
		gS[7][5].setBackground(c);
		gS[8][5].setBackground(c);
		gS[9][5].setBackground(c);
		gS[10][5].setBackground(c);
		gS[11][5].setBackground(c);
		gS[7][7].setBackground(c);
		gS[8][7].setBackground(c);
		gS[9][7].setBackground(c);
		gS[10][7].setBackground(c);
		gS[11][7].setBackground(c);
		gS[9][6].setBackground(c);
		
		gS[2][8].setBackground(c);		//	M
		gS[3][8].setBackground(c);
		gS[4][8].setBackground(c);
		gS[5][8].setBackground(c);
		gS[6][8].setBackground(c);
		gS[1][9].setBackground(c);
		gS[1][11].setBackground(c);
		gS[2][10].setBackground(c);
		gS[3][10].setBackground(c);
		gS[4][10].setBackground(c);
		gS[5][10].setBackground(c);
		gS[6][10].setBackground(c);
		gS[2][12].setBackground(c);
		gS[3][12].setBackground(c);
		gS[4][12].setBackground(c);
		gS[5][12].setBackground(c);
		gS[6][12].setBackground(c);
		
		gS[5][13].setBackground(c);		//	E
		gS[6][13].setBackground(c);
		gS[7][13].setBackground(c);
		gS[8][13].setBackground(c);
		gS[9][13].setBackground(c);
		gS[10][13].setBackground(c);
		gS[11][13].setBackground(c);
		gS[5][14].setBackground(c);
		gS[5][15].setBackground(c);
		gS[8][14].setBackground(c);
		gS[8][15].setBackground(c);
		gS[11][14].setBackground(c);
		gS[11][15].setBackground(c);
		
		
		mS[1][2].setBackground(c);		//	O
		mS[2][1].setBackground(c);
		mS[3][1].setBackground(c);
		mS[4][1].setBackground(c);
		mS[5][1].setBackground(c);
		mS[6][1].setBackground(c);
		mS[2][3].setBackground(c);
		mS[3][3].setBackground(c);
		mS[4][3].setBackground(c);
		mS[5][3].setBackground(c);
		mS[6][3].setBackground(c);
		mS[7][2].setBackground(c);
		
		mS[6][4].setBackground(c);		//V
		mS[7][4].setBackground(c);
		mS[8][4].setBackground(c);
		mS[8][5].setBackground(c);
		mS[9][5].setBackground(c);
		mS[10][5].setBackground(c);
		mS[11][5].setBackground(c);
		mS[11][6].setBackground(c);
		mS[12][6].setBackground(c);
		mS[6][8].setBackground(c);		
		mS[7][8].setBackground(c);
		mS[8][8].setBackground(c);
		mS[8][7].setBackground(c);
		mS[9][7].setBackground(c);
		mS[10][7].setBackground(c);
		mS[11][7].setBackground(c);
		
		mS[1][9].setBackground(c);		//E
		mS[2][9].setBackground(c);
		mS[3][9].setBackground(c);
		mS[4][9].setBackground(c);
		mS[5][9].setBackground(c);
		mS[6][9].setBackground(c);
		mS[7][9].setBackground(c);
		mS[1][10].setBackground(c);
		mS[1][11].setBackground(c);
		mS[4][10].setBackground(c);
		mS[4][11].setBackground(c);
		mS[7][10].setBackground(c);
		mS[7][11].setBackground(c);
		
		mS[5][13].setBackground(c);		//R
		mS[6][13].setBackground(c);
		mS[7][13].setBackground(c);
		mS[8][13].setBackground(c);
		mS[9][13].setBackground(c);
		mS[10][13].setBackground(c);
		mS[11][13].setBackground(c);
		mS[5][14].setBackground(c);
		mS[5][15].setBackground(c);
		mS[6][15].setBackground(c);
		mS[7][15].setBackground(c);
		mS[8][14].setBackground(c);
		mS[9][14].setBackground(c);
		mS[10][15].setBackground(c);
		mS[11][15].setBackground(c);
	}
	
	public JButton[][] getmS() {
		return mS;
	}
	public JButton[][] getgS() {
		return gS;
	}
	
	private void schiff(int zeile, int spalte, int stelle) {
		for(int x=0; x<schifflänge; x++) {					
			mS[zeile][spalte].setBackground(Color.BLACK);
			schiff[stelle][x] = mS[zeile][spalte];
			if (vertikal)
				zeile++;
			else
				spalte++;
		}
	}
	public void schiff(int zeile, int spalte) {
		switch(schifflänge) {
			case 2:
				schiff(zeile, spalte, (0+zweier));
				zweier++;
				if(zweier==4) {
					zweierSchiff.setEnabled(false);
					schifflänge=0;
				}
				schiffe++;
				break;
			case 3:
				schiff(zeile, spalte, (4+dreier));
				dreier++;
				if(dreier==3) {
					dreierSchiff.setEnabled(false);
					schifflänge=0;
				}
				schiffe++;
				break;
			case 4:
				schiff(zeile, spalte, (7+vierer));
				vierer++;
				if(vierer==2) {
					viererSchiff.setEnabled(false);
					schifflänge=0;
				}
				schiffe++;
				break;
			case 5:
				schiff(zeile, spalte, 9);
				fünferSchiff.setEnabled(false);
				schifflänge=0;
				schiffe++;
				break;
		}
		if(schiffe>=10) {
			buttonSchiffe.setVisible(false);
		}
	}
	
	public boolean setzbar(int zeile, int spalte) {
		try {
			if((vertikal && (zeile+schifflänge)<=13)|| ((vertikal==false) && (spalte+schifflänge)<=16)) { //falls das Schiff komplett im Feld liegt
				for (int x=0; x<schifflänge; x++) {
					if(mS[zeile][spalte].getBackground()==Color.BLACK||		//falls das Feld oder eins der Umliegenden teil eines Schiffes ist
							mS[zeile-1][spalte].getBackground()== Color.BLACK || mS[zeile+1][spalte].getBackground()==Color.BLACK||
							mS[zeile][spalte-1].getBackground()== Color.BLACK || mS[zeile][spalte+1].getBackground()==Color.BLACK||
							mS[zeile-1][spalte-1].getBackground()==Color.BLACK|| mS[zeile-1][spalte+1].getBackground()==Color.BLACK||
							mS[zeile+1][spalte+1].getBackground()==Color.BLACK|| mS[zeile+1][spalte-1].getBackground()==Color.BLACK		)
						return false;	//kann das Schiff nicht gesetztz werden
					
					if (vertikal) //gehe vertikal oder horrizontal weiter
						zeile++;
					else
						spalte++;
				}
			}
		}catch(Exception e) {	}
		return true;
	}
	
}