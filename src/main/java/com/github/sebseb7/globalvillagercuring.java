package com.github.sebseb7;

import com.github.sebseb7.events.GossipHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class globalvillagercuring extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new GossipHandler(), this);
        getServer().getConsoleSender().sendMessage("[GlobalVillagerCuring] Global Villager Curing Enabled");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("[GlobalVillagerCuring] Global Villager Curing Disabled");
    }
}
