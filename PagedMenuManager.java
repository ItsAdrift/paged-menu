package me.itsadrift.mooseclub.menu.paged;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PagedMenuManager {

    private static PagedMenuManager instance;

    private final Map<UUID, PagedMenu> openMenus;

    /**
     * Class constructor
     */
    public PagedMenuManager() {
        this.openMenus = new HashMap<>();
    }

    public static PagedMenuManager getInstance() {
        if (instance == null) {
            instance = new PagedMenuManager();
        }
        return instance;
    }

    /**
     * Register a menu to a user.
     * @param toRegister The user.
     * @param menu The menu.
     */
    public void registerMenu(UUID toRegister, PagedMenu menu) {
        openMenus.put(toRegister, menu);
    }

    /**
     * Unregister a menu.
     * @param toUnRegister The user's menu to unregister.
     */
    public void unregisterMenu(UUID toUnRegister) {
        openMenus.remove(toUnRegister);
    }

    /**
     * Find a menu.
     * @param user The user to search for.
     * @return The Menu found, or null if it does not exist.
     */
    public PagedMenu matchMenu(UUID user) { return openMenus.get(user); }

}
