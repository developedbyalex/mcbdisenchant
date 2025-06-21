package com.mcbdisenchant.listeners;

import com.mcbdisenchant.gui.DisenchantGUI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.Map;

import static com.mcbdisenchant.MCBDisenchant.getInstance;

public class DisenchantGUIListener implements Listener {

    // Store active GUIs
    private static final Map<Inventory, DisenchantGUI> activeGUIs = new HashMap<>();

    public static void addActiveGUI(DisenchantGUI gui) {
        activeGUIs.put(gui.getInventory(), gui);
    }

    public static void removeActiveGUI(Inventory inventory) {
        activeGUIs.remove(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;

        DisenchantGUI gui = activeGUIs.get(event.getView().getTopInventory());
        if (gui == null) return;

        // Cancel the event to prevent item movement
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        // Check if clicked item is an enchanted book
        if (clickedItem.getType() == Material.ENCHANTED_BOOK) {
            handleEnchantedBookClick(player, clickedItem, gui);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        removeActiveGUI(event.getInventory());
    }

    private void handleEnchantedBookClick(Player player, ItemStack book, DisenchantGUI gui) {
        if (!(book.getItemMeta() instanceof EnchantmentStorageMeta meta)) return;

        // Get the enchantment from the book
        Map<Enchantment, Integer> storedEnchants = meta.getStoredEnchants();
        if (storedEnchants.isEmpty()) return;

        // Get the first (and should be only) enchantment
        Enchantment enchantmentToRemove = storedEnchants.keySet().iterator().next();
        
        // Get the original item from player's main hand
        ItemStack originalItem = player.getInventory().getItemInMainHand();
        if (originalItem.getType() == Material.AIR) {
            player.closeInventory();
            return;
        }

        // Check if the item actually has this enchantment
        if (!originalItem.getEnchantments().containsKey(enchantmentToRemove)) {
            player.closeInventory();
            return;
        }

        // Remove the enchantment
        originalItem.removeEnchantment(enchantmentToRemove);

        // Close the inventory
        player.closeInventory();

        // Send success message
        String enchantName = enchantmentToRemove.getKey().getKey().replace("_", " ");
        enchantName = capitalizeWords(enchantName);
        
        String message = getInstance().getConfig().getString("messages.enchantment_removed", 
            "<green>Successfully removed <white>{enchantment}</white> from your item!</green>");
        message = message.replace("{enchantment}", enchantName);
        
        player.sendMessage(MiniMessage.miniMessage().deserialize(message));

        // Play success sound
        String successSound = getInstance().getConfig().getString("sounds.enchantment_removed", "ENTITY_EXPERIENCE_ORB_PICKUP");
        try {
            player.playSound(player.getLocation(), Sound.valueOf(successSound), 1.0f, 1.0f);
        } catch (IllegalArgumentException ignored) {}
    }

    private String capitalizeWords(String str) {
        String[] words = str.split(" ");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            if (i > 0) result.append(" ");
            if (words[i].length() > 0) {
                result.append(Character.toUpperCase(words[i].charAt(0)));
                if (words[i].length() > 1) {
                    result.append(words[i].substring(1).toLowerCase());
                }
            }
        }
        
        return result.toString();
    }
} 