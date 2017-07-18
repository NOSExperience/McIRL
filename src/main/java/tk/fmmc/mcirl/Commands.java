package tk.fmmc.mcirl;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Commands {
	
	CommandSpec addHome = CommandSpec.builder()
			.description(Text.of("Add a home!"))
			.executor(new HomeCommand(HomeCommand.Type.ADD))
			.build();
	CommandSpec delHome = CommandSpec.builder()
			.description(Text.of("Delete a home!"))
			.executor(new HomeCommand(HomeCommand.Type.DEL))
			.build();
	CommandSpec home = CommandSpec.builder()
			.description(Text.of("Test"))
			.executor(new HomeCommand(HomeCommand.Type.DEFAULT))
			.child(addHome, "add")
			.child(delHome, "del")
			.arguments(GenericArguments.optional(GenericArguments.string(Text.of("name"))))
			.build();

	public void register() {
		Sponge.getCommandManager().register(McIrl.getMcIrl(), home, "home");
		
	}

}
