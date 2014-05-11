import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameField extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1619486749319599783L;
	private JPanel contentPane;
	private GameField_Grid grid;
	private GameField_Sidebar sidebar;
	private GuiHandler guiHandler;

	/**
	 * Create the frame.
	 */
	public GameField(GuiHandler gui, int gametyp) {
		this.guiHandler = gui;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, this.guiHandler.getSize().width,
				gui.getSize().height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.grid = new GameField_Grid(this);
		this.add(grid);
		if (gametyp == 1) { // Gametyp = 1 Level Modi else Endlos
			this.sidebar = new GameField_Sidebar_Level(this);
			this.add(sidebar);
		}else if (gametyp==2){
			this.sidebar = new GameField_Sidebar_LevelEditor(this);
			this.add(sidebar);
		}else{
			this.sidebar = new GameField_Sidebar_Endlos(this);
			this.add(sidebar);
		}
	}

	public GameField_Grid getGrid() {
		return grid;
	}

	public GameField_Sidebar getSidebar() {
		return sidebar;
	}

	public GuiHandler getGuiHandler() {
		return guiHandler;
	}

	public void saveLevel(String zielGenerationen, String zielZellen, String zusetzendeZellen, String vergleichsOperator, String zielBeschreibung) {
		this.guiHandler.saveLevel(zielGenerationen, zielZellen, zusetzendeZellen,vergleichsOperator,zielBeschreibung, getGrid().getGrid_y());
	}

}
