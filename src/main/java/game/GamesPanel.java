package game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GamesPanel extends JPanel {
	
	Queries queries;
	QueryParams queryParams = new QueryParams();
	JPanel bodyPanel = new JPanel();
	JPanel footerPanel = new JPanel();
	
	GameDetailsPanel detailsPanel;

	public static ImageIcon getPlaceholderIcon() {
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
		return null;
	}
	
	static ImageIcon pPlaceIcon = getPlaceholderIcon();
	
	public GamesPanel(Queries queries, GameDetailsPanel detailsPanel) {
		this.queries = queries;
		this.detailsPanel = detailsPanel;
		
		bodyPanel = new JPanel();
		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
		JLabel bodyLabel = new JLabel("Aim: Find a game right for you!");
		bodyPanel.add(bodyLabel);
		footerPanel = new JPanel();
		footerPanel.add(pagePanel(0));
		
		setLayout(new BorderLayout());
		add(bodyPanel, BorderLayout.CENTER);
		add(footerPanel, BorderLayout.SOUTH);
	}
	
	public static ImageIcon scaledImage(String imageURL) {
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
			} catch (Exception e) {
				//e.printStackTrace();
				return pPlaceIcon;
			}
		} else {
			//place holder image
			return pPlaceIcon;
		}
	}
	
	private JScrollPane gridGames(List<String[]> games) {
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
					if(me.getClickCount() == 2) {
						detailsPanel.showGame(detail[0]);
					}
				}
			});
			contentpanel.add(detailpanel);
			contentpanel.setToolTipText(detail[0]);
		}
		
		JScrollPane scrollpane = new JScrollPane(contentpanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		
		return scrollpane;
	}
	
	private JPanel pagePanel(int i) {
		JPanel pPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
		int show= i+1;
		JLabel pageno = new JLabel("Page " + show);
		JButton first = new JButton("First");
		first.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(queryParams.pageNo != 0) {
					queryParams.pageNo = 0;
					updateGames();
				}
			}
			
		});
		JButton prev = new JButton("Prev");
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (queryParams.pageNo > 0) {
					queryParams.pageNo -=1;
					updateGames();

				}
			}
			
		});
		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(queryParams.pageNo<queryParams.lastPage) {
					queryParams.pageNo +=1;
					updateGames();
				}
			}
			
		});
		JButton last = new JButton("Last");
		last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(queryParams.pageNo != queryParams.lastPage) {
					queryParams.pageNo = queryParams.lastPage;
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
	
	public void updateGames() {
   		List<String[]> games = queries.get_qgames(queryParams.selected_genre, queryParams.selected_theme, queryParams.selected_ppers, 
   				queryParams.selected_gmode, queryParams.publisher, queryParams.developer, queryParams.pageNo, queryParams.selected_sort);
   		System.out.println(games.size() + " games in games");
   		JScrollPane gamePane = gridGames(games);
   		bodyPanel.removeAll();
   		bodyPanel.add(gamePane);
   		
   		footerPanel.removeAll();
   		footerPanel.add(pagePanel(queryParams.pageNo));
   		
   		bodyPanel.revalidate();
   		footerPanel.revalidate();
	}

}
