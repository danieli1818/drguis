package drguis.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import drguis.management.GUIsEditingManager;
import drlibs.common.commands.AdvancedCommand;
import drlibs.common.commands.ReloadCommand;
import drlibs.common.files.FileConfigurationsUtils;
import drlibs.common.plugin.MessagesPlugin;
import drlibs.utils.functions.MapsUtils;

public class ReloadSubCommand extends ReloadCommand {

	public ReloadSubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String command) {
		super(plugin, fatherCommand, command, "Reloads all GUIs", "drguis.reload");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		System.out.println("Yay in reload!");
		if (!parentOnCommand(sender, command, label, args)) {
			System.out.println("In reload args parser!");
			switch (args.length) {
			case 0:
				return runOnCommand(sender, command, label, args);
			case 1:
				String guiFileName = args[0];
				FileConfigurationsUtils fcu = getPlugin().getFileConfigurationsUtils();
				guiFileName += ".yml";
				if (!fcu.loadFileConfiguration(guiFileName)) {
					getPlugin().getMessagesSender().sendTranslatedMessage("error_load_file_message", sender,
							MapsUtils.mapOf("file", guiFileName), null, null);
					return false;
				}
				GUIsEditingManager.getInstance().loadGUIsFromFile(guiFileName, true);
				break;
			default:
				getPlugin().getMessagesSender().sendTranslatedMessage(getInvalidCommandMessageID(), sender);
				return false;
			}
		}
		return true;
	}

}
