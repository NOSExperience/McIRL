package tk.fmmc.mcirl;

import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class HomeCommand implements CommandExecutor{
	
	Parser p = new Parser();
	
	private Type type = Type.DEFAULT;
	
	public enum Type {
		DEL,
		ADD,
		DEFAULT
	}
	
	public HomeCommand(Type t) {
		this.type = t;
	}

	private String listHomes(String username){
		List<Home> all = p.getHomes(username);
		
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
	
	private void goHome(String name, CommandSource src){
		Home home = null;
		
		for(Home i: p.getHomes(src.getName())) {
			if(i.getName().equals(name)) {
				home = i;
				break;
			}
		}
		
		if(home != null){
			Sponge.getServer().getPlayer(src.getName()).get().setLocation(new Location<World>(Sponge.getServer().getPlayer(src.getName()).get().getWorld(), home.asVector3d()));
		} else {
			src.sendMessage(Text.of('"' + name + '"' + " does not exist"));
		}
	}
	
	private void delHome(String name, CommandSource src){
		if(listHomes(src.getName()).contains(name)){
			src.sendMessage(Text.of("Deleting Home " + name));
			p.delHome(src.getName(), name);	
		}else{
			src.sendMessage(Text.of(name + " does not exist"));
		}
	}
	
	private void addHome(String name, CommandSource src){
		p.addHome(src.getName(), name);
	}
	
	private void addHome(String name, CommandSource src, String options){
		p.addHome(src.getName(), name, options);
	}
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		//src.sendMessage(Text.of(p.returnStats(src.getName(), Options.HOMES, null)));
		switch(type){
		case DEFAULT:
			if(args.getOne("name").isPresent()){
				goHome(args.<String>getOne("name").get(), src);
				
			}else{
				src.sendMessage(Text.of(listHomes(src.getName())));
			}
			break;
		case DEL:
			delHome(args.<String>getOne("name").get(), src);
			break;
		case ADD:
			src.sendMessage(Text.of("Updating home " + args.<String>getOne("name").get()));
			if(args.getOne("option").isPresent()){
				addHome(args.<String>getOne("name").get(), src, args.<String>getOne("option").get());
			}else{
				addHome(args.<String>getOne("name").get(), src);
			}
			break;
		default:
			McIrl.getLogger().warn("Switch did something it never should...");
			break;
		}
		return CommandResult.success();
	
	}

}
