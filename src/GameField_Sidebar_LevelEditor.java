import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class GameField_Sidebar_LevelEditor extends GameField_Sidebar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldZielGenerationen;
	private JTextField textFieldZielZellen;
	private JTextField textFieldZusetzendeZellen;
	private JTextField textFieldVergleichsOperator;
	private JTextPane textPaneZielBeschreibung;

	/**
	 * Create the panel.
	 */
	public GameField_Sidebar_LevelEditor(GameField gamef) {
		super(gamef);
		lblLebendeZellen.setLocation(5, 245);
		lblGeneration.setSize(111, 14);
		btnHauptmen.setLocation(17, 546);
		btnPlay.setBounds(5, 30, 140, 23);
		lblGeneration.setLocation(5, 220);
		textFieldZeitProGeneration.setLocation(10, 146);
		setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		setLayout(null);

		JButton btnRestart = new JButton("Löschen");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//IF Bedingung nötig damit kein null Pointer ensteht falls
				//der singlestep button zuerst gedrückt wird bevor die simulationg estartet wurde
				//Anhalten der Simulation ist nötig damit es nicht Crashed später
				if(gameField.getGuiHandler().getFuctionHandler().getTimer() != null){
					gameField.getGuiHandler().getFuctionHandler().stopSimulation();
				}
				
				gameField.getGrid().clearFields();
			}
		});
		btnRestart.setBounds(5, 64, 140, 23);
		add(btnRestart);
		super.setSize(new Dimension(150, this.gameField.getGuiHandler()
				.getSize().height));
		this.setBounds(0, 0, 150, 800);
		
		//singelStep Simulations Button
		JButton btnSingleStep = new JButton("Generation Vorw\u00E4rts");
		btnSingleStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameField.getGuiHandler().getFuctionHandler().simulation(gameField.getGrid().getGrid_y(), gameField.getGuiHandler());
			}
		});
		btnSingleStep.setBounds(5, 186, 140, 23);
		add(btnSingleStep);
		
		JLabel lblZielGenerationen = new JLabel("Ziel Generationen");
		lblZielGenerationen.setBounds(5, 270, 111, 14);
		add(lblZielGenerationen);
		
		textFieldZielGenerationen = new JTextField();
		textFieldZielGenerationen.setBounds(5, 284, 140, 23);
		add(textFieldZielGenerationen);
		textFieldZielGenerationen.setColumns(10);
		
		JLabel lblZielAnZellen = new JLabel("Ziel an Zellen");
		lblZielAnZellen.setBounds(5, 315, 140, 14);
		add(lblZielAnZellen);
		
		textFieldZielZellen = new JTextField();
		textFieldZielZellen.setBounds(5, 329, 140, 23);
		add(textFieldZielZellen);
		textFieldZielZellen.setColumns(10);
		
		JLabel lblZusetzendeZellen = new JLabel("Zusetzende Zellen");
		lblZusetzendeZellen.setBounds(5, 356, 140, 14);
		add(lblZusetzendeZellen);
		
		textFieldZusetzendeZellen = new JTextField();
		textFieldZusetzendeZellen.setBounds(6, 369, 140, 23);
		add(textFieldZusetzendeZellen);
		textFieldZusetzendeZellen.setColumns(10);
		
		JLabel lblVergleichsOperator = new JLabel("Vergleichs Operator");
		lblVergleichsOperator.setBounds(5, 395, 140, 14);
		add(lblVergleichsOperator);
		
		textFieldVergleichsOperator = new JTextField();
		textFieldVergleichsOperator.setBounds(5, 409, 140, 23);
		add(textFieldVergleichsOperator);
		textFieldVergleichsOperator.setColumns(10);
		
		textPaneZielBeschreibung = new JTextPane();
		textPaneZielBeschreibung.setBackground(Color.LIGHT_GRAY);
		textPaneZielBeschreibung.setBounds(5, 457, 140, 78);
		add(textPaneZielBeschreibung);
		
		JLabel lblZielBeschreibung = new JLabel("Ziel Beschreibung");
		lblZielBeschreibung.setBounds(5, 443, 140, 14);
		add(lblZielBeschreibung);
		
		JButton btnSave = new JButton("Speichern");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{ //Kontrolliere auf Richtige eingabe
					Integer.parseInt(textFieldZielGenerationen.getText());
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "Kein Gültiger Wert bei der Generationen Anzahl angegeben.",
							"Fehler", JOptionPane.INFORMATION_MESSAGE);
				}
				try{ //Kontrolliere auf Richtige eingabe
					Integer.parseInt(textFieldZielZellen.getText());
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "Kein Gültiger Wert bei den Ziel Zellen.",
							"Fehler", JOptionPane.INFORMATION_MESSAGE);
				}
				try{ //Kontrolliere auf Richtige eingabe
					Integer.parseInt(textFieldZusetzendeZellen.getText());
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "Kein Gültiger Wert bei den zusetzenden Zellen angegeben",
							"Fehler", JOptionPane.INFORMATION_MESSAGE);
				}
				if(!(textFieldVergleichsOperator.getText().contains("<") || textFieldVergleichsOperator.getText().contains(">") || textFieldVergleichsOperator.getText().contains("="))){
					JOptionPane.showMessageDialog(null, "Kein Gültiger Vergleichs Operator angegeben",
							"Fehler", JOptionPane.INFORMATION_MESSAGE);
				}else if(textPaneZielBeschreibung.getText()==""){
					JOptionPane.showMessageDialog(null, "Keine Ziel Beschreibung angegeben",
							"Fehler", JOptionPane.INFORMATION_MESSAGE);
				}else{
					gameField.saveLevel(
						textFieldZielGenerationen.getText(),
						textFieldZielZellen.getText(),
						textFieldZusetzendeZellen.getText(),
						textFieldVergleichsOperator.getText(),
						textPaneZielBeschreibung.getText()
					);
					JOptionPane.showMessageDialog(null, "Level wurde erfolgreich gespeichert",
							"Level gespeichert", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnSave.setBounds(5, 0, 140, 23);
		add(btnSave);


	}

	public void setGeneration(int generation) {
		this.lblGeneration.setText("Generation: "
				+ Integer.toString(generation));
	}
}
