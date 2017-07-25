package tk.fmmc.mcirl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
	
	public List<Home> getHomes(String username) {
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
		return all;
	}
	
	public void addHome(String username, String homename){
		File playerfile = getPlayerFile(username);
		
		try{
			Document d = dBuilder.parse(playerfile);
			Location<World> location = Sponge.getServer().getPlayer(username).get().getLocation();
		
			boolean exists = false;
			
			for(Home i: getHomes(username)){
				if(i.getName().equals(homename)){
					exists = true;
				}
			}
			
			if(exists == false){
				Element home = d.createElement(HOME_TAG);
				home.setAttribute(HOME_ATTRIB_NAME, homename);
				home.setTextContent(location.getX() + ", " + location.getY() + ", " + location.getZ());
				d.getElementsByTagName(HOME_SECTION_TAG).item(0).appendChild(home);
				
				DOMSource source = new DOMSource(d);
				
				TransformerFactory transFactory = TransformerFactory.newInstance();
				Transformer transformer = transFactory.newTransformer();
				StreamResult xmlResult = new StreamResult(getPlayerFile(username));
				transformer.transform(source, xmlResult);
			}else{
				Sponge.getServer().getPlayer(username).get().sendMessage(Text.of("This home already exists! type /home add " + homename + " -f to change this home"));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addHome(String username, String homename, String option){
		File playerfile = getPlayerFile(username);
		
		try{
			Document d = dBuilder.parse(playerfile);
			Location<World> location = Sponge.getServer().getPlayer(username).get().getLocation();
			
			if(option.equals("-f")){
				NodeList nodes = d.getElementsByTagName(HOME_SECTION_TAG);
				for(int i = 0; i < nodes.getLength(); i++) {
					Node n = nodes.item(i);
				
					
					NodeList home_nodes = n.getChildNodes();
					for(int j = 0; j < home_nodes.getLength(); j++) {
						Node home = home_nodes.item(j);
						if(home.getNodeName() == HOME_TAG) {
							NamedNodeMap map = home.getAttributes();
							Node attrib = map.getNamedItem(HOME_ATTRIB_NAME);
							String content = attrib.getTextContent();
							if(content.equals(homename)){
								home.setTextContent(location.getX() + ", " + location.getY() + ", " + location.getZ());
							}
						}
					}
				}
				
				DOMSource source = new DOMSource(d);
				
				TransformerFactory transFactory = TransformerFactory.newInstance();
				Transformer transformer = transFactory.newTransformer();
				StreamResult xmlResult = new StreamResult(getPlayerFile(username));
				transformer.transform(source, xmlResult);
			}else{
				Sponge.getServer().getPlayer(username).get().sendMessage(Text.of("This argument does not exist!"));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void delHome(String username, String homename){
		File playerfile = getPlayerFile(username);
		
		try{
			Document d = dBuilder.parse(playerfile);
			NodeList nodes = d.getElementsByTagName(HOME_SECTION_TAG);
			for(int i = 0; i < nodes.getLength(); i++) {
				Node n = nodes.item(i);
			
				
				NodeList home_nodes = n.getChildNodes();
				for(int j = 0; j < home_nodes.getLength(); j++) {
					Node home = home_nodes.item(j);
					if(home.getNodeName() == HOME_TAG) {
						NamedNodeMap map = home.getAttributes();
						Node attrib = map.getNamedItem(HOME_ATTRIB_NAME);
						String content = attrib.getTextContent();
						if(content.equals(homename)){
							n.removeChild(home);
						}
					}
				}
			}			

			
			DOMSource source = new DOMSource(d);
			
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			StreamResult xmlResult = new StreamResult(getPlayerFile(username));
			transformer.transform(source, xmlResult);	
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
