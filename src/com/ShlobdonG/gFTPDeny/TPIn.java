package com.ShlobdonG.gFTPDeny;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.ShlobdonG.gFTPDeny.gFTPDenyMain;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;

public class TPIn implements Listener {

    // Allowing code in the main class be called.
    private gFTPDenyMain plugin;
    public TPIn(gFTPDenyMain inst) {
        this.plugin = inst;
    }
    public static String andcolor(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    //Enemy
    @EventHandler
    private void onPlayerTPIn(PlayerTeleportEvent eventin) {

        //Variables 		
        // Get the player
        Player player = eventin.getPlayer();
        // Get the faction player
        MPlayer mplayer = MPlayer.get(player);

        // Setting up the faction variable for use later
        Faction factionto;
        // Get the faction for location TPing into
        factionto = BoardColl.get().getFactionAt(PS.valueOf(eventin.getTo()));
        boolean eTo = mplayer.getFaction().getRelationTo(factionto) == Rel.ENEMY;
        boolean nTo = mplayer.getFaction().getRelationTo(factionto) == Rel.NEUTRAL;
        boolean tTo = mplayer.getFaction().getRelationTo(factionto) == Rel.TRUCE;

        // This gets wild, safe, and war zones defined so we can use it later in the checks!
        Faction wild = FactionColl.get().getNone();
        String wildId = wild.getId();
        Faction war = FactionColl.get().getWarzone();
        String warId = war.getId();
        Faction safe = FactionColl.get().getSafezone();
        String safeId = safe.getId();
        if (this.plugin.config.getBoolean("enemyDenyTPINTO") == true) {
            if (eTo == true && (!(factionto.getId() == warId || factionto.getId() == wildId || factionto.getId() == safeId))) {
                eventin.setCancelled(true);
                player.sendMessage(andcolor(plugin.getConfig().getString("enemyDenyTPINTOMessage")));
                if (this.plugin.config.getBoolean("enemyDenyTPINTO") == false) {
                    return;
                }
                return;
            } else {
                if (this.plugin.config.getBoolean("neutralDenyTPINTO") == true) {
                    if (nTo == true && (!(factionto.getId() == warId || factionto.getId() == wildId || factionto.getId() == safeId))) {
                        eventin.setCancelled(true);
                        player.sendMessage(andcolor(plugin.getConfig().getString("neutralDenyTPINTOMessage")));
                        if (this.plugin.config.getBoolean("neutralDenyTPINTO") == false) {
                            return;
                        }
                    } else {
                        if (this.plugin.config.getBoolean("truceDenyTPINTO") == true) {
                            if (tTo == true && (!(factionto.getId() == warId || factionto.getId() == wildId || factionto.getId() == safeId))) {
                                eventin.setCancelled(true);
                                player.sendMessage(andcolor(plugin.getConfig().getString("truceDenyTPINTOMessage")));
                                if (this.plugin.config.getBoolean("truceDenyTPINTO") == false) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}