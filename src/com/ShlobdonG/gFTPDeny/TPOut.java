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
	
	private gFTPDenyMain plugin;
	  public TPOut(gFTPDenyMain inst)
	  {
	    this.plugin = inst;
	  }
	  /* Enemy */
	    // This one is to stop players from TPing out of enemy territory.
	  @EventHandler
	    private void onPlayerEnemyTPOut(PlayerTeleportEvent eventeo) {
	        Player player = eventeo.getPlayer();
	        MPlayer mplayer = MPlayer.get(player);
	        Faction factionout;
	        factionout = BoardColl.get().getFactionAt(PS.valueOf(eventeo.getTo()));
	        if (mplayer.getFaction().getRelationTo(factionout) == Rel.ENEMY) {
	        	eventeo.setCancelled(false); 
	        } else {
	        // Get the config option for this event
	        if (this.plugin.config.getBoolean("enemyDenyTPOUTOF")) {
	        // Check to see if the faction claim player is standing on is enemy, then cancel the TP out of
	        if (mplayer.isInEnemyTerritory()) {
	            eventeo.setCancelled(true);
	            player.sendMessage("§c§l[MCE] §r§cYou cannot tp out of enemy land.");
	            return;
	        } else {
	        	eventeo.setCancelled(false);
	        }
	        }
	        }
	    }
	  
	  @EventHandler
	   /* Neutral */
	    // This is so that players cant tp out of Neutral territory
	    private void onPlayerNeutralTPOut(PlayerTeleportEvent eventno) {
	        // The Player
	        Player playerno = eventno.getPlayer();
	        MPlayer mplayerno = MPlayer.get(playerno);
	        // Making 'faction' usable later in code and so it can be defined.
	        Faction factionno; 
	        // Get the current relation of location claim player is at
	        factionno = BoardColl.get().getFactionAt(PS.valueOf(eventno.getFrom()));
	        // This gets wild, safe, and war zones defined so we can use it later in the checks!
	        Faction wildno = FactionColl.get().getNone();
	        String wildIdno = wildno.getId();
	        Faction warno = FactionColl.get().getWarzone();
	        String warIdno = warno.getId();
	        Faction safeno = FactionColl.get().getSafezone();
	        String safeIdno = safeno.getId();
	 
	 
	        // Checks if the player is neutral to the faction claims they are in 
	        if (mplayerno.getFaction().getRelationTo(factionno) == Rel.NEUTRAL) {
	 
	            // Checks if the faction is wild, safe or war zones, if so it will continue their teleport! Else, it will stop their teleport!
	            if (factionno.getId() == warIdno || factionno.getId() == wildIdno || factionno.getId() == safeIdno) {
	                eventno.setCancelled(false);
	                this.plugin.log.warning("Debug: [NO] 1.) Made it to cancel false point.");
	                return;
	            } else {
	                eventno.setCancelled(true);
	                this.plugin.log.warning("Debug: [NO] 2.) Made it to cancel true point.");
	                playerno.sendMessage("§c§l[MCE] §r§cYou cannot tp out of neutral land.");
	            }
	        } 
	    } 
	    
	  @EventHandler
		   /* Truce */
	    // This is so that players cant tp out of Truce territory
	    private void onPlayerTruceTPOut(PlayerTeleportEvent eventio) {
	        // The Player
	        Player player = eventio.getPlayer();
	        MPlayer mplayer = MPlayer.get(player);
	        // Making 'faction' usable later in code and so it can be defined.
	        Faction faction; 
	        // Get the current relation of location claim player is at
	        faction = BoardColl.get().getFactionAt(PS.valueOf(eventio.getFrom()));
	 
	        // Checks if the player is truce to the faction claims they are in 
	        if (mplayer.getFaction().getRelationTo(faction) == Rel.TRUCE) {
	                eventio.setCancelled(true);
	                player.sendMessage("§c§l[MCE] §r§cYou cannot tp out of truce land.");
	                return;
	            }
	        }    	
}
