package tk.fmmc.mcirl;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class HomeCommand implements CommandExecutor{
	
	Parser p = new Parser();

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		//src.sendMessage(Text.of(p.returnStats(src.getName(), Options.HOMES, null)));
		src.sendMessage(Text.of(p.getHome(src.getName())));
		return CommandResult.success();
	}

}
