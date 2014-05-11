import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public abstract class GameField_Sidebar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 854410959421359941L;
	protected JButton btnPlay;
	protected JButton btnStop;
	protected JButton btnHauptmen;
	protected JTextField textFieldZeitProGeneration;
	protected JLabel lblGeneration;
	protected GameField gameField;
	protected JLabel lblLebendeZellen;

	/**
	 * Create the panel.
	 */
	public GameField_Sidebar(GameField gamef) {
		this.gameField = gamef;
		btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//Noch aus gründen drin falls Nimbus Look and Feel doch noch benutzt werden möchte einfach KOmmetnare entfernen 
					/*
					if(Integer.parseInt(textFieldZeitProGeneration.getText())<300){ //DAmit keine Render Fehler auftreten
						JOptionPane.showMessageDialog(null,
								"Die Zeit ist zu gering.", "Eingabe Problem",
								JOptionPane.ERROR_MESSAGE);
					}else{*/
						gameField.getGuiHandler().getFuctionHandler().startSimulation(
										Integer.parseInt(textFieldZeitProGeneration.getText()),
										gameField.getGuiHandler());
						btnPlay.setVisible(false);
						btnStop.setVisible(true);
					//}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,
							"Keine Ganzzahl eingegeben.", "Eingabe Problem",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnPlay.setBounds(10, 30, 116, 23);
		add(btnPlay);

		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameField.getGuiHandler().getFuctionHandler().stopSimulation();
				btnStop.setVisible(false);
				btnPlay.setVisible(true);
			}
		});
		btnStop.setBounds(10, 30, 116, 23);
		add(btnStop);
		btnStop.setVisible(false);

		textFieldZeitProGeneration = new JTextField();
		textFieldZeitProGeneration.setText("300");
		textFieldZeitProGeneration.setBounds(10, 183, 116, 29);
		add(textFieldZeitProGeneration);
		textFieldZeitProGeneration.setColumns(10);

		JLabel lblZeitFrEine = new JLabel("Zeit f\u00FCr eine Generation");
		lblZeitFrEine.setBounds(10, 98, 130, 14);
		add(lblZeitFrEine);

		JLabel lblInMillisekunden = new JLabel("in Millisekunden");
		lblInMillisekunden.setBounds(10, 123, 106, 14);
		add(lblInMillisekunden);

		lblGeneration = new JLabel("Generation: 0");
		lblGeneration.setBounds(10, 235, 106, 14);
		add(lblGeneration);

		lblLebendeZellen = new JLabel("Lebende Zellen: 0");
		lblLebendeZellen.setBounds(5, 211, 140, 14);
		add(lblLebendeZellen);

		btnHauptmen = new JButton("Hauptmen\u00FC");
		btnHauptmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameField.getGuiHandler().getFuctionHandler()
						.gotoMainMenu(gameField.getGuiHandler());
			}
		});
		btnHauptmen.setBounds(10, 546, 116, 23);
		add(btnHauptmen);

	}

	public void setGeneration(int generation) {
		this.lblGeneration.setText("Generation: "
				+ Integer.toString(generation));
	}

	public void setLebendeZellen(int zellen) {
		this.lblLebendeZellen.setText("Lebende Zellen: "
				+ Integer.toString(zellen));
	}

	public JButton getBtnPlay() {
		return btnPlay;
	}

	public JButton getBtnStop() {
		return btnStop;
	}
}
