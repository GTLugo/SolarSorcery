package gtlugo.solarsorcery.commands;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class SolSorcCommand extends CommandTreeBase {
	
	public SolSorcCommand() {
		addSubcommand(new SetManaLevelCommand());
		addSubcommand(new AddManaCommand());
		addSubcommand(new SubManaCommand());
		addSubcommand(new SetManaCommand());
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "solsorc";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "solsorc.command.base.usage";
	}

}
