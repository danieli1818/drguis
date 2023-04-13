package drguis.commands.subcommands;

import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import drguis.api.GUIsAPI;
import drguis.management.GUIsEditingManager;
import drguis.models.types.editors.SimpleGUIEditor;
import drlibs.common.commands.AdvancedCommand;
import drlibs.common.commands.SubCommand;
import drlibs.common.commands.helpers.arguments.ArgumentsFlagsHelper;
import drlibs.common.commands.helpers.arguments.flags.ValueFlagData;
import drlibs.common.plugin.MessagesPlugin;
import drlibs.utils.functions.MapsUtils;
import drlibs.utils.functions.ParsingUtils;

public class CreateSubCommand extends SubCommand {

	private ArgumentsFlagsHelper argumentsFlagsHelper;

	public CreateSubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String command) {
		super(plugin, fatherCommand, command, "Create a new GUI", "drguis.create");
		this.argumentsFlagsHelper = new ArgumentsFlagsHelper(getCommandPrefix())
				.registerFlag("rows", new ValueFlagData("Number of rows of the GUI defaults to 4", "4").addFlagValues("1", "2", "3", "4", "5"))
				.registerFlag("slots", new ValueFlagData("Number of slots of the GUI defaults to 36", null).addFlagValues("9", "18", "27", "36", "45"))
				.registerFlag("type", new ValueFlagData("The type of the GUI defaults to simple", "simple").addFlagValue("simple"));
	}

	// TODO Change this function to use the argumentsFlagsHelper
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!super.onCommand(sender, command, label, args)) {
			args = ParsingUtils.splitArgs(String.join(" ", args));
			if (args.length < 2) {
				getPlugin().getMessagesSender().sendTranslatedMessage(getInvalidCommandMessageID(), sender,
						MapsUtils.mapOf("syntax", "/" + getCommandPrefix() + " <GUI ID> <GUI Title>"), null, null);
				return false;
			}
			if (!(sender instanceof Player)) {
				getPlugin().getMessagesSender().sendTranslatedMessage(getPlayerCommandMessageID(), sender);
			}
			Player player = (Player) sender;
			String guiID = args[0];
			if (GUIsEditingManager.getInstance().hasGUIEditor(guiID)) {
				getPlugin().getMessagesSender().sendTranslatedMessage("error_gui_id_exists", sender, MapsUtils.mapOf("id", guiID), null, null);
				return false;
			}
			String title = args[1];
			int rows = -1;
			Integer slots = null;
			String type = null;
			Map<String, Object> flagsValues = argumentsFlagsHelper.parseArgsOrDefault(args);
			try {
				rows = Integer.valueOf((String) flagsValues.get("rows"));
				Object slotsObject = flagsValues.get("slots");
				if (slotsObject != null) {
					slots = Integer.valueOf((String) slotsObject);
				}
			} catch (NumberFormatException exception) {
				getPlugin().getMessagesSender().sendTranslatedMessage("error_arguments_must_be_an_integer", sender,
						MapsUtils.mapOf("args", "rows and slots"), null, null);
				return false;
			}
			type = (String) flagsValues.get("type");
			if (rows <= 0) {
				getPlugin().getMessagesSender().sendTranslatedMessage("error_argument_must_be_bigger_than_value",
						sender, MapsUtils.mapOf("arg", "row", "value", "0"), null, null);
				return false;
			}
			if (slots == null) {
				slots = rows * 9;
			}
			if (slots <= 0) {
				getPlugin().getMessagesSender().sendTranslatedMessage("error_argument_must_be_bigger_than_value",
						sender, MapsUtils.mapOf("arg", "slots", "value", "0"), null, null);
				return false;
			}
			switch (type) {
			case "simple":
				GUIsEditingManager.getInstance().registerEditor(guiID, new SimpleGUIEditor(slots, title));
				break;
			default:
				getPlugin().getMessagesSender().sendTranslatedMessage("error_gui_type_doesn't_exist", sender,
						MapsUtils.mapOf("type", type), null, null);
				return false;
			}
			GUIsAPI.showGUIToPlayer(player, GUIsEditingManager.getInstance().getGUIEditor(guiID).getGUI(player));
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return argumentsFlagsHelper.getAutocompleteOptions(args);
	}

}
