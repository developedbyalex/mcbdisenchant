package com.mcbdisenchant.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mcbdisenchant.MCBDisenchant.getInstance;

public class DisenchantGUI {
    
    private final Player player;
    private final Inventory inventory;
    private final ItemStack originalItem;
    
    // Slots that should be filled with filler items
    private static final int[] FILLER_SLOTS = {
        0, 1, 2, 3, 4, 5, 6, 7, 8,     // Top row
        9, 10, 11, 17, 18,             // Second row (excluding 12-16)
        20, 26,                        // Third row (excluding 19, 21-25)
        27, 28, 29, 35, 36,            // Fourth row (excluding 30-34)
        37, 38, 39, 40, 41, 42, 43, 44 // Bottom row
    };
    
    // Slot for the original item
    private static final int ORIGINAL_ITEM_SLOT = 19;
    
    // Slots for enchanted books
    private static final int[] BOOK_SLOTS = {12, 13, 14, 15, 16, 21, 22, 23, 24, 25, 30, 31, 32, 33, 34};
    
    public DisenchantGUI(Player player) {
        this.player = player;
        this.originalItem = player.getInventory().getItemInMainHand().clone();
        
        // Create inventory with MiniMessage title
        String title = getInstance().getConfig().getString("gui.title", "<gradient:#ff6b6b:#4ecdc4>Disenchant Item</gradient>");
        Component titleComponent = MiniMessage.miniMessage().deserialize(title);
        this.inventory = Bukkit.createInventory(null, 45, titleComponent);
        
        setupGUI();
    }
    
    private void setupGUI() {
        // Add filler items
        addFillerItems();
        
        // Add the original item in slot 19
        addOriginalItem();
        
        // Add enchanted books for each enchantment
        addEnchantedBooks();
    }
    
    private void addFillerItems() {
        String materialName = getInstance().getConfig().getString("gui.filler.material", "GRAY_STAINED_GLASS_PANE");
        String fillerName = getInstance().getConfig().getString("gui.filler.name", "<gray> </gray>");
        
        Material fillerMaterial;
        try {
            fillerMaterial = Material.valueOf(materialName);
        } catch (IllegalArgumentException e) {
            fillerMaterial = Material.GRAY_STAINED_GLASS_PANE;
        }
        
        ItemStack filler = new ItemStack(fillerMaterial);
        ItemMeta meta = filler.getItemMeta();
        if (meta != null) {
            meta.displayName(MiniMessage.miniMessage().deserialize(fillerName));
            filler.setItemMeta(meta);
        }
        
        for (int slot : FILLER_SLOTS) {
            inventory.setItem(slot, filler);
        }
    }
    
    private void addOriginalItem() {
        ItemStack displayItem = originalItem.clone();
        ItemMeta meta = displayItem.getItemMeta();
        if (meta != null) {
            String itemName = getInstance().getConfig().getString("messages.gui_item_name", "<aqua>Item to Disenchant</aqua>");
            meta.displayName(MiniMessage.miniMessage().deserialize(itemName));
            displayItem.setItemMeta(meta);
        }
        inventory.setItem(ORIGINAL_ITEM_SLOT, displayItem);
    }
    
    private void addEnchantedBooks() {
        Map<Enchantment, Integer> enchantments = originalItem.getEnchantments();
        int bookSlotIndex = 0;
        
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            if (bookSlotIndex >= BOOK_SLOTS.length) break;
            
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();
            
            ItemStack book = createEnchantedBook(enchantment, level);
            inventory.setItem(BOOK_SLOTS[bookSlotIndex], book);
            bookSlotIndex++;
        }
    }
    
    private ItemStack createEnchantedBook(Enchantment enchantment, int level) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        
        if (meta != null) {
            // Add the enchantment to the book
            meta.addStoredEnchant(enchantment, level, true);
            
            // Set display name
            String enchantName = enchantment.getKey().getKey().replace("_", " ");
            enchantName = capitalizeWords(enchantName);
            Component displayName = MiniMessage.miniMessage().deserialize(
                "<gradient:#ffd700:#ffb347>" + enchantName + " " + level + "</gradient>"
            );
            meta.displayName(displayName);
            
            // Set lore
            List<Component> lore = new ArrayList<>();
            List<String> configLore = getInstance().getConfig().getStringList("messages.enchanted_book_lore");
            for (String loreLine : configLore) {
                String processedLore = loreLine.replace("{level}", String.valueOf(level))
                                              .replace("{enchantment}", enchantName);
                lore.add(MiniMessage.miniMessage().deserialize(processedLore));
            }
            meta.lore(lore);
            
            book.setItemMeta(meta);
        }
        
        return book;
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
    
    public void open() {
        com.mcbdisenchant.listeners.DisenchantGUIListener.addActiveGUI(this);
        player.openInventory(inventory);
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public ItemStack getOriginalItem() {
        return originalItem;
    }
} 