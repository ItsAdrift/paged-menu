package me.itsadrift.mooseclub.menu.paged;

import me.itsadrift.mooseclub.menu.MenuButton;
import me.itsadrift.mooseclub.utils.HexUtils;
import me.itsadrift.mooseclub.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Map;

public class MenuPage {

    private Inventory inventory;
    private int page;

    private HashMap<Integer, MenuButton> itemMap;

    public MenuPage(int page, String title, HashMap<Integer, MenuButton> items) {
        this.page = page;

        this.itemMap = (HashMap<Integer, MenuButton>) items.clone();

        inventory = Bukkit.createInventory(null, 54, HexUtils.colour(title));
        for (Map.Entry<Integer, MenuButton> e : itemMap.entrySet()) {
            inventory.setItem(e.getKey(), e.getValue().getItemStack());
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getPage() {
        return page;
    }

    public HashMap<Integer, MenuButton> getItemMap() {
        return itemMap;
    }
}
