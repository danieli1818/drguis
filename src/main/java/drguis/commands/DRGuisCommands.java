package drguis.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.messages.MessagesIDs;
import drguis.models.types.SimpleGUIModel;
import drguis.models.types.editors.SimpleGUIEditor;
import drguis.models.types.list.GUIModelsListGUIModel;
import drguis.models.types.list.IconsListGUIModel;
import drguis.views.GUIView;
import drguis.views.common.actions.ConsumerAction;
import drguis.views.common.icons.types.ActionIcon;
import drguis.views.common.icons.types.SimpleIcon;
import drguis.views.types.SparseGUIView;
import drlibs.common.commands.RootCommand;
import drlibs.common.plugin.MessagesPlugin;

public class DRGuisCommands extends RootCommand {

	private static final String DESCRIPTION = "DRGuis Commands";
	private static final String PERMISSION = "drguis.use";
	
	private SimpleGUIEditor editor;
	private boolean isEditor;

	public DRGuisCommands(MessagesPlugin plugin) {
		super(plugin, DESCRIPTION, PERMISSION, MessagesIDs.DRGUIS_INVALID_COMMAND_MESSAGE_ID,
				MessagesIDs.NO_PERMISSION_MESSAGE_ID, MessagesIDs.PLAYER_COMMAND_MESSAGE_ID);
		editor = new SimpleGUIEditor(36, "First GUI Editing");
		isEditor = true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!super.onCommand(sender, command, label, args) && args.length == 0) {
			if (!(sender instanceof Player)) {
				getPlugin().getMessagesSender().sendTranslatedMessage(getPlayerCommandMessageID(), sender);
				return false;
			}
			Player player = (Player) sender;
			
//			IconsListGUIModel guiModel = new IconsListGUIModel(36, "Page {PAGE_NUMBER}");
//			for (int i = 0; i < 100; i++) {
//				final int iFinal = i;
//				guiModel.addIcon(new ActionIcon(new ItemStack(Material.values()[i]), true, new ConsumerAction(
//						(Player currentPlayer) -> currentPlayer.getInventory().addItem(new ItemStack(Material.values()[iFinal])))));
//			}
//			GUIsAPI.showGUIToPlayer(player, guiModel.getGUI(player));
			
//			GUIView guiView1 = new SparseGUIView(36, label);
//			guiView1.setIcon(0,
//					new ActionIcon(new ItemStack(Material.APPLE), true,
//							new ConsumerAction((Player currentPlayer) -> currentPlayer.getInventory()
//									.addItem(new ItemStack(Material.APPLE, 64)))));
//			GUIView guiView2 = new SparseGUIView(36, label);
//			guiView2.setIcon(0,
//					new ActionIcon(new ItemStack(Material.COOKED_BEEF), true,
//							new ConsumerAction((Player currentPlayer) -> currentPlayer.getInventory()
//									.addItem(new ItemStack(Material.COOKED_BEEF, 64)))));
//			GUIsAPI.showGUIToPlayer(player, new GUIModelsListGUIModel(36).addGUIModel(new SimpleGUIModel(guiView1))
//					.addGUIModel(new SimpleGUIModel(guiView2)).getGUI(player));
			
			if (isEditor) {
				GUIView editorGUIView = editor.getGUI(player);
				GUIsAPI.showGUIToPlayer(player, editorGUIView);
			} else {
				GUIsAPI.showGUIToPlayer(player, editor.getGUIModel().getGUI(player));
			}
			isEditor = !isEditor;
			
			
		}
		return true;
	}

}
