package com.github.sebseb7.events;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.github.bananapuncher714.nbteditor.NBTEditor.NBTCompound;

public class GossipHandler implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {

        Entity entity = event.getRightClicked();
        if (!(entity instanceof Villager || entity instanceof ZombieVillager)) return;

        if (!(NBTEditor.contains(entity, "Gossips", 0))) return;

        Player player = event.getPlayer();
    
        String[] gossipTypes = { "major_positive" };
        for (String type : gossipTypes) {
            addPlayerToGossip(player, entity, type);
        }
    }

    private void addPlayerToGossip(Player player, Entity entity, String gossipType) {
        int maxValue = 0;
        int playerIndex = -1;
        int playerValue = 0;

        NBTCompound gossips = NBTEditor.getNBTCompound(entity, "Gossips");
        int size = NBTEditor.getSize(gossips);
        int[] playerId = NBTEditor.getIntArray(player, "UUID");
        
        for (int i = 0; i < size; i++) {
            if (NBTEditor.getString(gossips, i, "Type").equals(gossipType)) {
                int val = NBTEditor.getInt(gossips, i, "Value");
                maxValue = (val > maxValue) ? val : maxValue;

                if (Arrays.equals(playerId, NBTEditor.getIntArray(gossips, i, "Target"))) {
                    playerIndex = i;
                    playerValue = val;
                }
            }

        }
        if (maxValue <= 0) {
			return;
		}
        if (playerValue == maxValue){
			return;
		}; 

        NBTCompound newGossipEntry = NBTEditor.getEmptyNBTCompound();
        newGossipEntry.set(playerId, "Target");
        newGossipEntry.set(gossipType, "Type");
        newGossipEntry.set(maxValue, "Value");

        if (playerIndex >= 0) {
            NBTEditor.set(entity, newGossipEntry, "Gossips", playerIndex);
        } else {
            NBTEditor.set(entity, newGossipEntry, "Gossips", NBTEditor.NEW_ELEMENT);
        }
    }
}

