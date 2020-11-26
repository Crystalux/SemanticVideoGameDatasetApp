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
	BuildQuery buildQuery;
	JPanel bodyPanel;
	JPanel footerPanel;
	JPanel countPanel;
	JPanel gameInfo;
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
		setTitle("A Semantic Video Game Database");
		
		setResizable(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width, (int) ((screenSize.height/2)*1.5));
		int total_width = screenSize.width/2;
		int label_width = total_width/4;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Tabbed panes for different queries
		tabbedPane = new JTabbedPane();
		JPanel homePanel = new JPanel();
		JLabel homeLabel = new JLabel("Home");
		homePanel.add(homeLabel);
		
		JPanel devPanel = new JPanel();
		JLabel devLabel = new JLabel("Search Video Games By Publisher");
		devPanel.add(devLabel);
		
		JPanel qPanel = queryPanel();
		JPanel pubPanel = publisherPanel();
		gameInfo = new JPanel();
		JLabel infoLabel = new JLabel("Games selected will be viewed here");
		gameInfo.add(infoLabel);
		
		tabbedPane.add("Home", homePanel);
		tabbedPane.add("General Queries", qPanel);
		tabbedPane.add("Search by Developer", devPanel);
		tabbedPane.add("Search by Publisher", pubPanel);
		tabbedPane.add("Game Details", gameInfo);
		add(tabbedPane);
		
	}
	
	public ImageIcon scaledImage(String imageURL) {
		if (imageURL != "") {
			try {
				  URL url = new URL(imageURL);
				  HttpURLConnection httpcon = (HttpURLConnection) url.openConnection(); 
				  httpcon.addRequestProperty("User-Agent", ""); 
				  BufferedImage image = ImageIO.read(httpcon.getInputStream());
				  ImageIcon urlIcon = new ImageIcon(image);
				  
				  Image urlImage = urlIcon.getImage();
				  Image modifiedImage = urlImage.getScaledInstance(200,300, java.awt.Image.SCALE_SMOOTH);
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
					    Image modifiedImage = placeImage.getScaledInstance(200,300, java.awt.Image.SCALE_SMOOTH);
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
	
	public ImageIcon scaledLogo(String imageURL) {
		if (imageURL != "") {
			try {
				  URL url = new URL(imageURL);
				  HttpURLConnection httpcon = (HttpURLConnection) url.openConnection(); 
				  httpcon.addRequestProperty("User-Agent", ""); 
				  BufferedImage image = ImageIO.read(httpcon.getInputStream());
				  ImageIcon urlIcon = new ImageIcon(image);
				  
				  Image urlImage = urlIcon.getImage();
				  Image modifiedImage = urlImage.getScaledInstance(200,150, java.awt.Image.SCALE_SMOOTH);
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
					    Image modifiedImage = placeImage.getScaledInstance(200,150, java.awt.Image.SCALE_SMOOTH);
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


	
	public JPanel gameDetail(String title) {
		JPanel details = new JPanel();
		JPanel tablePanel = new JPanel();
		JPanel coverPanel = new JPanel();
		String[] info = new Queries().get_game_detail(title);
		info[1] = info[1].substring(0,10);
		String[] infolabels = {"Title", "Release Date", "Publisher", "Developer", "Platforms", "Age Rating", "Genres", "Themes", "Game Modes", "Player Perspective"};
		JTable table = new JTable();
		DefaultTableModel model = new DefaultTableModel(0,2);
		table.setModel(model);
		for(int i = 0;  i< infolabels.length; i++) {
			model.addRow(new Object[] {infolabels[i], info[i]});
		}
		
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(200);
		columnModel.getColumn(1).setPreferredWidth(500);
		tablePanel.add(table);
		ImageIcon cover = scaledImage(info[info.length - 1]);
		JLabel coverLabel = new JLabel(cover);
		coverPanel.add(coverLabel);
		details.add(tablePanel, BorderLayout.WEST);
		details.add(coverPanel, BorderLayout.EAST);
		return details;
	}
	
	public JScrollPane gridGames(List<String[]> games) {
		JPanel contentpanel = new JPanel();
		int cols = Math.min(4,  games.size());
		int rows = Math.min((int)Math.ceil(games.size() / 4), 3);
		contentpanel.setLayout(new GridLayout(rows,cols));

		for(String[] detail : games) {
			//overall panel for this game detail.
			JPanel detailpanel = new JPanel();
			detailpanel.setLayout(new BoxLayout(detailpanel, BoxLayout.Y_AXIS));
			//title panel
			JPanel ptitle = new JPanel();
			JLabel ltitle = new JLabel(detail[0]);
			ptitle.add(ltitle);
			
			//cover panel
			JPanel pcover = new JPanel();
			JLabel lcover = new JLabel(scaledImage(detail[1]));
			pcover.add(lcover);
			
			//date panel
			JPanel pdate = new JPanel();
			JLabel ldate = new JLabel("Release date: " + detail[2].substring(0,10));
			pdate.add(ldate);
			
			detailpanel.add(pcover);
			detailpanel.add(ptitle);
			detailpanel.add(pdate);
			detailpanel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					gameInfo.removeAll();
					gameInfo.add(gameDetail(detail[0]));
					tabbedPane.setSelectedIndex(4);
				}
			});
			contentpanel.add(detailpanel);
			contentpanel.setToolTipText(detail[0]);
		}
		
		JScrollPane scrollpane = new JScrollPane(contentpanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		
		return scrollpane;
	}
	
	public JPanel pagePanel(int i) {
		JPanel pPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
		int show= i+1;
		JLabel pageno = new JLabel("Page " + show);
		JButton first = new JButton("First");
		first.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(buildQuery.pageNo != 0) {
					buildQuery.pageNo = 0;
					updateGames();
				}
			}
			
		});
		JButton prev = new JButton("Prev");
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (buildQuery.pageNo > 0) {
					buildQuery.pageNo -=1;
					updateGames();

				}
			}
			
		});
		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(buildQuery.pageNo<buildQuery.lastPage) {
					buildQuery.pageNo +=1;
					updateGames();
				}
			}
			
		});
		JButton last = new JButton("Last");
		last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(buildQuery.pageNo != buildQuery.lastPage) {
					buildQuery.pageNo = buildQuery.lastPage;
					updateGames();
				}
			}
			
		});
		
		
		pPanel.add(first);
		pPanel.add(prev);
		pPanel.add(pageno);
		pPanel.add(next);
		pPanel.add(last);
		
		return pPanel;
	}
	
	private void updateGames() {
   		List<String[]> games = new Queries().get_qgames(buildQuery.selected_genre, buildQuery.selected_theme, buildQuery.selected_ppers, 
   				buildQuery.selected_gmode, buildQuery.pageNo, buildQuery.selected_sort);
   		System.out.println(games.size() + " games in games");
   		JScrollPane gamePane = gridGames(games);
   		bodyPanel.removeAll();
   		bodyPanel.add(gamePane);
   		
   		footerPanel.removeAll();
   		footerPanel.add(pagePanel(buildQuery.pageNo));
	}
	

	public JPanel queryPanel() {
		
		JPanel contentPanel = new JPanel();
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		bodyPanel = new JPanel();
		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
		JLabel bodyLabel = new JLabel("This is a content label");
		bodyPanel.add(bodyLabel);
		footerPanel = new JPanel();
		footerPanel.add(pagePanel(0));
		
		//Title Panel
		JPanel titlepanel = new JPanel();
		JLabel titleLabel = new JLabel("General video game queries");
		titlepanel.add(titleLabel);
		
		//Genre Dropdown Box
		JPanel search_panel =  new JPanel();
		search_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		Queries query =new Queries();
		
		List<String> genres = query.get_genre();
		//Add a select item at the start of the list
		genres.add(0,"-- Select genre --");
		JComboBox<String>  genre_dd = new JComboBox(genres.toArray(new String[genres.size()]));
		search_panel.add(genre_dd);
		
		List<String> themes = query.get_theme();
		//Add a select item at the start of the list
		themes.add(0,"-- Select theme --");
		JComboBox<String>  theme_dd = new JComboBox(themes.toArray(new String[themes.size()]));
		search_panel.add(theme_dd);
		
		List<String> ppers = query.get_pperspective();
		//Add a select item at the start of the list
		ppers.add(0,"-- Select player perspective --");
		JComboBox<String>  ppers_dd = new JComboBox(ppers.toArray(new String[ppers.size()]));
		search_panel.add(ppers_dd);
		
		List<String> gmode = query.get_gmode();
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
				
				buildQuery = new BuildQuery();
				buildQuery.selected_genre = genre_dd.getSelectedItem().toString();
				buildQuery.selected_theme = theme_dd.getSelectedItem().toString();
				buildQuery.selected_ppers = ppers_dd.getSelectedItem().toString();
				buildQuery.selected_gmode = gmode_dd.getSelectedItem().toString();
				buildQuery.selected_sort = selectSort.getSelectedItem().toString();
				buildQuery.pageNo=0;
				
				int count  = new Queries().count_qGames(buildQuery.selected_genre, buildQuery.selected_theme, buildQuery.selected_ppers, buildQuery.selected_gmode);
				countPanel.removeAll();
				JLabel countLabel = new JLabel(count + " games found");
				countPanel.add(countLabel);
				buildQuery.lastPage = Math.floorDiv(count -1, 12);
				updateGames();
				
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

		
		contentPanel.add(headerPanel);
		countPanel = new JPanel();
		contentPanel.add(countPanel);
		contentPanel.add(bodyPanel);
		contentPanel.add(footerPanel);
		return contentPanel;
	}
	
	public JPanel publisherPanel() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		JPanel header = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		
		JPanel body = new JPanel();
		countPanel = new JPanel();
		JPanel footer = new JPanel();
		

		contentPanel.add(header, BorderLayout.NORTH);
		contentPanel.add(body, BorderLayout.CENTER);
		contentPanel.add(footer, BorderLayout.SOUTH);
		
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
				
				int count = new Queries().count_publisher(pubName);
				String[] header = {count+" publisher(s) found"};
				
				List<String[]> publishers =  new Queries().get_publisher(pubName, pubSort);
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
