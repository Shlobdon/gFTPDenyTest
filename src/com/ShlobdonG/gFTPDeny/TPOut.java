package com.ShlobdonG.gFTPDeny;

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

public class TPOut implements Listener {

    // Allowing code in the main class be called.
    private gFTPDenyMain plugin;
    public TPOut(gFTPDenyMain inst) {
            this.plugin = inst;
        }
        // Enemy
    @EventHandler
    public void onPlayerTPOut(PlayerTeleportEvent eventout) {
        // Variables
        // Get the player
        Player player = eventout.getPlayer();
        // Get the faction player
        MPlayer mplayer = MPlayer.get(player);

        // Setting up the faction variable for later use
        Faction factionout;
        factionout = BoardColl.get().getFactionAt(PS.valueOf(eventout.getFrom()));
        boolean eFrom = mplayer.getFaction().getRelationTo(factionout) == Rel.ENEMY;
        boolean nFrom = mplayer.getFaction().getRelationTo(factionout) == Rel.NEUTRAL;
        boolean tFrom = mplayer.getFaction().getRelationTo(factionout) == Rel.TRUCE;

        // This gets wild, safe, and war zones defined so we can use it later in the checks!
        Faction wild = FactionColl.get().getNone();
        String wildId = wild.getId();
        Faction war = FactionColl.get().getWarzone();
        String warId = war.getId();
        Faction safe = FactionColl.get().getSafezone();
        String safeId = safe.getId();
        if (this.plugin.config.getBoolean("enemyDenyTPOUTOF") == true) {
            if (eFrom == true && (!(factionout.getId() == warId || factionout.getId() == wildId || factionout.getId() == safeId))) {
                eventout.setCancelled(true);
                player.sendMessage(plugin.getConfig().getString("enemyDenyTPOUTOFMessage"));
                if (this.plugin.config.getBoolean("enemyDenyTPOUTOF") == false) {
                    return;
                }
            } else {
                if (this.plugin.config.getBoolean("neutralDenyTPOUTOF") == true) {
                    if (nFrom == true && (!(factionout.getId() == warId || factionout.getId() == wildId || factionout.getId() == safeId))) {
                        eventout.setCancelled(true);
                        player.sendMessage(plugin.getConfig().getString("neutralDenyTPOUTOFMessage"));
                        if (this.plugin.config.getBoolean("neutralDenyTPOUTOF") == false) {
                            return;
                        }
                    } else {
                        if (this.plugin.config.getBoolean("truceDenyTPOUTOF") == true) {
                            if (tFrom == true && (!(factionout.getId() == warId || factionout.getId() == wildId || factionout.getId() == safeId))) {
                                eventout.setCancelled(true);
                                player.sendMessage(plugin.getConfig().getString("truceDenyTPOUTOFMessage"));
                                if (this.plugin.config.getBoolean("truceDenyTPOUTOF") == false) {
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