import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Spiel extends UnicastRemoteObject implements Serverinterface {
	
	private Fensterpanel panel1, panel2;	//Fenster
	private int sp;
	private ClientInterface spieler1, spieler2, currentPlayer;	// Clients	(crrentPlayer = aktiver Spieler)

	public Spiel() throws RemoteException {		//starten
		panel1 = new Fensterpanel();
		panel2 = new Fensterpanel();
		sp = -1;
	}
	
	public Fensterpanel gegner(ClientInterface s) {
		if (s.equals(spieler1))
			return panel2;
		return panel1;
	}
	private Fensterpanel getPanel(ClientInterface spieler) {
		if(spieler.equals(spieler1))
				return panel1;
		return panel2;
	}
	
	@Override
	public void schuss(int zeile,int spalte, ClientInterface sender) throws RemoteException {
		if (sender.equals(currentPlayer))	//nur aktiver Spieler darf schiessen
			schusss(zeile,spalte, sender);
	}
	private void schusss(int zeile,int spalte, ClientInterface sender) throws RemoteException{
		if (getPanel(sender).getgS()[zeile][spalte].getBackground() == Color.BLUE) {
			if (gegner(sender).getmS()[zeile][spalte].getBackground() == Color.BLACK) {	//falls trffer
				
				getPanel(sender).getgS()[zeile][spalte].setBackground(Color.RED);
				gegner(sender).getmS()[zeile][spalte].setBackground(Color.RED);
				if(gegner(sender).schiffVersenkt(gegner(sender).getmS()[zeile][spalte])) {	//falls ein Schiff versenkt wurde
					if(sender.equals(spieler1)) {
						spieler1.update(panel1, "Treffer, versenkt");
						spieler2.update(panel2, "Dein Schiff wurde versenkt");	}
					else {
						spieler1.update(panel1, "Dein Schiff wurde versenkt");
						spieler2.update(panel2, "Treffer, versenkt");	}
					if(gegner(sender).verloren()) {									// falls alle Schiffe versenkt wurden
						GameOver(sender);
						if(sender.equals(spieler1)) {
							spieler1.update(panel1, "Du hast gewonnen");	//Gegner versenkt
							spieler2.update(panel2, "Du wurdest versenkt");	}
						else {
							spieler1.update(panel1, "Du wurdest versenkt");
							spieler2.update(panel2, "Du hast gewonnen");	}
					}
				}
				else {					// bei "nur" Treffer
					if(sender.equals(spieler1)) {
						spieler1.update(panel1, "Treffer");
						spieler2.update(panel2, "Dein Schiff wurde getroffen");	}
					else {
						spieler1.update(panel1, "Dein Schiff wurde getroffen");
						spieler2.update(panel2, "Treffer");	}
				}
			}
			else if (gegner(sender).getmS()[zeile][spalte].getBackground() == Color.WHITE) {	//falls Wasser (kein treffer)
				
				getPanel(sender).getgS()[zeile][spalte].setBackground(Color.WHITE);
				gegner(sender).getmS()[zeile][spalte].setBackground(Color.CYAN);
				
				if(sender.equals(spieler1)) {
					currentPlayer = spieler2;
					spieler1.update(panel1, "Dein Gegner ist am Zug");
					spieler2.update(panel2, "Du bist am Zug");	
				}
				else {
					currentPlayer = spieler1;
					spieler1.update(panel1, "Du bist am Zug");
					spieler2.update(panel2, "Dein Gegner ist am Zug");	
				}
			}
		}
	}
	
	public void setzen(int zeile,int spalte, ClientInterface sender) throws RemoteException {
		setzen(getPanel(sender),zeile,spalte);
		if (sender.equals(spieler1)){
			spieler1.update(panel1, " setze deine Schiffe (4x2, 3x3, 2x4, 1x5)");
			if ( getPanel(sender).getSchiffe() == 10) {				// wenn alle Schiffe gesetzt wurden
				for (int i = 1; i < panel1.getmS().length; i++) {				//deaktiviere Buttons vom Feld mS
					for (int j = 1; j < panel1.getmS()[i].length; j++) {
						panel1.getmS()[i][j].setEnabled(false);
					}
				}
				spieler1.update(panel1, "Du hast deine Schiffe positioniert");
				spieler2.update(panel2,	"Dein Gegner ist bereit fürs Gefecht");	
				sp++;	
			} 
		}
		else {
			spieler2.update(panel2, "		setze deine Schiffe (4x2, 3x3, 2x4, 1x5)");
			if( getPanel(sender).getSchiffe() == 10 ) {				// wenn alle Schiffe gesetzt wurden
				for (int i = 1; i < panel2.getmS().length; i++) {		//deaktiviere Buttons vom Feld mS
					for (int j = 1; j < panel2.getmS()[i].length; j++) {
						panel2.getmS()[i][j].setEnabled(false);
					}
				}
				spieler1.update(panel1, "Dein Gegner ist bereit fürs Gefecht");
				spieler2.update(panel2, "Du hast deine Schiffe positioniert");	
				sp++;
			}
		}
		if(sp==1) {				//wenn beide Spieler fertig gesetzt haben
			for (int i = 0; i < panel1.getgS().length; i++) {
				for (int j = 0; j < panel1.getgS()[i].length; j++) {	//aktivieren der Buttens vom Feld gS bei beiden Spielern
					panel1.getgS()[i][j].setEnabled(true);					// es kann geschossen werden
					panel2.getgS()[i][j].setEnabled(true);
				}
			}
			spieler1.update(panel1, "Du beginnst die Schlacht");
			spieler2.update(panel2, "Dein Gegner beginnt die Schlacht");
		}
	}

	private void setzen(Fensterpanel panel, int zeile, int spalte) throws RemoteException {
		if(panel.setzbar(zeile, spalte)) {			//falls das Schiff an diese Stelle gesetzt werden kann
			panel.schiff(zeile,spalte);			//setze Schiff
		}
	}
	
	public void registPlayer(ClientInterface spieler) throws RemoteException {
		// bekommt spieler übergeben und registriert diese
		if (spieler1 == null) {
			spieler1 = spieler;
			spieler1.update(panel1, "		setze deine Schiffe (4x2, 3x3, 2x4, 1x5)");
		} else if (spieler2 == null) {
			spieler2 = spieler ;
			currentPlayer = spieler1;
			spieler2.update(panel2, "		setze deine Schiffe (4x2, 3x3, 2x4, 1x5)");
		}
	}
	private void GameOver(ClientInterface c) {
		if(c.equals(spieler1)) {
			panel1.gameOver(true);
			panel2.gameOver(false);
		}
		else {
			panel1.gameOver(false);
			panel2.gameOver(true);
		}
	}

}
