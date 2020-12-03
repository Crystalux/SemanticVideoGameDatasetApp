package game;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	public String[] get_game_detail(String title) {
    	String prefix = "prefix game: <" + GAME_NS + ">\n"+
    			        "prefix rdfs: <" + RDFS.getURI() + ">\n" ;
    	
    	String release_date = "OPTIONAL{ ?game game:release_date ?release_date}";
    	String publisher = "OPTIONAL{ ?game game:publishedBy|^game:published ?pub. \r\n"
    			+ "     ?pub game:name ?publisher} \r\n";
    	String developer = "OPTIONAL{ ?game game:developedBy|^game:developed ?dev. \r\n"
    			+ "     ?dev game:name ?developer} \r\n";
    	String platform = "OPTIONAL{ ?game game:availableOn ?plat.\r\n"
    			+ "          ?plat game:name ?platform.} \r\n";
    	String ageRating = "OPTIONAL{ ?p rdfs:subPropertyOf game:hasAgeRating.\r\n"
    			+ "    	     ?game ?p ?age. \r\n"
    			+ "          ?age game:name ?ageRate.} \r\n";
    	String genres="OPTIONAL{ ?game game:hasGenre ?gen. \r\n"
    			+ "          ?gen game:name ?genre.} \r\n";
    	String themes="OPTIONAL{ ?game game:hasTheme ?the. \r\n"
    			+ "          ?the game:name ?theme.} \r\n";
    	String gameModes="OPTIONAL{ ?game game:hasGameMode ?mode. \r\n"
    			+ "          ?mode game:name ?gmode.} \r\n";
    	String ppers="OPTIONAL{ ?game game:hasPlayerPerspective ?pper. \r\n"
    			+ "          ?pper game:name ?ppers.} \r\n";
    	String cover = "OPTIONAL{?game game:cover ?cover} \r\n";
    	String filter = "FILTER(STR(?title) = \""+title+"\" )";
    	
    	String query_text1=  prefix +
    			"SELECT ?title \r\n"  
    			+ "(GROUP_CONCAT(DISTINCT(?platform); SEPARATOR=', ') AS ?platforms) \r\n"
    			+ "(GROUP_CONCAT(DISTINCT(?ageRate); SEPARATOR=', ') AS ?ageRating) \r\n"
    			+ "(GROUP_CONCAT(DISTINCT(?genre); SEPARATOR=', ') AS ?genres) \r\n"
    			+ "(GROUP_CONCAT(DISTINCT(?theme); SEPARATOR=', ') AS ?themes) \r\n"
    			+ "(GROUP_CONCAT(DISTINCT(?gmode); SEPARATOR=', ') AS ?gameModes) \r\n"
    			+ "(GROUP_CONCAT(DISTINCT(?ppers); SEPARATOR=', ') AS ?playerPerspectives) \r\n"
    	    	+ "WHERE { \r\n"
    	    	+"	?game a game:video_game. \r\n"
    	    	+"	?game game:title ?title. \r\n"
    	    	+ platform
    	    	+ ageRating
    	    	+ genres
    	    	+ themes
    	    	+ gameModes
    	    	+ ppers
    	    	+ filter
    	    	+"} \r\n"
    	    	+"GROUP BY ?title";
    	String query_text2=  prefix +
    			"SELECT ?title ?release_date ?publisher ?developer ?cover \r\n"
    	    	+ "WHERE { \r\n"
    	    	+"	?game a game:video_game. \r\n"
    	    	+"	?game game:title ?title. \r\n"
    	    	+ release_date
    	    	+ publisher
    	    	+ developer
    	    	+ cover
    	    	+ filter
    	    	+"} \r\n";
    	
    	
    	ArrayList<String> games = new ArrayList<String>();
    	System.out.println(query_text1);
    	Query query1 = QueryFactory.create( query_text1);
    	QueryExecution qexec1 = QueryExecutionFactory.create( query1, m );
    	
    	//List<String> genres = new ArrayList<String>();
    	

    	
    	    	        
        try {
    	      ResultSet results = qexec1.execSelect();
    	      while ( results.hasNext() ) {
    	           QuerySolution qs = results.next();
    	           
                   String[] list1 = {qs.get("title").toString(),get_if_exists(qs,"platforms"), get_if_exists(qs,"ageRating"), get_if_exists(qs,"genres"), 
                		   get_if_exists(qs,"themes"), get_if_exists(qs,"gameModes"),get_if_exists(qs,"playerPerspectives")};
                   
                   games.addAll(Arrays.asList(list1));
                   System.out.println(qs.get("title").toString());
    	       }
    	          
    	}
    	catch(NullPointerException e) {
    	        	
    	}
    	finally {
               qexec1.close();
        }

    	System.out.println(query_text2);
    	Query query2 = QueryFactory.create( query_text2);
    	QueryExecution qexec2 = QueryExecutionFactory.create( query2, m );
    	
        try {
  	      ResultSet results = qexec2.execSelect();
  	      while ( results.hasNext() ) {
  	           QuerySolution qs = results.next();
  	           String[] list2 = {qs.get("title").toString(),get_if_exists(qs,"release_date"), get_if_exists(qs,"publisher"), get_if_exists(qs,"developer"), get_if_exists(qs,"cover")};
  	           String[] slice1 = Arrays.copyOfRange(list2, 1,4);
  	           String[] slice2 = Arrays.copyOfRange(list2, 4,5);
  	           games.addAll(1, Arrays.asList(slice1));
  	           games.addAll(Arrays.asList(slice2));

               System.out.println(qs.get("cover").toString());
  	       }
  	          
        }
        catch(NullPointerException e) {
  	        	
        }
        finally {
             qexec2.close();
        }
        
        String[] game = games.stream().toArray(String[]::new);
    	return game;
    	        
    }

	
    public List<String[]> get_qgames(String genre, String theme, String pers, String mode, String publisher, String developer, int pageno, String sort) {
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
    	String select_publisher = (publisher == "")? "":"  ?game game:publishedBy|^game:published ?pub.\r\n"
    			+ "  ?pub game:name \""+publisher+"\"\r\n";
    	String select_developer = (developer == "")? "":"  ?game game:developedBy|^game:developed ?dev.\n"
    			+ "  ?dev game:name \""+developer+"\"\r\n";
    	
    	String orderBy;
    	if(sort == "Release Date Descending") {
    		orderBy = "DESC(?release_date)";
    	} else if(sort == "Release Date Ascending"){
    		orderBy = "ASC(?release_date)";
    	} else if(sort == "Alphabetical Ascending") {
    		orderBy = "ASC(?title)";
    	} else {
    		orderBy = "DESC(?title)";
    	}
  
    	int offset = pageno * 12;
    	
    	
    	
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
    	    	+ select_developer
    	    	+ select_publisher
    	    	+"} \r\n"
    	    	+"ORDER BY " + orderBy + " \r\n" 
    	    	+"LIMIT 12 \r\n"
    	    	+"OFFSET " + offset;
    	
    			
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
    
    public int count_qGames(String genre, String theme, String pers, String mode) {
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
    			"SELECT (COUNT (?game) as ?total_games) \r\n"  
    	    	+ "WHERE { \r\n"
    	    	+"	?game a game:video_game. \r\n"
    	    	+ select_genre
    	    	+ select_theme
    	    	+ select_pers
    	    	+ select_mode
    	    	+"} \r\n";
    	
    			
    	System.out.println(query_text);
    			
    	Query query = QueryFactory.create( query_text );
    	QueryExecution qexec = QueryExecutionFactory.create( query, m );
    	       
    	//List<String> genres = new ArrayList<String>();
    	int count = 0;
    	
    	    	        
        try {
    	      ResultSet results = qexec.execSelect();
    	      while ( results.hasNext() ) {
    	           QuerySolution qs = results.next();

                   String strCount = qs.get("total_games").toString();
                   String[] parts = strCount.split("\\^\\^");
                   count = Integer.parseInt(parts[0]);
                   
                   System.out.println(count + " games founded");

    	      }
    	          
    	}
    	catch(NullPointerException e) {
    	        	
    	}
    	finally {
               qexec.close();
        } 
    	        
    	return count;
    	        
    }
	
	
	public List<String[]> get_company(String company, String devpub, String sort){
		String prefix = "prefix game: <" + GAME_NS + ">\n"+
        		"prefix rdfs: <" + RDFS.getURI() + ">\n" ;

    	String orderBy;
    	if(sort == "Alphabetical Ascending") {
    		orderBy = "ASC(?publisher)";
    	} else if(sort == "Alphabetical Descending"){
    		orderBy = "DESC(?publisher)";
    	} else {
    		orderBy = "DESC(?gameCount)";
    	}
    	
    	String rdfClass;
    	String rdfProperty;
    	if(devpub == "pub") {
    		rdfClass="publisher";
    		rdfProperty="published";
    	}else {
    		rdfClass = "developer";
    		rdfProperty="developed";
    	}
    	
    	
    	
    	String query_text=  prefix +
    			"SELECT ?company (COUNT(?game)AS?gameCount) \r\n"
    			+ "WHERE{ \r\n"
    			+ "		    ?com a game:"+rdfClass+". \r\n"
    			+ "		    ?com game:name ?company. \r\n"
    			+"          OPTIONAL{?com game:"+rdfProperty+" ?game.} \r\n"
    			+ "		    FILTER(REGEX(?company, \""+company+"\", \"i\")) \r\n"
    			+ "} \r\n"
    			+ "GROUP BY ?company \r\n"
    			+ "ORDER BY "+ orderBy+"\r\n";
    	
    	System.out.println(query_text);
    	
    	Query query = QueryFactory.create( query_text );
    	QueryExecution qexec = QueryExecutionFactory.create( query, m );
    	
    	List<String[]> com = new ArrayList<String[]>();
    	
    	try {
    		
    		ResultSet results = qexec.execSelect();
    		while ( results.hasNext() ) {
    			QuerySolution qs = results.next();
    			com.add(new String[]{qs.get("company").toString(), qs.get("gameCount").toString()});
    			System.out.println(qs.get("company").toString());
    		}
    	}
    	catch(NullPointerException e) {
    		
    	}
    	finally {
    		qexec.close();
    	} 
    	return com;
	}

	
	public int count_company(String company, String devpub){
		String prefix = "prefix game: <" + GAME_NS + ">\n"+
        		"prefix rdfs: <" + RDFS.getURI() + ">\n" ;
		
		String rdfClass;
    	if(devpub == "pub") {
    		rdfClass="publisher";
    	}else {
    		rdfClass = "developer";
    	}


    	String query_text=  prefix +
    			"SELECT (COUNT(?com) AS ?total_com) \r\n"
    			+ "WHERE{ \r\n"
    			+ "		    ?com a game:"+rdfClass+". \r\n"
    			+ "		    ?com game:name ?company. \r\n"
    			+ "		    FILTER(REGEX(?company, \""+company+"\", \"i\")) \r\n"
    			+ "} \r\n";
 
    	
    	System.out.println(query_text);
    	
    	Query query = QueryFactory.create( query_text );
    	QueryExecution qexec = QueryExecutionFactory.create( query, m );
    	
    	
    	int count=0;
    	try {
    		
    		ResultSet results = qexec.execSelect();
    		while ( results.hasNext() ) {
    			QuerySolution qs = results.next();

                String strCount = qs.get("total_com").toString();
                String[] parts = strCount.split("\\^\\^");
                count = Integer.parseInt(parts[0]);
                
                System.out.println(count + rdfClass + " found");
    		}
    	}
    	catch(NullPointerException e) {
    		
    	}
    	finally {
    		qexec.close();
    	}
    	return count;
	}
    
    public String[] get_company_detail(String company, String devpub){
		String prefix = "prefix game: <" + GAME_NS + ">\n"+
        		"prefix rdfs: <" + RDFS.getURI() + ">\n" ;
		
    	String rdfClass;
    	String rdfProperty;
    	if(devpub == "pub") {
    		rdfClass="publisher";
    		rdfProperty="published";
    	}else {
    		rdfClass = "developer";
    		rdfProperty="developed";
    	}
    	
    	String query_text1=  prefix +
    			"SELECT ?company ?country ?foundingDate ?website ?logo\r\n"
    			+ "WHERE{ \r\n"
    			+ "		    ?com a game:"+rdfClass+". \r\n"
    			+ "		    ?com game:name ?company. \r\n"
    			+ "			OPTIONAL{?com game:locatedIn ?loc. \r\n"
    			+ "					 ?loc game:name ?country} \r\n"
    			+ "			OPTIONAL{?com game:foundedOn ?foundingDate}\r\n"
    			+ "			OPTIONAL{?com game:company_url ?website}\r\n"
    			+ "			OPTIONAL{?com game:logo ?logo}\r\n"
    			+ "			FILTER(STR(?company) = \""+ company+"\")"
    			+ "} \r\n";
 
    	String query_text2 = prefix+
    			"SELECT (COUNT(?games) as ?totalGames)\r\n"
    			+ "WHERE{ \r\n"
    			+ "		?com a game:publisher; \r\n"
    			+ "		      game:name ?company; \r\n"
    			+ "		      game:"+rdfProperty+" ?games.\r\n"
    			+ "		FILTER(STR(?company) = \""+ company+"\")\r\n"
    			+ "} \r\n";
    	
    	System.out.println(query_text1);
    	Query query1 = QueryFactory.create(query_text1);
    	QueryExecution qexec1 = QueryExecutionFactory.create( query1, m );
    	
    	
    	ArrayList<String> details = new ArrayList<String>();
    	
    	try {
    		
    		ResultSet results = qexec1.execSelect();
    		while ( results.hasNext() ) {
    			QuerySolution qs = results.next();
    			
    			String[] list1 = {qs.get("company").toString(),get_if_exists(qs,"country"), get_if_exists(qs,"foundingDate"), 
    					get_if_exists(qs,"website"), get_if_exists(qs,"logo")};
                
                details.addAll(Arrays.asList(list1));
                System.out.println(qs.get("company").toString());
    		}
    	}
    	catch(NullPointerException e) {
    		
    	}
    	finally {
    		qexec1.close();
    	}
    	
    	System.out.println(query_text2);
    	Query query2 = QueryFactory.create(query_text2);
    	QueryExecution qexec2 = QueryExecutionFactory.create( query2, m );
    	
    	try {
    		
    		ResultSet results = qexec2.execSelect();
    		while ( results.hasNext() ) {
    			QuerySolution qs = results.next();
    			
    			String[] list2 = {qs.get("totalGames").toString()};
                
                details.addAll(4,Arrays.asList(list2));
                System.out.println(qs.get("totalGames").toString());
    		}
    	}
    	catch(NullPointerException e) {
    		
    	}
    	finally {
    		qexec1.close();
    	} 

        String[] detail = details.stream().toArray(String[]::new);
    	return detail;
	}



}


