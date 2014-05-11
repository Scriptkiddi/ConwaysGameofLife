import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class LevelSelection extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8817396432100345447L;
	private JPanel contentPane;
	private GuiHandler guiHandler;
	private ArrayList<JPanel> panels = new ArrayList<JPanel>();

	/**
	 * Create the frame.
	 */
	public LevelSelection(GuiHandler gui) {
		this.guiHandler = gui;
		int levelcount = gui.getFuctionHandler().getLevelAnzahl();
		int anzahlAnPanels =0;
		int aktuellesLevel =1;
		for(int i=levelcount; i>0;i-=25){
			anzahlAnPanels++;
		}
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 526, 300);
		for(int l=0; l<anzahlAnPanels;l++){
			contentPane = new JPanel();
			panels.add(contentPane);
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			contentPane.setLayout(null);

			JLabel lblLevelAuswahl = new JLabel("Level Auswahl");
			lblLevelAuswahl.setFont(new Font("Agency FB", Font.PLAIN, 30));
			lblLevelAuswahl.setBounds(189, 11, 132, 41);
			contentPane.add(lblLevelAuswahl);
			if(l<anzahlAnPanels-1){ //Damit das letzte Panel keinen weiter knopf bekommt
				final int i =l;
				JButton btnForward = new JButton("");
				btnForward.setIcon(new ImageIcon(LevelSelection.class.getResource("/resources/images/Alarm-Forward-icon.png")));
				btnForward.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setContentPane(i+1);
					}
				});
				btnForward.setBounds(451, 219, 38, 32);
				contentPane.add(btnForward);
			}
			if(l!=0){//damit das erste panel keinen zurück knopf bekommt
				final int i =l;
				JButton btnBack = new JButton("");
				btnBack.setIcon(new ImageIcon(LevelSelection.class.getResource("/resources/images/Alarm-Backward-icon.png")));
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setContentPane(i-1);
					}
				});
				btnBack.setBounds(10, 219, 38, 32);
				contentPane.add(btnBack);
			}
	
			
	
			//Definitionen für das Layout der Buttons
			int buttonwidth = 89;
			int buttonheight = 23;
			int marginleft = 10;
			int margintop = 51;
			int buttonspaceheight = 11;
			int buttonspacewidth =10;
			int buttonrow= 0;
			int buttoncoloumn =0;
			
			for(int i=1; i<=25; i++){
				if(aktuellesLevel<=levelcount){
					JButton jtemp = new JButton(Integer.toString(aktuellesLevel));
					final int k = aktuellesLevel;
					jtemp.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							guiHandler.getFuctionHandler().loadLevelGui(k, guiHandler);
						}
					});
					jtemp.setFont(new Font("Agency FB", Font.PLAIN, 15));
					jtemp.setBounds(marginleft+buttoncoloumn*(buttonspacewidth+buttonwidth), margintop+buttonrow*(buttonspaceheight+buttonheight), buttonwidth, buttonheight);
					contentPane.add(jtemp);
					if(buttoncoloumn<4){ //Fünf in eine Reihe
						buttoncoloumn++;
					}else{
						buttonrow++; //Wenn Reihe voll dann wird der Spaltenzähler zurück gesetzt und der Reihen zähler um eins erhöht
						buttoncoloumn=0;
					}
					aktuellesLevel++;
				}else{
					break;
				}
			}
		}
		setContentPane(panels.get(0));
	}
	
	private void setContentPane(int i){
		this.setContentPane(panels.get(i)); //setze das neue panel
		this.validate(); //zum neu zeichen des frames
	}
}
