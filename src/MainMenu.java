import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;

public class MainMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1204277567023071119L;
	private JPanel contentPane;
	private GuiHandler guiHandler;

	public MainMenu(GuiHandler gui) {
		this.guiHandler = gui;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnEndlosSpiel = new JButton("Endlos Spiel");

		btnEndlosSpiel.setBounds(123, 78, 187, 46);
		contentPane.add(btnEndlosSpiel);
		btnEndlosSpiel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiHandler.startEndlos();
			}
		});
		JButton btnLevels = new JButton("Levels");
		btnLevels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiHandler.startLevel();
			}
		});
		btnLevels.setBounds(123, 135, 187, 46);
		contentPane.add(btnLevels);

		JButton btnBeenden = new JButton("Beenden");
		btnBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnBeenden.setBounds(123, 192, 187, 46);
		contentPane.add(btnBeenden);

		JLabel lblConwaysGameOf = new JLabel("Conway's Game of Life");
		lblConwaysGameOf.setFont(new Font("Agency FB", Font.PLAIN, 30));
		lblConwaysGameOf.setBounds(112, 11, 235, 56);
		contentPane.add(lblConwaysGameOf);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guiHandler.startSettings();
			}
		});
		btnNewButton.setIcon(new ImageIcon(MainMenu.class
				.getResource("/resources/images/settings-icon.png")));
		btnNewButton.setBounds(357, 11, 67, 66);
		contentPane.add(btnNewButton);
	}
}
