package gtlugo.solarsorcery.commands;

import gtlugo.solarsorcery.handlers.NetworkHandler;
import gtlugo.solarsorcery.networking.messages.LevelChangeMessage;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class SetManaLevelCommand extends CommandBase {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "setLevel";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "pe.command.setlevel.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		int level = Integer.parseInt(args[0]);
		
		if (args.length < 1)
		{
			throw new WrongUsageException(getUsage(sender));
		}
		for (EntityPlayerMP player : getPlayers(server, sender, args[0]))
		{
			//IPlayerData data = player.getCapability(PlayerDataProvider.TAG_DATA, null);
			if (player.getName() == sender.getName()) {
				NetworkHandler.INSTANCE.sendToServer(new LevelChangeMessage(level, true, false));
			}
		}
	}

}
