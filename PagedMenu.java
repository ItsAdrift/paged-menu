package me.itsadrift.pagedmenu;

import me.itsadrift.pagedmenu.Menu;
import me.itsadrift.pagedmenu.MenuButton;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.function.Consumer;

public class PagedMenu {

    private HashMap<Integer, MenuPage> pages;
    private HashMap<UUID, Integer> users;

    private Consumer<Player> inventoryClosed;
    private Consumer<Player> inventoryOpened;

    public PagedMenu(String title, List<MenuButton> buttons) {
        pages = new HashMap<>();
        users = new HashMap<>();

        // Arrow Menu Buttons
        MenuButton[] arrowMenuButtons = getArrowMenuButtons();

        int page = 0;
        HashMap<Integer, MenuButton> tempItems = new HashMap<>();
        for (int i = 0; i < buttons.size(); i++) {
            if (((i != 0)&& i % 45 == 0) || i+1 == buttons.size()) {
                if (i+1 == buttons.size()) {
                    tempItems.put(i - (45 * page), buttons.get(i));
                }

                // Create current page, increase to next page
                tempItems.put(45, arrowMenuButtons[0]);
                tempItems.put(53, arrowMenuButtons[1]);

                MenuPage menuPage = new MenuPage(page, title, tempItems);
                pages.put(page, menuPage);
                page++;
                tempItems.clear();

                tempItems.put(i - (45 * page), buttons.get(i));

                continue;
            }

            tempItems.put(i - (45 * page), buttons.get(i));
        }
    }

    public void openPage(Player player, int page) {
        MenuPage menuPage = pages.get(0);
        if (pages.containsKey(page)) {
            menuPage = pages.get(page);
        }

        player.openInventory(menuPage.getInventory());

        users.put(player.getUniqueId(), page);
        PagedMenuManager.getInstance().registerMenu(player.getUniqueId(), this);
    }

    /**
     * Sets the value of the inventoryClosed consumer.
     * @param inventoryClosed The consumer to use.
     */
    public void setInventoryClosed(Consumer<Player> inventoryClosed) {
        this.inventoryClosed = inventoryClosed;
    }

    /**
     * Sets the value of the inventoryOpened consumer.
     * @param inventoryOpened The consumer to use.
     */
    public void setInventoryOpened(Consumer<Player> inventoryOpened) {
        this.inventoryOpened = inventoryOpened;
    }

    /**
     * Handles a player closing the inventory. <br>
     * Executes the inventoryClosed consumer if it is not null.
     * @param player The player who has closed the inventory.
     */
    public void handleClose(Player player) {
        if (inventoryClosed != null) {
            inventoryClosed.accept(player);
        }
    }

    /**
     * Handles a player opening the inventory. <br>
     * Executes the inventoryOpen consumer if it is not null.
     * @param player The player who has opened the inventory.
     */
    public void handleOpen(Player player) {
        if (inventoryOpened != null) {
            inventoryOpened.accept(player);
        }
    }

    /**
     * Handles an InventoryClickEvent inside this menu.
     * @param event The InventoryClickEvent.
     */
    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || users.get(event.getWhoClicked().getUniqueId()) == null) {
            return;
        }

        MenuPage page = getPage(users.get(event.getWhoClicked().getUniqueId()));

        Map<Integer, MenuButton> itemMap = page.getItemMap();

        if (itemMap.containsKey(event.getRawSlot())) {
            // Clicked on a valid button
            Consumer<Player> consumer = itemMap.get(event.getRawSlot()).getWhenClicked();

            // Does the button clicked have an action associated with it?
            if (consumer != null) {
                consumer.accept((Player) event.getWhoClicked());
            }
        }
    }

    public MenuPage getPage(int page) {
        return pages.get(page);
    }

    private MenuButton[] getArrowMenuButtons() {
        ItemStack[] arrows = getArrows();
        MenuButton leftArrow = new MenuButton(arrows[0]).setWhenClicked(player -> {
            int currentPage = users.get(player.getUniqueId());
            if (currentPage != 0) {
                openPage(player, currentPage - 1);
            }
        });
        MenuButton rightArrow = new MenuButton(arrows[1]).setWhenClicked(player -> {
            int currentPage = users.get(player.getUniqueId());
            if (currentPage != pages.size()) {
                openPage(player, currentPage + 1);
            }
        });

        return new MenuButton[]{leftArrow, rightArrow};
    }

    private ItemStack[] getArrows() {
        ItemStack skullLeft = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta leftMeta = (SkullMeta) skullLeft.getItemMeta();
        leftMeta.setDisplayName(colour("&c&lGo Back"));
        leftMeta.setOwner("MHF_ArrowLeft");
        skullLeft.setItemMeta(leftMeta);

        ItemStack skullRight = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skullRight.getItemMeta();
        skullMeta.setOwner("MHF_ArrowRight");
        skullMeta.setDisplayName(colour("&a&lNext"));
        skullRight.setItemMeta(skullMeta);

        return new ItemStack[]{skullLeft, skullRight};
    }

    private String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
