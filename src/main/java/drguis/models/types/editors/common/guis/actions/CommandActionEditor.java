package drguis.models.types.editors.common.guis.actions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import drguis.api.GUIsAPI;
import drguis.common.CloseReason;
import drguis.common.actions.AskChatInputAction;
import drguis.common.actions.CommandAction;
import drguis.common.actions.ConsumerAction;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.common.icons.types.ActionIcon;
import drguis.common.icons.types.ActionsIcon;
import drguis.management.GUIsStack;
import drguis.models.GUIModel;
import drguis.models.types.editors.common.icons.PrevGUIIcon;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.utils.GUIsUtils;
import drguis.views.GUIView;
import drguis.views.types.SparseGUIView;
import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;

public class CommandActionEditor implements GUIModel {

	private ActionsIcon icon;
	private String command;

	public CommandActionEditor(ActionsIcon icon) {
		this.icon = icon;
	}

	@Override
	public GUIView getGUI(Player player) {
		GUIView guiView = new SparseGUIView(this, 36, "Command Action Editor"); // TODO Change string to be retrieved
																				// from MessagesStorage
		guiView.setIcon(4, icon);
		guiView.setIcon(13, new ActionIcon(getCommandItemStack(), true,
				// TODO Change string to be retrieved from MessagesStorage
				new AskChatInputAction("Please enter the command:", (Player currentPlayer, String input) -> {
					setCommand(input);
					GUIsAPI.showGUIToPlayer(currentPlayer, getGUI(player));
				})));
		guiView.setIcon(31, new ActionIcon(getFinishItemStack(), true, new ConsumerAction((Player currentPlayer) -> {
			icon.addAction(new CommandAction(command));
		})));
		if (GUIsStack.getInstance().hasGUIView(player.getUniqueId())) {
			guiView.setIcon(28, new PrevGUIIcon());
		}
		return guiView;
	}

	private ItemStack getCommandItemStack() {
		ItemStack commandItemStack = new ItemStack(Material.SIGN);
		ItemMeta commandItemMeta = commandItemStack.getItemMeta();
		commandItemMeta.setDisplayName("Command");
		List<String> commandLore = new ArrayList<>();
		commandLore.add(command);
		commandItemMeta.setLore(commandLore);
		commandItemStack.setItemMeta(commandItemMeta);
		return commandItemStack;
	}
	
	private ItemStack getFinishItemStack() {
		ItemStack finishItemStack = new ItemStack(Material.GREEN_GLAZED_TERRACOTTA);
		ItemMeta finishItemMeta = finishItemStack.getItemMeta();
		finishItemMeta.setDisplayName("Finish");
		List<String> finishLore = new ArrayList<>();
		finishLore.add("Click on me when you finish building the command");
		finishItemMeta.setLore(finishLore);
		finishItemStack.setItemMeta(finishItemMeta);
		return finishItemStack;
	}
	
	private void setCommand(String command) {
		this.command = command;
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {
		IconsFunctionsUtils.defaultOnIconClickEvent(event);
	}

	@Override
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event) {
	}

	@Override
	public void onDragAndDropEvent(DragAndDropInventoryEvent event, GUIRelation relation) {
	}

	@Override
	public void onSlotSwapEvent(ItemSlotSwapEvent event) {
	}

	@Override
	public void onMoveItemToOtherInventoryEvent(MoveItemToOtherInventoryEvent event, GUIRelation relation) {
	}
	
	@Override
	public void onGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player) {
		GUIsUtils.defaultOnGUICloseEvent(guiView, closeReason, player);
	}

}
