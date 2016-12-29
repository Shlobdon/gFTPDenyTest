package com.ShlobdonG.gFTPDeny;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.ShlobdonG.gFTPDeny.TPIn;
import com.ShlobdonG.gFTPDeny.TPOut;


public class gFTPDenyMain extends JavaPlugin {

	  private TPIn fpListener = new TPIn(this);
	  private TPOut fpListener2 = new TPOut(this);
	  Logger log;
	  FileConfiguration config;
	  File cfile;
	  
   // public gFTPDenyMain plugin;

	public void onEnable()
	  {
	      Bukkit.getPluginManager().registerEvents(this.fpListener, this);
	      Bukkit.getPluginManager().registerEvents(this.fpListener2, this);
	      log = getLogger();
	      config = getConfig();
	      config.options().copyDefaults(true);
	      saveConfig();
	      cfile = new File(getDataFolder(), "config.yml");
	      config.addDefault("enemyDenyTPINTO", true);
	      config.addDefault("enemyDenyTPOUTOF", true);
	      config.addDefault("truceDenyTPINTO", true);
	      config.addDefault("truceDenyTPOUTOF", true);	     
	  }

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	      if (cmd.getName().equalsIgnoreCase("gftpdreload")) {
	    	  	  reloadConfig();
	              sender.sendMessage(ChatColor.RED + "[gFTPDeny] has been reloaded!");
	              return true;
	      }
		return true;
	}
}
