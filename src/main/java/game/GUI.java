package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


public class GUI extends JFrame {
	JTabbedPane tabbedPane;
	JPanel countPanel;
	GameDetailsPanel gameInfo;
	
	Queries queries = new Queries();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		setTitle("A Semantic Video Game Knowledgebase");
		
		setResizable(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width, (int) ((screenSize.height/2)*1.5));
		int total_width = screenSize.width/2;
		int label_width = total_width/4;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Tabbed panes for different queries
		tabbedPane = new JTabbedPane();
		gameInfo = new GameDetailsPanel(queries, tabbedPane);
		JPanel homePanel = new JPanel();
		JLabel homeLabel = new JLabel("Home");
		homePanel.add(homeLabel);
		
		
		JPanel qPanel = queryPanel();
		JPanel pubPanel = publisherPanel();
		JPanel devPanel = developerPanel();
		JLabel infoLabel = new JLabel("Games selected will be viewed here");
		gameInfo.add(infoLabel);
		
		tabbedPane.add("Home", homePanel);
		tabbedPane.add("General Queries", qPanel);
		tabbedPane.add("Search by Developer", devPanel);
		tabbedPane.add("Search by Publisher", pubPanel);
		tabbedPane.add("Game Details", gameInfo);
		add(tabbedPane);
		
	}
	
	public ImageIcon scaledLogo(String imageURL) {
		if (imageURL != "") {
			try {
				  URL url = new URL(imageURL);
				  HttpURLConnection httpcon = (HttpURLConnection) url.openConnection(); 
				  httpcon.addRequestProperty("User-Agent", ""); 
				  BufferedImage image = ImageIO.read(httpcon.getInputStream());
				  ImageIcon urlIcon = new ImageIcon(image);
				  
				  Image urlImage = urlIcon.getImage();
				  Image modifiedImage = urlImage.getScaledInstance(100,100, java.awt.Image.SCALE_SMOOTH);
				  urlIcon = new ImageIcon(modifiedImage);
				  return urlIcon;
				 } 
				 catch (Exception e) {
				  //e.printStackTrace();
				  BufferedImage pimage = null;
					try {
					    pimage = ImageIO.read(new File("./src/main/resources/images/placeholder.png"));
					    
					    ImageIcon placeIcon = new ImageIcon(pimage);
					    Image placeImage = placeIcon.getImage();
					    Image modifiedImage = placeImage.getScaledInstance(100,100, java.awt.Image.SCALE_SMOOTH);
						placeIcon = new ImageIcon(modifiedImage);
						
						return placeIcon;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				 }

		}else {
			//place holder image
			BufferedImage pimage = null;
			try {
			    pimage = ImageIO.read(new File("./src/main/resources/images/placeholder.png"));
			    
			    ImageIcon placeIcon = new ImageIcon(pimage);
			    Image placeImage = placeIcon.getImage();
			    Image modifiedImage = placeImage.getScaledInstance(200,300, java.awt.Image.SCALE_SMOOTH);
				placeIcon = new ImageIcon(modifiedImage);
				
				return placeIcon;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
		

	public JPanel queryPanel() {
		
		JPanel contentPanel = new JPanel();
		GamesPanel gamesPanel = new GamesPanel(queries, gameInfo);
		
		contentPanel.setLayout(new BorderLayout());
		
		//Title Panel
		JPanel titlepanel = new JPanel();
		JLabel titleLabel = new JLabel("General video game queries");
		titlepanel.add(titleLabel);
		
		//Genre Dropdown Box
		JPanel search_panel =  new JPanel();
		search_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		List<String> genres = queries.get_genre();
		//Add a select item at the start of the list
		genres.add(0,"-- Select genre --");
		JComboBox<String>  genre_dd = new JComboBox(genres.toArray(new String[genres.size()]));
		search_panel.add(genre_dd);
		
		List<String> themes = queries.get_theme();
		//Add a select item at the start of the list
		themes.add(0,"-- Select theme --");
		JComboBox<String>  theme_dd = new JComboBox(themes.toArray(new String[themes.size()]));
		search_panel.add(theme_dd);
		
		List<String> ppers = queries.get_pperspective();
		//Add a select item at the start of the list
		ppers.add(0,"-- Select player perspective --");
		JComboBox<String>  ppers_dd = new JComboBox(ppers.toArray(new String[ppers.size()]));
		search_panel.add(ppers_dd);
		
		List<String> gmode = queries.get_gmode();
		//Add a select item at the start of the list
		gmode.add(0,"-- Select game mode --");
		JComboBox<String>  gmode_dd = new JComboBox(gmode.toArray(new String[gmode.size()]));
		search_panel.add(gmode_dd);
		
		JPanel sortPanel = new JPanel();
		JComboBox<String> selectSort = new JComboBox<String>();
		selectSort.addItem("Release Date Descending");
		selectSort.addItem("Release Date Ascending");
		selectSort.addItem("Alphabetical Ascending");
		selectSort.addItem("Alphabetical Descending");
		sortPanel.add(selectSort);
		
		//Search button
		JPanel btn_panel = new JPanel();
		JButton search_button = new JButton("Search");
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				QueryParams queryParams = new QueryParams();
				queryParams.selected_genre = genre_dd.getSelectedItem().toString();
				queryParams.selected_theme = theme_dd.getSelectedItem().toString();
				queryParams.selected_ppers = ppers_dd.getSelectedItem().toString();
				queryParams.selected_gmode = gmode_dd.getSelectedItem().toString();
				queryParams.selected_sort = selectSort.getSelectedItem().toString();
				queryParams.developer ="";
				queryParams.publisher="";
				queryParams.pageNo=0;
				
				gamesPanel.queryParams = queryParams;
				
				int count  = queries.count_qGames(queryParams.selected_genre, queryParams.selected_theme, queryParams.selected_ppers, queryParams.selected_gmode);
				countPanel.removeAll();
				JLabel countLabel = new JLabel(count + " games found");
				countPanel.add(countLabel);
				queryParams.lastPage = Math.floorDiv(count -1, 12);
				gamesPanel.updateGames();
				
			}
			
		});
		btn_panel.add(search_button);
		
		// Sort Panel


		//header Panel
		JPanel headerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.33;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		headerPanel.add(titlepanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		headerPanel.add(search_panel,c);
		
		c.fill = GridBagConstraints.WEST;
		c.gridx = 0;       //aligned with button 2
		c.gridwidth = 1;   //1 columns wide
		c.gridy = 2;       //third row
		headerPanel.add(sortPanel, c);
		
		c.fill = GridBagConstraints.EAST;
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 1;   //2 columns wide
		c.gridy = 2;       //third row
		headerPanel.add(btn_panel, c);
		
		//Content Panel

		contentPanel.add(headerPanel, BorderLayout.NORTH);
		JPanel body = new JPanel();
		body.setLayout(new BorderLayout());		
		countPanel = new JPanel();
		body.add(countPanel, BorderLayout.NORTH);
		body.add(gamesPanel, BorderLayout.CENTER);
		contentPanel.add(body, BorderLayout.CENTER);
		return contentPanel;
	}
	
	public JPanel publisherPanel() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		JPanel header = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		
		JPanel body = new JPanel();
		body.setLayout(new BorderLayout());
		

		contentPanel.add(header, BorderLayout.NORTH);
		contentPanel.add(body, BorderLayout.CENTER);
		
		JPanel titlePanel = new JPanel();
		JLabel titleText = new JLabel("Search games by publisher");
		titlePanel.add(titleText);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JPanel textPanel =new JPanel();
		JTextField textField = new JTextField();
		textField.setColumns(20);
		textField.setText("Enter a publisher name");
		textPanel.add(textField);
		
		JPanel sortPanel = new JPanel();
		JComboBox<String> selectSort = new JComboBox<String>();
		selectSort.addItem("Alphabetical Ascending");
		selectSort.addItem("Alphabetical Descending");
		selectSort.addItem("Games Released");
		sortPanel.add(selectSort);
		
		JPanel buttonPanel = new JPanel();
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pubName = textField.getText();
				String pubSort = selectSort.getSelectedItem().toString();
				
				int count = queries.count_company(pubName, "pub");
				String[] header = {count+" publisher(s) found"};
				
				List<String[]> publishers = queries.get_company(pubName, "pub", pubSort);
				JPanel publisherList = new JPanel();
				publisherList.setLayout(new BorderLayout());
				JTable table = new JTable();
				DefaultTableModel model = new DefaultTableModel(header,0) {
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
				
				
				for(String[] publisher : publishers) {
					model.addRow(new Object[] {publisher[0]});
				}

				TableColumnModel columnModel = table.getColumnModel();
				columnModel.getColumn(0).setPreferredWidth(500);
				
				JScrollPane tableContainer = new JScrollPane(table);
				
				publisherList.add(tableContainer, BorderLayout.CENTER);
				body.removeAll();
				body.add(publisherList);
				
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						if(me.getClickCount() == 2) {
							JTable target  = (JTable)me.getSource();
							int row = target.getSelectedRow();
							int column = target.getSelectedColumn();
							String publisherName = table.getValueAt(row, column).toString();
							
							body.removeAll();

							String[] details = queries.get_company_detail(publisherName,"pub");
							if(details[2] !="") {
								details[2] = details[2].substring(0,4);
							}
							//details[4]
							if(details[4] != "") {
								String[] parts = details[4].split("\\^\\^");
				                details[4] = parts[0];
							}
							String[] infolabels = {"Name", "Location", "Founding Year", "Website", "# games published"};
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
								model.addRow(new Object[] {infolabels[i], details[i]});
							}
							
							TableColumnModel columnModel = table.getColumnModel();
							columnModel.getColumn(0).setPreferredWidth(200);
							columnModel.getColumn(1).setPreferredWidth(500);
							JPanel tablePanel = new JPanel();
							tablePanel.add(table);
							System.out.println(details[details.length - 1]);
							ImageIcon cover = scaledLogo(details[details.length - 1]);
							JLabel coverLabel = new JLabel(cover);
							JPanel coverPanel = new JPanel();
							coverPanel.add(coverLabel);
							JPanel detailPanel = new JPanel();
							detailPanel.setLayout(new BorderLayout());
							detailPanel.add(tablePanel, BorderLayout.WEST);
							detailPanel.add(coverPanel, BorderLayout.EAST);
							body.add(detailPanel, BorderLayout.NORTH);
							
							
							if(!details[4].equals("0")) {
								JPanel gamePanel = new JPanel();
								gamePanel.setLayout(new BorderLayout());
								body.add(gamePanel, BorderLayout.CENTER);
								
								JPanel sortPanel = new JPanel();
								JComboBox<String> selectSort = new JComboBox<String>();
								selectSort.addItem("Release Date Descending");
								selectSort.addItem("Release Date Ascending");
								selectSort.addItem("Alphabetical Ascending");
								selectSort.addItem("Alphabetical Descending");
								sortPanel.add(selectSort);
								GamesPanel gamesPanel = new GamesPanel(queries, gameInfo);
								gamePanel.add(sortPanel, BorderLayout.NORTH);
								gamePanel.add(gamesPanel, BorderLayout.CENTER);
								///new QueryParams
								QueryParams queryParams = new QueryParams();
								queryParams.selected_genre = "-- Select genre --";
								queryParams.selected_theme = "-- Select theme --";
								queryParams.selected_ppers = "-- Select player perspective --";
								queryParams.selected_gmode = "-- Select game mode --";
								queryParams.selected_sort = selectSort.getSelectedItem().toString();
								queryParams.developer = "";
								queryParams.publisher=publisherName;
								queryParams.pageNo=0;

								gamesPanel.queryParams = queryParams;
								
								int count  = Integer.parseInt(details[4]);
								countPanel.removeAll();
								JLabel countLabel = new JLabel(count + " games found");
								countPanel.add(countLabel);
								queryParams.lastPage = Math.floorDiv(count -1, 12);
								gamesPanel.updateGames();
								
								///update game
								
							}
						
						}
						
					}
				});
			}
			
		});
		buttonPanel.add(searchButton);
		
		searchPanel.add(textPanel);
		searchPanel.add(sortPanel);
		searchPanel.add(buttonPanel);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.33;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		header.add(titlePanel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		header.add(searchPanel, c);
		
		
		return contentPanel;
	}

	public JPanel developerPanel() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		JPanel header = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		
		JPanel body = new JPanel();
		body.setLayout(new BorderLayout());
		

		contentPanel.add(header, BorderLayout.NORTH);
		contentPanel.add(body, BorderLayout.CENTER);
		
		JPanel titlePanel = new JPanel();
		JLabel titleText = new JLabel("Search games by developer");
		titlePanel.add(titleText);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JPanel textPanel =new JPanel();
		JTextField textField = new JTextField();
		textField.setColumns(20);
		textField.setText("Enter a developer name");
		textPanel.add(textField);
		
		JPanel sortPanel = new JPanel();
		JComboBox<String> selectSort = new JComboBox<String>();
		selectSort.addItem("Alphabetical Ascending");
		selectSort.addItem("Alphabetical Descending");
		selectSort.addItem("Games Released");
		sortPanel.add(selectSort);
		
		JPanel buttonPanel = new JPanel();
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String devName = textField.getText();
				String devSort = selectSort.getSelectedItem().toString();
				
				int count = queries.count_company(devName, "dev");
				String[] header = {count+" developer(s) found"};
				
				List<String[]> developers = queries.get_company(devName, "dev", devSort);
				JPanel developerList = new JPanel();
				developerList.setLayout(new BorderLayout());
				JTable table = new JTable();
				DefaultTableModel model = new DefaultTableModel(header,0) {
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
				
				
				for(String[] developer : developers) {
					model.addRow(new Object[] {developer[0]});
				}

				TableColumnModel columnModel = table.getColumnModel();
				columnModel.getColumn(0).setPreferredWidth(500);
				
				JScrollPane tableContainer = new JScrollPane(table);
				
				developerList.add(tableContainer, BorderLayout.CENTER);
				body.removeAll();
				body.add(developerList);
				
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						if(me.getClickCount() == 2) {
							JTable target  = (JTable)me.getSource();
							int row = target.getSelectedRow();
							int column = target.getSelectedColumn();
							String developerName = table.getValueAt(row, column).toString();
							
							body.removeAll();

							String[] details = queries.get_company_detail(developerName,"dev");
							// year founded
							if(details[2] != "") {
								details[2] = details[2].substring(0,4);
							}
							//games developed
							if(details[4] != "") {
								String[] parts = details[4].split("\\^\\^");
				                details[4] = parts[0];
							}
							String[] infolabels = {"Name", "Location", "Founding Year", "Website", "# games developed"};
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
								model.addRow(new Object[] {infolabels[i], details[i]});
							}
							
							TableColumnModel columnModel = table.getColumnModel();
							columnModel.getColumn(0).setPreferredWidth(200);
							columnModel.getColumn(1).setPreferredWidth(500);
							JPanel tablePanel = new JPanel();
							tablePanel.add(table);
							
							ImageIcon cover = scaledLogo(details[details.length - 1]);
							JLabel coverLabel = new JLabel(cover);
							JPanel coverPanel = new JPanel();
							coverPanel.add(coverLabel);
							JPanel detailPanel = new JPanel();
							detailPanel.setLayout(new BorderLayout());
							detailPanel.add(tablePanel, BorderLayout.WEST);
							detailPanel.add(coverPanel, BorderLayout.EAST);
							body.add(detailPanel, BorderLayout.NORTH);
							
							if(!details[4].equals("0")) {
								
								JPanel gamePanel = new JPanel();
								gamePanel.setLayout(new BorderLayout());
								body.add(gamePanel, BorderLayout.CENTER);
								
								JPanel sortPanel = new JPanel();
								JComboBox<String> selectSort = new JComboBox<String>();
								selectSort.addItem("Release Date Descending");
								selectSort.addItem("Release Date Ascending");
								selectSort.addItem("Alphabetical Ascending");
								selectSort.addItem("Alphabetical Descending");
								sortPanel.add(selectSort);
								GamesPanel gamesPanel = new GamesPanel(queries, gameInfo);
								gamePanel.add(sortPanel, BorderLayout.NORTH);
								gamePanel.add(gamesPanel, BorderLayout.CENTER);
								///new QueryParams
								QueryParams queryParams = new QueryParams();
								queryParams.selected_genre = "-- Select genre --";
								queryParams.selected_theme = "-- Select theme --";
								queryParams.selected_ppers = "-- Select player perspective --";
								queryParams.selected_gmode = "-- Select game mode --";
								queryParams.selected_sort = selectSort.getSelectedItem().toString();
								queryParams.developer = developerName;
								queryParams.publisher="";
								queryParams.pageNo=0;

								gamesPanel.queryParams = queryParams;
								
								int count  = Integer.parseInt(details[4]);
								countPanel.removeAll();
								JLabel countLabel = new JLabel(count + " games found");
								countPanel.add(countLabel);
								queryParams.lastPage = Math.floorDiv(count -1, 12);
								gamesPanel.updateGames();
								
								///update game
								
							}
						
						}
						
					}
				});
			}
			
		});
		buttonPanel.add(searchButton);
		
		searchPanel.add(textPanel);
		searchPanel.add(sortPanel);
		searchPanel.add(buttonPanel);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.33;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		header.add(titlePanel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		header.add(searchPanel, c);
		
		
		return contentPanel;
	}

}
