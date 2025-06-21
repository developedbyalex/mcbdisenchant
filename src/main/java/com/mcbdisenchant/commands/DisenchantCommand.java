package com.mcbdisenchant.commands;

import com.mcbdisenchant.gui.DisenchantGUI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.mcbdisenchant.MCBDisenchant.getInstance;

public class DisenchantCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players!");
            return true;
        }

        // Check permission
        if (!player.hasPermission("mcbdisenchant.use")) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You don't have permission to use this command!</red>"));
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        
        // Check if player has an item in hand
        if (item.getType() == Material.AIR) {
            String message = getInstance().getConfig().getString("messages.no_item_in_hand", "<red>You must hold an item in your main hand to disenchant!</red>");
            player.sendMessage(MiniMessage.miniMessage().deserialize(message));
            
            // Play error sound
            String errorSound = getInstance().getConfig().getString("sounds.error", "ENTITY_VILLAGER_NO");
            try {
                player.playSound(player.getLocation(), Sound.valueOf(errorSound), 1.0f, 1.0f);
            } catch (IllegalArgumentException ignored) {}
            
            return true;
        }

        // Check if item has enchantments
        if (item.getEnchantments().isEmpty()) {
            String message = getInstance().getConfig().getString("messages.no_enchantments", "<yellow>This item has no enchantments to remove!</yellow>");
            player.sendMessage(MiniMessage.miniMessage().deserialize(message));
            
            // Play error sound
            String errorSound = getInstance().getConfig().getString("sounds.error", "ENTITY_VILLAGER_NO");
            try {
                player.playSound(player.getLocation(), Sound.valueOf(errorSound), 1.0f, 1.0f);
            } catch (IllegalArgumentException ignored) {}
            
            return true;
        }

        // Open the disenchant GUI
        new DisenchantGUI(player).open();
        
        // Play GUI open sound
        String openSound = getInstance().getConfig().getString("sounds.gui_open", "BLOCK_CHEST_OPEN");
        try {
            player.playSound(player.getLocation(), Sound.valueOf(openSound), 1.0f, 1.0f);
        } catch (IllegalArgumentException ignored) {}

        return true;
    }
} 