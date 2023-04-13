package drguis.commands.subcommands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import drguis.management.GUIsEditingManager;
import drlibs.common.commands.AdvancedCommand;
import drlibs.common.commands.SubCommand;
import drlibs.common.plugin.MessagesPlugin;
import drlibs.utils.functions.MapsUtils;

public class SaveSubCommand extends SubCommand {

	public SaveSubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String command) {
		super(plugin, fatherCommand, command, "Save GUIs", "drguis.save");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!super.onCommand(sender, command, label, args)) {
			for (String guiID : args) {
				String permission = "drguis.save." + guiID;
				if (!sender.hasPermission(permission)) {
					getPlugin().getMessagesSender().sendTranslatedMessage("error_saving_gui_no_permission", sender,
							MapsUtils.mapOf("gui", guiID, "permission", permission));
					continue;
				}
				try {
					if (!GUIsEditingManager.getInstance().saveGUI(guiID)) {
						getPlugin().getMessagesSender().sendTranslatedMessage("error_saving_gui_does_not_exist", sender,
								MapsUtils.mapOf("gui", guiID));
						continue;
					}
				} catch (IOException e) {
					getPlugin().getMessagesSender().sendTranslatedMessage("error_saving_gui_check_logs", sender,
							MapsUtils.mapOf("gui", guiID));
					e.printStackTrace();
					continue;
				}
				getPlugin().getMessagesSender().sendTranslatedMessage("successfully_saved_gui", sender,
						MapsUtils.mapOf("gui", guiID));
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			String prefix = args[0];
			List<String> autocompleteOptions = new ArrayList<>(GUIsEditingManager.getInstance().getGUIEditorsIDs())
					.stream()
					.filter((String id) -> id.startsWith(prefix) && (sender.hasPermission("drguis.save." + id)))
					.collect(Collectors.toList());
			for (String guiID : args) {
				autocompleteOptions.remove(guiID);
			}
			autocompleteOptions.sort(String::compareTo);
			return autocompleteOptions;
		}
		return null;
	}

}
