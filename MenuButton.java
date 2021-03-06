package me.itsadrift.pagedmenu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MenuButton {

    private ItemStack itemStack;
    private Consumer<Player> whenClicked;

    /**
     * Class constructor
     * @param itemStack The ItemStack to use for this button.
     */
    public MenuButton(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /*
     * Returns the value of the whenClicked consumer.
     * @return The whenClicked consumer.
     */
    public Consumer<Player> getWhenClicked() {
        return whenClicked;
    }

    /**
     * Sets the value of the whenClicked consumer.
     * @param whenClicked The consumer to set.
     * @return Returns this object.
     */
    public MenuButton setWhenClicked(Consumer<Player> whenClicked) {
        this.whenClicked = whenClicked;
        return this;
    }

    /**
     * Returns the ItemStack for this button.
     * @return The itemStack supplied in the constructor.
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

}
