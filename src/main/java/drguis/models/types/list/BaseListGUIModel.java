package drguis.models.types.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.common.CloseReason;
import drguis.common.GUIViewIdentifier;
import drguis.common.Icon;
import drguis.common.actions.ConsumerAction;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.common.icons.properties.SimpleIconProperties;
import drguis.common.icons.types.ActionsIcon;
import drguis.common.icons.utils.IconMetadata;
import drguis.common.identifiers.IntGUIViewIdentifier;
import drguis.management.PlayersGUIsCloseReasonsManager;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.utils.GUIsUtils;
import drguis.views.GUIView;
import drguis.views.types.LavishGUIView;
import drguis.views.types.SparseGUIView;
import drlibs.events.inventory.DragInventoryDragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.NormalDragAndDropInventoryEvent;
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
		guiPage.setIdentifier(new IntGUIViewIdentifier(pageIndex));
		return guiPage;
	}

	protected GUIView getLavishGUIView(Player player, int pageIndex) {
		GUIView guiPage = new LavishGUIView(this, guiPageSize,
				getTitle().replace("{PAGE_NUMBER}", String.valueOf(pageIndex + 1)));
		addPrevNextIcons(player, guiPage, pageIndex);
		guiPage.setIdentifier(new IntGUIViewIdentifier(pageIndex));
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
		if (prevIconMetadata == null || currentPageIndex == 0) {
			return null;
		}
		return new ActionsIcon(prevIconMetadata.getItemStack(), new SimpleIconProperties())
				.addAction(new ConsumerAction((Player actionPlayer) -> {
					PlayersGUIsCloseReasonsManager.getInstance().setCloseReason(player.getUniqueId(), CloseReason.PREV_PAGE);
					GUIsAPI.showGUIToPlayer(actionPlayer, getGUIPage(actionPlayer, currentPageIndex - 1));
				}));
	}

	private Icon getNextIcon(Player player, int currentPageIndex) {
		if (prevIconMetadata == null || currentPageIndex + 1 >= getNumOfPages(player)) {
			return null;
		}
		return new ActionsIcon(nextIconMetadata.getItemStack(), new SimpleIconProperties())
				.addAction(new ConsumerAction((Player actionPlayer) -> {
					PlayersGUIsCloseReasonsManager.getInstance().setCloseReason(player.getUniqueId(), CloseReason.NEXT_PAGE);
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
	public void onNormalDragAndDropEvent(NormalDragAndDropInventoryEvent event, GUIRelation relation) {
	}

	@Override
	public void onDragInventoryDragAndDropEvent(DragInventoryDragAndDropInventoryEvent event, boolean isFromGUI) {
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

	@Override
	public GUIView getUpdatedGUI(Player player, GUIView prevGUIView) {
		int pageNumber = getPageNumberOfGUIView(prevGUIView);
		if (pageNumber < 0) {
			return getGUI(player);
		}
		return getGUIPage(player, pageNumber);
	}

	protected int getPageNumberOfGUIView(GUIView guiView) {
		GUIViewIdentifier identifier = guiView.getIdentifier();
		if (identifier == null) {
			return -1;
		}
		Object id = identifier.getValue("id");
		if (id instanceof Integer) {
			return (int) id;
		}
		return -1;
	}
	
	protected int getPrevPageIconIndex() {
		return prevIconMetadata.getIndex();
	}
	
	protected int getNextPageIconIndex() {
		return nextIconMetadata.getIndex();
	}
	
	protected Icon getGUIViewIcon(Player player, GUIView guiView, int slot) {
		if (slot == getPrevPageIconIndex()) {
			return getPrevIcon(player, getPageNumberOfGUIView(guiView));
		}
		if (slot == getNextPageIconIndex()) {
			return getNextIcon(player, getPageNumberOfGUIView(guiView));
		}
		return null;
	}
	
}
