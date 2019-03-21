import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Hauptmenue extends JFrame {

	private JButton btnMultiplayer, btnClose;
	private JLabel lblUeberschrift;
	

	public Hauptmenue() {
		super("Hauptmenü");
		setSize(250, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(3,1));
		//Elemente
		lblUeberschrift = new JLabel("Schiffeversenken	(A.K.)");
		lblUeberschrift.setFont(new Font("Serif", Font.BOLD, 12));
		add(lblUeberschrift);
		ButtonLis b = new ButtonLis();
		btnMultiplayer = new JButton("Multiplayer Spiel");
		btnMultiplayer.addActionListener(b);
		add(btnMultiplayer);
		btnClose = new JButton("Schließen");
		btnClose.addActionListener(b);
		add(btnClose);
		setVisible(true);
	}

	private class ButtonLis implements ActionListener{
		Server s;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if("Multiplayer Spiel".equals(arg0.getActionCommand())){
				//Ist kein Server aktiv wird einer gestartet sonst wird auf dem Servr ein neues Spiel erstellt
				//und gestartet
				try {
					if( s == null){
						s = new Server();
					}else{
						s.newstart();
					}
					 //s = new Server();
				} catch (RemoteException | MalformedURLException | AlreadyBoundException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
				try {
					GUI eins = new GUI("localhost");
				} catch (MalformedURLException | RemoteException | NotBoundException e) {
					e.printStackTrace();
			}
				try {
					GUI zwei = new GUI ("localhost");
				} catch (MalformedURLException | RemoteException | NotBoundException e) {
					e.printStackTrace();
				}
			}
			
			//Schliest das Programm
			if("Schließen".equals(arg0.getActionCommand())){
				System.exit(0);
			}
		}
		
	}
	
	//main Methode
	public static void main(String[] args) {
		Hauptmenue t = new Hauptmenue();
	}
	
	

}

