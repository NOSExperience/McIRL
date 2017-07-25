package tk.fmmc.mcirl;

import com.flowpowered.math.vector.Vector3d;

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
	
	public Vector3d asVector3d() {
		String[] working = coords.split(", ");
		return new Vector3d(Double.parseDouble(working[0]), Double.parseDouble(working[1]), Double.parseDouble(working[2]));
	}
}
