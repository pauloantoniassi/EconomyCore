package net.tnemc.paper;
/*
 * The New Economy
 * Copyright (C) 2022 - 2023 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import net.tnemc.menu.bukkit.listener.BukkitChatListener;
import net.tnemc.menu.bukkit.listener.BukkitInventoryCloseListener;
import net.tnemc.paper.hook.economy.VaultHook;
import net.tnemc.paper.hook.misc.PAPIHook;
import net.tnemc.paper.listeners.player.PlayerClickListener;
import net.tnemc.paper.listeners.player.PlayerCloseInventoryListener;
import net.tnemc.paper.listeners.player.PlayerInteractListener;
import net.tnemc.paper.listeners.player.PlayerJoinListener;
import net.tnemc.paper.listeners.player.PlayerQuitListener;
import net.tnemc.paper.listeners.world.WorldLoadListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * TNE
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class TNE extends JavaPlugin {

  private static TNE instance;
  private PaperCore core;

  @Override
  public void onLoad() {
    instance = this;

    //Initialize our TNE Core Class
    this.core = new PaperCore(instance);

    //Vault
    try {
      Class.forName("net.milkbowl.vault.economy.Economy");
      new VaultHook().register();
    } catch(Exception ignore) {

    }
  }

  @Override
  public void onEnable() {

    this.core.enable();

    //Register our event listeners

    //Player Listeners
    Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);

    //Menu-related Listeners from TNML
    Bukkit.getPluginManager().registerEvents(new BukkitChatListener(this), this);
    Bukkit.getPluginManager().registerEvents(new PlayerClickListener(this), this);
    Bukkit.getPluginManager().registerEvents(new BukkitInventoryCloseListener(this), this);
    Bukkit.getPluginManager().registerEvents(new PlayerCloseInventoryListener(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);

    //World Listeners
    Bukkit.getPluginManager().registerEvents(new WorldLoadListener(), this);

    //Register our service providers.

    //PAPI
    if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      new PAPIHook().register();
    }

    final Metrics metrics = new Metrics(this, 602);

    getLogger().log(Level.INFO, "The New Economy has been enabled!");

  }

  @Override
  public void onDisable() {

    this.core.onDisable();
  }

  public static TNE instance() {
    return instance;
  }
}