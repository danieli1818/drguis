package drguis.commands.subcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import drguis.management.GUIsEditingManager;
import drguis.models.types.editors.GUIEditor;
import drlibs.common.commands.AdvancedCommand;
import drlibs.common.commands.SubCommand;
import drlibs.common.plugin.MessagesPlugin;
import drlibs.utils.functions.MapsUtils;

public class EditSubCommand extends SubCommand {

	public EditSubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String command) {
		super(plugin, fatherCommand, command, "Edit GUI", "drguis.edit");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!super.onCommand(sender, command, label, args)) {
			if (!(sender instanceof Player)) {
				getPlugin().getMessagesSender().sendTranslatedMessage(getPlayerCommandMessageID(), sender);
				return false;
			}
			switch (args.length) {
			case 1:
				String guiID = args[0];
				GUIEditor editor = GUIsEditingManager.getInstance().getGUIEditor(guiID);
				if (editor == null) {
					getPlugin().getMessagesSender().sendTranslatedMessage("error_gui_id_does_not_exist", sender,
							MapsUtils.mapOf("id", guiID), null, null);
					return false;
				}
				if (!editor.editGUI((Player) sender)) {
					getPlugin().getMessagesSender().sendTranslatedMessage("error_could_not_edit_gui", sender,
							MapsUtils.mapOf("id", guiID), null, null);
					return false;
				}
				return true;
			default:
				getPlugin().getMessagesSender().sendTranslatedMessage(getInvalidCommandMessageID(), sender);
				return false;
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			String prefix = args[0];
			List<String> autocompleteOptions = new ArrayList<>(GUIsEditingManager.getInstance().getGUIEditorsIDs())
					.stream().filter((String id) -> id.startsWith(prefix) && sender.hasPermission("drguis.edit." + id))
					.collect(Collectors.toList());
			autocompleteOptions.sort(String::compareTo);
			return autocompleteOptions;
		}
		return null;
	}

}
