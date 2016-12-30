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

public class TPIn implements Listener {

	private gFTPDenyMain plugin;
	  public TPIn(gFTPDenyMain inst)
	  {
	    this.plugin = inst;
	  }
	
	  /* Enemy */
	    // This one is to stop players TP INTO enemy territory
	  @EventHandler
	    private void onPlayerEnemyTPIn(PlayerTeleportEvent eventei) {
	        Player player = eventei.getPlayer();
	        MPlayer mplayer = MPlayer.get(player);
	        Faction faction;
	        // Get the current relation of location claim player is headed
	        faction = BoardColl.get().getFactionAt(PS.valueOf(eventei.getTo()));
	        Faction factionout;
	        factionout = BoardColl.get().getFactionAt(PS.valueOf(eventei.getFrom()));
	        if (mplayer.getFaction().getRelationTo(factionout) == Rel.ENEMY) {
	        	return;
	        } else {
	        // Get the config option for this event
	        if (this.plugin.config.getBoolean("enemyDenyTPINTO")) {
	        // Check to see if the faction claim player is standing on is enemy, then cancel the TP INTO
	        if (mplayer.getFaction().getRelationTo(faction) == Rel.ENEMY) {
	            eventei.setCancelled(true);
	            player.sendMessage("§c§l[MCE] §r§cYou cannot tp in to enemy land.");
	        } 
	        } else {
	        	return;
	        }
	        }
	      
	    }
	  
	  @EventHandler
	    /* Neutral */
	     // This is so players cant tp into Neutral territory
	    private void onPlayerNeutralTPIn(PlayerTeleportEvent eventni) {
	        // The Player
	        Player playerni = eventni.getPlayer();
	        MPlayer mplayerni = MPlayer.get(playerni);
	        // Making 'faction' usable later in code and so it can be defined.
	        Faction factionni; 
	        // Get the current relation of location claim player is headed
	        factionni = BoardColl.get().getFactionAt(PS.valueOf(eventni.getTo()));
	        // This gets wild, safe, and war zones defined so we can use it later in the checks!
	        Faction wild = FactionColl.get().getNone();
	        String wildId = wild.getId();
	        Faction war = FactionColl.get().getWarzone();
	        String warId = war.getId();
	        Faction safe = FactionColl.get().getSafezone();
	        String safeId = safe.getId();
	        
	        // Defines another version of 'faction' to stop message sending when not needed
	        Faction factionoutni;
	        factionoutni = BoardColl.get().getFactionAt(PS.valueOf(eventni.getFrom()));
	        if (mplayerni.getFaction().getRelationTo(factionoutni) == Rel.NEUTRAL) {
	        	return;
	        } else {
	            // Checks if the faction is wild, safe or war zones, if so it will continue their teleport! Else, it will stop their teleport!
	            if (factionni.getId() == warId || factionni.getId() == wildId || factionni.getId() == safeId) {
	                return;
	            } else {
		        	if (this.plugin.config.getBoolean("neutralDenyTPINTO")) {
	        // Checks if the player is neutral to the faction claims they are in
	        if (mplayerni.getFaction().getRelationTo(factionni) == Rel.NEUTRAL) {
	            //checks if the faction is wild, safe or war zones, if so it will continue their teleport! Else, it will stop their teleport!
	                eventni.setCancelled(true);
	                playerni.sendMessage("§c§l[MCE] §r§cYou cannot tp into neutral land.");
            }
        } else {
        	return;
        }
        	}
	        }
	  }

	    
	  @EventHandler
	    /* Truce */
	     // This is so players cant tp into Truce territory
	    private void onPlayerTruceTPIn(PlayerTeleportEvent eventti) {
	        // The Player
	        Player player = eventti.getPlayer();
	        MPlayer mplayer = MPlayer.get(player);
	        // Making 'faction' usable later in code and so it can be defined.
	        Faction faction; 
	        // Get the current relation of location claim player is headed
	        faction = BoardColl.get().getFactionAt(PS.valueOf(eventti.getTo()));
	        // Making Wild, Safe, & War zones usable later in the code.
	        Faction wildTI = FactionColl.get().getNone();
	        String wildIdTI = wildTI.getId();
	        Faction warTI = FactionColl.get().getWarzone();
	        String warIdTI = warTI.getId();
	        Faction safeTI = FactionColl.get().getSafezone();
	        String safeIdTI = safeTI.getId();
	        
	        //Etc other stuff
	        Faction factionout;
	        factionout = BoardColl.get().getFactionAt(PS.valueOf(eventti.getFrom()));
	        if (mplayer.getFaction().getRelationTo(factionout) == Rel.TRUCE) {
	        	return;
	        } else {
	            if (faction.getId() == warIdTI || faction.getId() == wildIdTI || faction.getId() == safeIdTI) {
	                return;
	            } else {
	        	if (this.plugin.config.getBoolean("truceDenyTPINTO")) {
	        // Checks if the player is neutral to the faction claims they are in
	        if (mplayer.getFaction().getRelationTo(faction) == Rel.TRUCE) {
	                eventti.setCancelled(true);
	                player.sendMessage("§c§l[MCE] §r§cYou cannot tp into truce land.");
	            }
	        } else {
	        	return;
	        }
	        	}
	        }
	  }
}
