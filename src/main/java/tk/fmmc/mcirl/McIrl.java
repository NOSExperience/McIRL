package tk.fmmc.mcirl;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.inject.Inject;

@Plugin(id = "mcirl", name = "McIRL", version = "1.0")
public class McIrl {
	private static McIrl INSTANCE = null;
	
	private Commands commands;
	
	@Inject
	private static Logger logger;
	
	public McIrl() {
		INSTANCE = this;
		commands = new Commands();
	}
	
	public static McIrl getMcIrl() {
		return INSTANCE;
	}
	
	@Inject
	private void setLogger(Logger l){
		logger = l;
	}
	
	public static Logger getLogger(){
		return logger;
	}
	
	@Listener
	public void onStart(GameStartedServerEvent e){
		getLogger().warn("Uh oh! It Works!");
		commands.register();
	}
}
