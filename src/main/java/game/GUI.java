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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


public class GUI extends JFrame {
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
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel homePanel = new JPanel();
		JLabel homeLabel = new JLabel("Home");
		homePanel.add(homeLabel);
		
		JPanel devPanel = new JPanel();
		JLabel devLabel = new JLabel("Search Video Games By Publisher");
		devPanel.add(devLabel);
	
		JPanel pubPanel = new JPanel();
		JLabel pubLabel = new JLabel("Search Video Games By Publisher");
		pubPanel.add(pubLabel);
		
		JPanel qPanel = queryPanel();
		
		tabbedPane.add("Home", homePanel);
		tabbedPane.add("General Queries", qPanel);
		tabbedPane.add("Search by Developer", devPanel);
		tabbedPane.add("Search by Publisher", pubPanel);
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
				  e.printStackTrace();
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
	
	public JScrollPane gridGames(List<String[]> games) {
		JPanel contentpanel = new JPanel();
		contentpanel.setLayout(new GridLayout(3,4));

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

			contentpanel.add(detailpanel);
			
		}
		
		JScrollPane scrollpane = new JScrollPane(contentpanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		
		return scrollpane;
	}
	public JPanel queryPanel() {
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
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
		
		//Search button
		JPanel btn_panel = new JPanel();
		JButton search_button = new JButton("Search");
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String selected_genre = genre_dd.getSelectedItem().toString();
				String selected_theme = theme_dd.getSelectedItem().toString();
				String selected_pers = ppers_dd.getSelectedItem().toString();
				String selected_mode = gmode_dd.getSelectedItem().toString();

		   		System.out.print("Button Clicked"+ selected_genre);
		   		List<String[]> games = new Queries().get_qgames(selected_genre, selected_theme, selected_pers, selected_mode);
		   		
		   		JScrollPane gamePane = gridGames(games);
		   		contentPanel.add(gamePane);

			}
			
		});
		btn_panel.add(search_button);
		

		//header Panel
		JPanel headerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		headerPanel.add(titlepanel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		headerPanel.add(search_panel,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 2;       //third row
		headerPanel.add(btn_panel, c);
		
		//Content Panel

		contentPanel.add(headerPanel);
		return contentPanel;
	}
	
	

	
}
