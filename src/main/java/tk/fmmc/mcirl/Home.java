package tk.fmmc.mcirl;

public class Home {

	private String name; 
	private String coords;
	public Home(String name, String coords) {
		super();
		this.name = name;
		this.coords = coords;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCoords() {
		return coords;
	}
	public void setCoords(String coords) {
		this.coords = coords;
	}
	
	
}
