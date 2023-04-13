package drguis.commands.subcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import drguis.api.GUIsAPI;
import drguis.management.GUIsEditingManager;
import drguis.models.types.editors.GUIEditor;
import drlibs.common.commands.AdvancedCommand;
import drlibs.common.commands.SubCommand;
import drlibs.common.plugin.MessagesPlugin;
import drlibs.utils.functions.MapsUtils;

public class ShowSubCommand extends SubCommand {

	public ShowSubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String command) {
		super(plugin, fatherCommand, command, "Shows the GUI to you or to a player", "drguis.show");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!super.onCommand(sender, command, label, args)) {
			String guiID = null;
			switch (args.length) {
			case 2:
				guiID = args[0];
				if (!sender.hasPermission("drguis.show.other." + guiID)) {
					getPlugin().getMessagesSender().sendTranslatedMessage(getNoPermissionMessageID(), sender,
							MapsUtils.mapOf("permission", "drguis.show.other." + guiID));
					return false;
				}
				String otherPlayerStr = args[1];
				Player otherPlayer = Bukkit.getPlayer(otherPlayerStr);
				if (otherPlayer == null || !otherPlayer.isOnline()) {
					getPlugin().getMessagesSender().sendTranslatedMessage("error_invalid_player_online", sender,
							MapsUtils.mapOf("player", otherPlayerStr), null, null);
					return false;
				}
				return showGUIToPlayer(guiID, otherPlayer, sender);
			case 1:
				if (!(sender instanceof Player)) {
					getPlugin().getMessagesSender().sendTranslatedMessage(getPlayerCommandMessageID(), sender);
					return false;
				}
				guiID = args[0];
				if (!sender.hasPermission("drguis.show.myself." + guiID)) {
					getPlugin().getMessagesSender().sendTranslatedMessage(getNoPermissionMessageID(), sender,
							MapsUtils.mapOf("permission", "drguis.show.myself." + guiID));
					return false;
				}
				return showGUIToPlayer(guiID, (Player) sender, sender);
			default:
				getPlugin().getMessagesSender().sendTranslatedMessage(getInvalidCommandMessageID(), sender);
				return false;
			}
		}
		return true;
	}

	private boolean showGUIToPlayer(String guiID, Player showPlayer, CommandSender initiativeSender) {
		GUIEditor editor = GUIsEditingManager.getInstance().getGUIEditor(guiID);
		if (editor == null) {
			getPlugin().getMessagesSender().sendTranslatedMessage("error_gui_id_does_not_exist", initiativeSender,
					MapsUtils.mapOf("id", guiID), null, null);
			return false;
		}
		GUIsAPI.showGUIToPlayer(showPlayer, editor.getGUIModel().getGUI(showPlayer));
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			String prefix = args[0];
			List<String> autocompleteOptions = new ArrayList<>(GUIsEditingManager.getInstance().getGUIEditorsIDs())
					.stream()
					.filter((String id) -> id.startsWith(prefix) && (sender.hasPermission("drguis.show.myself." + id)
							|| sender.hasPermission("drguis.show.other." + id)))
					.collect(Collectors.toList());
			autocompleteOptions.sort(String::compareTo);
			return autocompleteOptions;
		}
		return null;
	}

}
