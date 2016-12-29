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
	        	eventei.setCancelled(false); 
	        } else {
	        // Get the config option for this event
	        if (this.plugin.config.getBoolean("enemyDenyTPINTO")) {
	        // Check to see if the faction claim player is standing on is enemy, then cancel the TP INTO
	        if (mplayer.getFaction().getRelationTo(faction) == Rel.ENEMY) {
	            eventei.setCancelled(true);
	            player.sendMessage("§c§l[MCE] §r§cYou cannot tp in to enemy land...");
	            return;
	        } 
	        } else {
	        	eventei.setCancelled(false);
	        }
	        }
	      
	    }
	  
	  @EventHandler
	    /* Neutral */
	     // This is so players cant tp into Neutral territory
	    private void onPlayerNeutralTPIn(PlayerTeleportEvent eventni) {
	        // The Player
	        Player player = eventni.getPlayer();
	        MPlayer mplayer = MPlayer.get(player);
	        // Making 'faction' usable later in code and so it can be defined.
	        Faction faction; 
	        // Get the current relation of location claim player is headed
	        faction = BoardColl.get().getFactionAt(PS.valueOf(eventni.getTo()));
	        // This gets wild, safe, and war zones defined so we can use it later in the checks!
	        Faction wild = FactionColl.get().getNone();
	        String wildId = wild.getId();
	        Faction war = FactionColl.get().getWarzone();
	        String warId = war.getId();
	        Faction safe = FactionColl.get().getSafezone();
	        String safeId = safe.getId();
	 
	 
	        // Checks if the player is neutral to the faction claims they are in
	        if (mplayer.getFaction().getRelationTo(faction) == Rel.NEUTRAL) {
	 
	            //checks if the faction is wild, safe or war zones, if so it will continue their teleport! Else, it will stop their teleport!
	            if (faction.getId() == warId || faction.getId() == wildId || faction.getId() == safeId) {
	                eventni.setCancelled(false);
	                this.plugin.log.warning("Debug: [NI] 1.) Made it to cancel false point.");
	                return;
	            } else {
	                eventni.setCancelled(true);
	                this.plugin.log.warning("Debug: [NI] 2.) Made it to cancel true point.");
	                player.sendMessage("§c§l[MCE] §r§cYou cannot tp into neutral land.");
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
	        
	        // Checks if the player is neutral to the faction claims they are in
	        if (mplayer.getFaction().getRelationTo(faction) == Rel.TRUCE) {
	                eventti.setCancelled(true);
	                player.sendMessage("§c§l[MCE] §r§cYou cannot tp into truce land.");
	                return;
	            }
	        }
}
