package tk.fmmc.mcirl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {
	/*
	public enum Options{
		HOMES,
		HOMECOORDS
	}
	*/
	
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	
	public Parser() {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static final String HOME_SECTION_TAG = "homes";
	private static final String HOME_TAG = "home";
	private static final String HOME_ATTRIB_NAME = "name";
	
	/*
	public Object returnStats(String player, Options option, Object arg0){
		File basedir = new File("./players");
		
		Object result = null;
		
		if(!basedir.exists()){
			basedir.mkdir();
			McIrl.getLogger().warn("Players directory did not exist, atempted to create.");
		}
		
		File playerfile = new File("./" + basedir.getName() + "/" + player + ".xml");
		if(!playerfile.exists()){
			try {
				playerfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document d = dBuilder.parse(playerfile);
			
			List<String> homelist = new ArrayList<String>();
			
			if(option == Options.HOMES){
				for(int homen = 0; d.getElementById("homes").getChildNodes().getLength() < homen; homen++){
					homelist.add(d.getElementById("homes").getChildNodes().item(homen).getNodeName().toString());
				}
				result = homelist;
			}else{
				result = false;
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	*/
	
	private File getPlayerFile(String username) {
		File basedir = new File("./players");
		
		if(!basedir.exists()){
			basedir.mkdir();
			McIrl.getLogger().warn("Players directory did not exist, atempted to create.");
		}
		
		File playerfile = new File("./" + basedir.getName() + "/" + username + ".xml");
		if(!playerfile.exists()){
			try {
				playerfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return playerfile;
	}
	
	public String getHome(String username) {
		File playerfile = getPlayerFile(username);
		List<Home> all = new ArrayList<>();
		
		try {
			Document d = dBuilder.parse(playerfile);
			
			NodeList nodes = d.getElementsByTagName(HOME_SECTION_TAG);
			for(int i = 0; i < nodes.getLength(); i++) {
				Node n = nodes.item(i);
				
				NodeList home_nodes = n.getChildNodes();
				for(int j = 0; j < home_nodes.getLength(); j++) {
					Node home = home_nodes.item(j);
					
					if(home.getNodeName() == HOME_TAG) {
						//getLogger().info("Found home:");
						
						String coords = home.getTextContent();
						if(coords != null) {
							NamedNodeMap map = home.getAttributes();
							if(map != null) {
								Node attrib = map.getNamedItem(HOME_ATTRIB_NAME);
								if(attrib != null) {
									String content = attrib.getTextContent();
									if(content != null) {
										all.add(new Home(content, coords));
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(all.size() != 0) {
			String ret = new String();
			for(Home i: all) {
				ret += i.getName() + ", ";
			}
			
			String suffix = (all.size() == 1) ? " is" : "s are";
			return "Your home" + suffix + ": " + ret.substring(0, ret.length() - 2);
		} else {
			return "You haven't set any homes!";
		}
	}

}
