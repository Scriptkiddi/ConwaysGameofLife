import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;
import javax.swing.JTextPane;
import java.awt.Font;

public class GameField_Sidebar_Level extends GameField_Sidebar {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2863425198379114120L;
	private JTextPane txtpnZielBeschreibung;
	private JLabel lblZuplazierendeZellen;
	private JLabel lblLevel;

	public GameField_Sidebar_Level(GameField gamef) {
		super(gamef);
		lblGeneration.setSize(111, 14);
		btnHauptmen.setLocation(17, 546);
		btnPlay.setBounds(5, 30, 140, 23);
		lblGeneration.setLocation(5, 186);
		textFieldZeitProGeneration.setLocation(10, 146);
		setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		setLayout(null);

		JButton btnRestart = new JButton("Nochmal Versuchen");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Zum Restarten des Levels
				//Anhalten der Simulation ist nötig damit es nicht Crashed später
				gameField.getGuiHandler().getFuctionHandler().stopSimulation();
				gameField.getGuiHandler().getFuctionHandler().resetLevel(gameField.getGuiHandler());
			}
		});
		btnRestart.setBounds(5, 64, 140, 23);
		add(btnRestart);
		super.setSize(new Dimension(150, this.gameField.getGuiHandler()
				.getSize().height));
		this.setBounds(0, 0, 150, 800);

		txtpnZielBeschreibung = new JTextPane();
		txtpnZielBeschreibung
				.setText("Mindesten 3 Zellen noch am Leben nach 10 Generationen");
		txtpnZielBeschreibung.setBounds(5, 267, 140, 79);
		add(txtpnZielBeschreibung);

		lblZuplazierendeZellen = new JLabel("Zuplazierende Zellen: 0");
		lblZuplazierendeZellen.setBounds(5, 242, 140, 14);
		add(lblZuplazierendeZellen);
		
		lblLevel = new JLabel("Level 1");
		lblLevel.setFont(new Font("Agency FB", Font.PLAIN, 20));
		lblLevel.setBounds(57, 0, 59, 29);
		add(lblLevel);

	}

	public void setGeneration(int generation) {
		this.lblGeneration.setText("Generation: "
				+ Integer.toString(generation));
	}

	public void setTxtpnZielBeschreibung(String txtpnZielBeschreibung) {
		this.txtpnZielBeschreibung.setText(txtpnZielBeschreibung);
	}

	public void setlblZuplazierendeZellen(int number) {
		this.lblZuplazierendeZellen.setText("Zuplazierende Zellen: "
				+ Integer.toString(number));
	}
	
	public void setlblLevel(int number){
		this.lblLevel.setText("Level "+ Integer.toString(number));
	}
}
