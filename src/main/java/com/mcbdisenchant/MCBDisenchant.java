package com.mcbdisenchant;

import com.mcbdisenchant.commands.DisenchantCommand;
import com.mcbdisenchant.listeners.DisenchantGUIListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCBDisenchant extends JavaPlugin {

    private static MCBDisenchant instance;

    @Override
    public void onEnable() {
        instance = this;
        
        // Save default config
        saveDefaultConfig();
        
        // Register command
        getCommand("disenchant").setExecutor(new DisenchantCommand());
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new DisenchantGUIListener(), this);
        
        getLogger().info("MCBDisenchant has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MCBDisenchant has been disabled!");
    }

    public static MCBDisenchant getInstance() {
        return instance;
    }
} 