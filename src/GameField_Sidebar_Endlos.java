import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.MatteBorder;

public class GameField_Sidebar_Endlos extends GameField_Sidebar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2613506381052995545L;

	/**
	 * Create the panel.
	 */
	public GameField_Sidebar_Endlos(GameField gamef) {
		super(gamef);
		lblGeneration.setLocation(10, 184);
		textFieldZeitProGeneration.setLocation(10, 144);
		setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		setLayout(null);

		JButton btnLschen = new JButton("L\u00F6schen");
		btnLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Zum Löschen der Benutzten Felder
				//Anhalten der Simulation ist nötig damit es nicht Crashed später
				gameField.getGuiHandler().getFuctionHandler().stopSimulation();
				gameField.getGrid().clearFields();
			}
		});
		btnLschen.setBounds(10, 64, 116, 23);
		add(btnLschen);
		this.setSize(new Dimension(150, this.gameField.getGuiHandler().getSize().height));
		this.setBounds(0, 0, 150, 800);
	}

}
