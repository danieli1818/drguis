package drguis.commands;

import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.common.icons.types.SimpleIcon;
import drguis.messages.MessagesIDs;
import drguis.models.types.editors.GUIEditor;
import drguis.models.types.editors.SimpleGUIEditor;
import drguis.views.GUIView;
import drlibs.common.commands.RootCommand;
import drlibs.common.files.FileConfigurationsUtils;
import drlibs.common.plugin.MessagesPlugin;
import drlibs.utils.functions.ItemsUtils;

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
			
			FileConfigurationsUtils fcu = getPlugin().getFileConfigurationsUtils();
			fcu.loadFileConfiguration("output.yml");
			if (fcu.hasKey("output.yml", "ItemStack")) {
				try {
					System.out.println(ItemsUtils.fromString(fcu.getString("output.yml", "ItemStack")));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				fcu.setObject("output.yml", "ItemStack", ItemsUtils.toString(new ItemStack(Material.APPLE)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (fcu.hasKey("output.yml", "SimpleIcon")) {
				try {
					System.out.println(fcu.getSerializable("output.yml", "SimpleIcon"));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			try {
				fcu.setSerializable("output.yml", "SimpleIcon", new SimpleIcon(new ItemStack(Material.APPLE), true));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (fcu.hasKey("output.yml", "gui")) {
				try {
					System.out.println(fcu.getSerializable("output.yml", "gui"));
					GUIsAPI.showGUIToPlayer(player, ((GUIEditor) fcu.getSerializable("output.yml", "gui")).getGUI(player));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			try {
//				fcu.setSerializable("output.yml", "gui", editor);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			if (fcu.hasKey("output.yml", "gui1")) {
				try {
					System.out.println(fcu.getSerializable("output.yml", "gui1"));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			try {
				fcu.setSerializable("output.yml", "gui1", editor);
			} catch (IOException e) {
				e.printStackTrace();
			}
			fcu.save("output.yml");
			
		}
		return true;
	}

}
