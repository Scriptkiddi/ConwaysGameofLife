import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Settings extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private GuiHandler guiHandler;

	/**
	 * Create the frame.
	 */
	public Settings(GuiHandler gui) {
		this.guiHandler = gui;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblEinstellungen = new JLabel("Einstellungen");
		lblEinstellungen.setBounds(153, 5, 128, 36);
		lblEinstellungen.setFont(new Font("Agency FB", Font.PLAIN, 30));
		contentPane.add(lblEinstellungen);

		JButton btnLevelFortschrittZurck = new JButton(
				"Level Fortschritt zur\u00FCck setzen");
		btnLevelFortschrittZurck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiHandler.getFuctionHandler().getDatenhandler().resetLevel();
			}
		});
		btnLevelFortschrittZurck.setBounds(10, 52, 414, 23);
		contentPane.add(btnLevelFortschrittZurck);

		JButton btnMainMenu = new JButton("Hauptmen\u00FC");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiHandler.getFuctionHandler().gotoMainMenu(guiHandler);
			}
		});
		btnMainMenu.setBounds(10, 228, 110, 23);
		contentPane.add(btnMainMenu);
		
		JButton btnLevelEditor = new JButton("Level Editor");
		btnLevelEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiHandler.startLevelEditor();
			}
		});
		btnLevelEditor.setBounds(10, 86, 414, 23);
		contentPane.add(btnLevelEditor);
		
		JButton btnDeleteLastLevel = new JButton("Letztes Level L\u00F6schen");
		btnDeleteLastLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guiHandler.getFuctionHandler().deletelastLevel();
			}
		});
		btnDeleteLastLevel.setBounds(10, 120, 414, 23);
		contentPane.add(btnDeleteLastLevel);
	}
}
