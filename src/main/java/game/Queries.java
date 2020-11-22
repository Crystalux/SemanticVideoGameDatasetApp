package game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFS;

public class Queries {
	public static final String SOURCE = "./src/main/resources/data/";
	public static final String GAME_NS = "http://www.semanticweb.org/crystalux/ontologies/2020/9/Game#";
	
	OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
	//read ontology model
	public Queries() {
		FileManager.get().readModel( m, SOURCE + "Game.owl" );
	}
	
	public static String get_if_exists(QuerySolution qs, String colName) {
		return (qs.get(colName)==null) ? "" : qs.get(colName).toString();
	}
	
	public List<String> get_genre(){
		String prefix = "prefix game: <" + GAME_NS + ">\n"+
        		"prefix rdfs: <" + RDFS.getURI() + ">\n" ;
		
    	String query_text=  prefix +
    			"SELECT ?genre_name \r\n"
    			+ "WHERE{ \r\n"
    			+ "		    ?gen a game:genre. \r\n"
    			+ "		    ?gen game:name ?genre_name. \r\n"
    			+ "} \r\n"
    			+ "		ORDER BY ASC(?genre_name)";
 
    	
    	System.out.println(query_text);
    	
    	Query query = QueryFactory.create( query_text );
    	QueryExecution qexec = QueryExecutionFactory.create( query, m );
    	
    	List<String> genres = new ArrayList<String>();
    	
    	try {
    		
    		ResultSet results = qexec.execSelect();
    		while ( results.hasNext() ) {
    			QuerySolution qs = results.next();
    			genres.add(qs.get("genre_name").toString());
    			System.out.println(qs.get("genre_name").toString());
    		}
    	}
    	catch(NullPointerException e) {
    		
    	}
    	finally {
    		qexec.close();
    	} 
    	return genres;
	}

	public List<String> get_theme(){
		String prefix = "prefix game: <" + GAME_NS + ">\n"+
        		"prefix rdfs: <" + RDFS.getURI() + ">\n" ;
		
    	String query_text=  prefix +
    			"SELECT ?theme_name \r\n"
    			+ "WHERE{ \r\n"
    			+ "		    ?them a game:theme. \r\n"
    			+ "		    ?them game:name ?theme_name. \r\n"
    			+ "} \r\n"
    			+ "		ORDER BY ASC(?theme_name)";
 
    	
    	System.out.println(query_text);
    	
    	Query query = QueryFactory.create( query_text );
    	QueryExecution qexec = QueryExecutionFactory.create( query, m );
    	
    	List<String> themes = new ArrayList<String>();
    	
    	try {
    		
    		ResultSet results = qexec.execSelect();
    		while ( results.hasNext() ) {
    			QuerySolution qs = results.next();
    			themes.add(qs.get("theme_name").toString());
    			System.out.println(qs.get("theme_name").toString());
    		}
    	}
    	catch(NullPointerException e) {
    		
    	}
    	finally {
    		qexec.close();
    	} 
    	return themes;
	}
	
	public List<String> get_pperspective(){
		String prefix = "prefix game: <" + GAME_NS + ">\n"+
        		"prefix rdfs: <" + RDFS.getURI() + ">\n" ;
		
    	String query_text=  prefix +
    			"SELECT ?player_perspective \r\n"
    			+ "WHERE{ \r\n"
    			+ "		    ?pers a game:playerPerspective. \r\n"
    			+ "		    ?pers game:name ?player_perspective. \r\n"
    			+ "} \r\n"
    			+ "		ORDER BY ASC(?player_perspective)";
 
    	
    	System.out.println(query_text);
    	
    	Query query = QueryFactory.create( query_text );
    	QueryExecution qexec = QueryExecutionFactory.create( query, m );
    	
    	List<String> ppers = new ArrayList<String>();
    	
    	try {
    		
    		ResultSet results = qexec.execSelect();
    		while ( results.hasNext() ) {
    			QuerySolution qs = results.next();
    			ppers.add(qs.get("player_perspective").toString());
    			System.out.println(qs.get("player_perspective").toString());
    		}
    	}
    	catch(NullPointerException e) {
    		
    	}
    	finally {
    		qexec.close();
    	} 
    	return ppers;
	}
	
	public List<String> get_gmode(){
		String prefix = "prefix game: <" + GAME_NS + ">\n"+
        		"prefix rdfs: <" + RDFS.getURI() + ">\n" ;
		
    	String query_text=  prefix +
    			"SELECT ?game_mode \r\n"
    			+ "WHERE{ \r\n"
    			+ "		    ?mod a game:gameMode. \r\n"
    			+ "		    ?mod game:name ?game_mode. \r\n"
    			+ "} \r\n"
    			+ "		ORDER BY ASC(?game_mode)";
 
    	
    	System.out.println(query_text);
    	
    	Query query = QueryFactory.create( query_text );
    	QueryExecution qexec = QueryExecutionFactory.create( query, m );
    	
    	List<String> gmode = new ArrayList<String>();
    	
    	try {
    		
    		ResultSet results = qexec.execSelect();
    		while ( results.hasNext() ) {
    			QuerySolution qs = results.next();
    			gmode.add(qs.get("game_mode").toString());
    			System.out.println(qs.get("game_mode").toString());
    		}
    	}
    	catch(NullPointerException e) {
    		
    	}
    	finally {
    		qexec.close();
    	} 
    	return gmode;
	}
	
    public List<String[]> get_qgames(String genre, String theme, String pers, String mode) {
    	String prefix = "prefix game: <" + GAME_NS + ">\n"+
    			        "prefix rdfs: <" + RDFS.getURI() + ">\n" ;
    	String select_genre = (genre == "-- Select genre --")? "":"?game game:hasGenre ?genre. \r\n "
    			        	+"?genre game:name '" +genre+ "'. \r\n";   		
    	String select_theme = (theme == "-- Select theme --")? "":"?game game:hasTheme ?theme. \r\n "
	        	+"?theme game:name '" +theme+ "'. \r\n";   	
    	String select_pers = (pers == "-- Select player perspective --")? "":"?game game:hasPlayerPerspective ?ppers. \r\n "
	        	+"?ppers game:name '" +pers+ "'. \r\n";   	
    	String select_mode = (mode == "-- Select game mode --")? "":"?game game:hasGameMode ?gmode. \r\n "
	        	+"?gmode game:name '" +mode+ "'. \r\n";   	
    	String query_text=  prefix +
    			"SELECT ?title ?cover ?release_date \r\n"  
    	    	+ "WHERE { \r\n"
    	    	+"	?game a game:video_game. \r\n"
    	    	+"	?game game:title ?title. \r\n"
    	    	+"	OPTIONAL{ \r\n"
    	    	+"	?game game:cover ?cover.\r\n"
    	    	+"	} \r\n"
    	    	+"	OPTIONAL{\r\n"
    	    	+"	?game game:release_date ?release_date.\r\n"
    	    	+"	}	 \r\n"
    	    	+ select_genre
    	    	+ select_theme
    	    	+ select_pers
    	    	+ select_mode 
    	    	+"} \r\n"
    	    	+"ORDER BY DESC(?release_date) \r\n" 
    	    	+"LIMIT 12 \r\n"
    	    	+"OFFSET 1";
    	
    			
    	System.out.println(query_text);
    			
    	Query query = QueryFactory.create( query_text );
    	QueryExecution qexec = QueryExecutionFactory.create( query, m );
    	       
    	//List<String> genres = new ArrayList<String>();
    	List<String[]> games = new ArrayList<String[]>();
    	
    	    	        
        try {
    	      ResultSet results = qexec.execSelect();
    	      while ( results.hasNext() ) {
    	           QuerySolution qs = results.next();

                   games.add(new String[] {qs.get("title").toString(),get_if_exists(qs, "cover"), get_if_exists(qs, "release_date")});
                   System.out.println(qs.get("title").toString());
    	       }
    	          
    	}
    	catch(NullPointerException e) {
    	        	
    	}
    	finally {
               qexec.close();
        } 
    	        
    	return games;
    	        
    }

}
