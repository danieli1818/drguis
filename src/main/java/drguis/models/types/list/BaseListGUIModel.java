package drguis.models.types.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.common.CloseReason;
import drguis.common.events.GUIRelation;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.utils.GUIsUtils;
import drguis.views.GUIView;
import drguis.views.common.Icon;
import drguis.views.common.actions.ConsumerAction;
import drguis.views.common.events.IconClickEvent;
import drguis.views.common.events.PlayerInventoryClickEvent;
import drguis.views.common.icons.properties.SimpleIconProperties;
import drguis.views.common.icons.types.ActionsIcon;
import drguis.views.common.icons.utils.IconMetadata;
import drguis.views.types.LavishGUIView;
import drguis.views.types.SparseGUIView;
import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;
import drlibs.items.ItemStackBuilder;

public abstract class BaseListGUIModel implements ListGUIModel {

	private int guiPageSize;
	private String title;
	private IconMetadata prevIconMetadata;
	private IconMetadata nextIconMetadata;

	public BaseListGUIModel(int guiPageSize, String title) {
		this(guiPageSize, title,
				new IconMetadata(new ItemStack(Material.ARROW),
						guiPageSize % 9 == 0 ? guiPageSize - 9 : guiPageSize - guiPageSize % 9),
				new IconMetadata(new ItemStack(Material.ARROW), guiPageSize - 1));
	}

	public BaseListGUIModel(int guiPageSize, String title, IconMetadata prevIconMetadata,
			IconMetadata nextIconMetadata) {
		this.guiPageSize = guiPageSize;
		this.title = title;
		this.prevIconMetadata = prevIconMetadata;
		this.nextIconMetadata = nextIconMetadata;
	}

	@Override
	public GUIView getGUI(Player player) {
		return getGUIPage(player, 0);
	}

	protected GUIView getSparseGUIView(Player player, int pageIndex) {
		GUIView guiPage = new SparseGUIView(this, guiPageSize,
				getTitle().replace("{PAGE_NUMBER}", String.valueOf(pageIndex + 1)));
		addPrevNextIcons(player, guiPage, pageIndex);
		return guiPage;
	}

	protected GUIView getLavishGUIView(Player player, int pageIndex) {
		GUIView guiPage = new LavishGUIView(this, guiPageSize,
				getTitle().replace("{PAGE_NUMBER}", String.valueOf(pageIndex + 1)));
		addPrevNextIcons(player, guiPage, pageIndex);
		return guiPage;
	}

	protected GUIView getOptimalGUIView(Player player, int pageIndex, int numOfIcons) {
		if (numOfIcons <= guiPageSize / 2) {
			return getSparseGUIView(player, pageIndex);
		}
		return getLavishGUIView(player, pageIndex);
	}

	protected void addPrevNextIcons(Player player, GUIView guiPage, int pageIndex) {
		Icon prevIcon = getPrevIcon(player, pageIndex);
		if (prevIcon != null) {
			guiPage.setIcon(prevIconMetadata.getIndex(), prevIcon);
		}
		Icon nextIcon = getNextIcon(player, pageIndex);
		if (nextIcon != null) {
			guiPage.setIcon(nextIconMetadata.getIndex(), nextIcon);
		}
	}

	private Icon getPrevIcon(Player player, int currentPageIndex) {
		System.out.println("Getting Prev Icon: " + currentPageIndex);
		if (prevIconMetadata == null || currentPageIndex == 0) {
			return null;
		}
		return new ActionsIcon(prevIconMetadata.getItemStack(), new SimpleIconProperties())
				.addAction(new ConsumerAction((Player actionPlayer) -> {
					GUIsAPI.showGUIToPlayer(actionPlayer, getGUIPage(actionPlayer, currentPageIndex - 1));
				}));
	}

	private Icon getNextIcon(Player player, int currentPageIndex) {
		System.out.println("Getting Next Icon: " + currentPageIndex + ", " + getNumOfPages(player));
		if (prevIconMetadata == null || currentPageIndex + 1 >= getNumOfPages(player)) {
			return null;
		}
		return new ActionsIcon(nextIconMetadata.getItemStack(), new SimpleIconProperties())
				.addAction(new ConsumerAction((Player actionPlayer) -> {
					GUIsAPI.showGUIToPlayer(actionPlayer, getGUIPage(actionPlayer, currentPageIndex + 1));
				}));
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
	public void onMoveItemToOtherInventoryEvent(MoveItemToOtherInventoryEvent event, GUIRelation relation) {
	}

	@Override
	public void onSlotSwapEvent(ItemSlotSwapEvent event) {
	}

	@Override
	public void onGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player) {
		GUIsUtils.defaultOnGUICloseEvent(guiView, closeReason, player);
	}

	public String getTitle() {
		return title;
	}

	public int getGuiPageSize() {
		return guiPageSize;
	}

	public static ItemStack getDefaultPrevItemStack() {
		return new ItemStackBuilder(Material.ARROW).setDisplayName("Previous Page")
				.setLoreString("Click to return to the previous page").build();
	}

	public static ItemStack getDefaultNextItemStack() {
		return new ItemStackBuilder(Material.ARROW).setDisplayName("Next Page")
				.setLoreString("Click to go to the next page").build();
	}

}