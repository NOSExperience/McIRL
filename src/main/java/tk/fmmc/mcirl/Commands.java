package tk.fmmc.mcirl;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Commands {
	
	CommandSpec home = CommandSpec.builder()
			.description(Text.of("Test"))
			.executor(new HomeCommand())
			.build();
	
	public void register() {
		Sponge.getCommandManager().register(McIrl.getMcIrl(), home, "home");
	}

}
