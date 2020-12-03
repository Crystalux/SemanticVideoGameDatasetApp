package game;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class GameDetailsPanel extends JPanel {
	Queries queries;
	JTabbedPane tabbedPane;

	public GameDetailsPanel(Queries queries, JTabbedPane tabbedPane) {
		this.queries = queries;
		this.tabbedPane = tabbedPane;
	}
	
	public void showGame(String game) {
		removeAll();
		add(gameDetail(game));
		revalidate();
		tabbedPane.setSelectedIndex(4);
	}
	
	public JPanel gameDetail(String title) {
		JPanel details = new JPanel();
		JPanel tablePanel = new JPanel();
		JPanel coverPanel = new JPanel();
		String[] info = queries.get_game_detail(title);
		info[1] = info[1].substring(0,10);
		String[] infolabels = {"Title", "Release Date", "Publisher", "Developer", "Platforms", "Age Rating", "Genres", "Themes", "Game Modes", "Player Perspective"};
		JTable table = new JTable();
		DefaultTableModel model = new DefaultTableModel(0,2) {
			   /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			   public boolean isCellEditable(int row, int col) {       
			       return false; // or a condition at your choice with row and column
			   }
		};
		table.setModel(model);
		for(int i = 0;  i< infolabels.length; i++) {
			model.addRow(new Object[] {infolabels[i], info[i]});
		}
		
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(200);
		columnModel.getColumn(1).setPreferredWidth(500);
		tablePanel.add(table);
		ImageIcon cover = GamesPanel.scaledImage(info[info.length - 1]);
		JLabel coverLabel = new JLabel(cover);
		coverPanel.add(coverLabel);
		details.add(tablePanel, BorderLayout.WEST);
		details.add(coverPanel, BorderLayout.EAST);
		return details;
	}
}
