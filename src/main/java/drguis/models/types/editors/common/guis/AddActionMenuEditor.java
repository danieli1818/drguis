package drguis.models.types.editors.common.guis;

import org.bukkit.entity.Player;

import drguis.common.CloseReason;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.common.icons.types.ActionsIcon;
import drguis.common.icons.utils.IconMetadata;
import drguis.common.regions.ListRegion;
import drguis.management.ActionsManager;
import drguis.management.GUIsStack;
import drguis.models.GUIModel;
import drguis.models.types.editors.common.icons.PrevGUIIcon;
import drguis.models.types.list.IconsListGUIModel;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.utils.GUIsUtils;
import drguis.views.GUIView;
import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;

public class AddActionMenuEditor extends IconsListGUIModel implements GUIModel {

	private ActionsIcon icon;

	public AddActionMenuEditor(ActionsIcon icon) {
		super(new ListRegion(10, 12, 14, 16, 28, 30, 32, 34), "Add Action Editor", 45,
				new IconMetadata(getDefaultPrevItemStack(), 37), new IconMetadata(getDefaultNextItemStack(), 44));
		this.icon = icon;
		addIcons(ActionsManager.getInstance().getIcons(this.icon));
	}

	@Override
	public GUIView getGUIPage(Player player, int pageIndex) {
		GUIView guiView = super.getGUIPage(player, pageIndex);
		guiView.setIcon(4, icon);
		if (GUIsStack.getInstance().hasGUIView(player.getUniqueId())) {
			guiView.setIcon(36, new PrevGUIIcon());
		}
		return guiView;
	}
	
	@Override
	public GUIView getGUI(Player player) {
		return getGUIPage(player, 0);
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
