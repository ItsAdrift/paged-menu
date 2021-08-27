package me.itsadrift.mooseclub.menu.paged;

import me.itsadrift.mooseclub.menu.Menu;
import me.itsadrift.mooseclub.menu.MenuManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PagedMenuListener implements Listener {

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        PagedMenu matchedMenu = PagedMenuManager.getInstance().matchMenu(event.getWhoClicked().getUniqueId());

        if (matchedMenu != null) {
            // Menu found.
            matchedMenu.handleClick(event);
        }
    }

    @EventHandler
    public void InventoryClose(InventoryCloseEvent event) {
        PagedMenu matchedMenu = PagedMenuManager.getInstance().matchMenu(event.getPlayer().getUniqueId());

        if (matchedMenu != null) {
            // Menu found.
            matchedMenu.handleClose((Player) event.getPlayer());
        }

        // Unregister menu - it has been closed.
        PagedMenuManager.getInstance().unregisterMenu(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event) {
        PagedMenu matchedMenu = PagedMenuManager.getInstance().matchMenu(event.getPlayer().getUniqueId());

        if (matchedMenu != null) {
            // Menu found.
            matchedMenu.handleClose(event.getPlayer());
        }

        // Unregister menu - the player has quit.
        PagedMenuManager.getInstance().unregisterMenu(event.getPlayer().getUniqueId());
    }

}
