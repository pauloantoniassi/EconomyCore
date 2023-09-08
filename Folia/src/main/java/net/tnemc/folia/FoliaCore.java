package net.tnemc.folia;

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

import net.tnemc.bukkit.command.AdminCommand;
import net.tnemc.bukkit.command.ModuleCommand;
import net.tnemc.bukkit.command.MoneyCommand;
import net.tnemc.bukkit.command.TransactionCommand;
import net.tnemc.bukkit.depend.towny.TownyHandler;
import net.tnemc.bukkit.impl.BukkitLogProvider;
import net.tnemc.core.TNECore;
import net.tnemc.core.api.callback.TNECallbacks;
import net.tnemc.folia.impl.FoliaServerProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

/**
 * FoliaCore
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class FoliaCore extends TNECore {
  private JavaPlugin plugin;

  public FoliaCore(JavaPlugin plugin) {
    super(new FoliaServerProvider(), new BukkitLogProvider(plugin.getLogger()));
    setInstance(this);
    this.plugin = plugin;
  }

  @Override
  protected void onEnable() {
    this.directory = plugin.getDataFolder();

    super.onEnable();
  }

  @Override
  public void registerCommandHandler() {
    command = BukkitCommandHandler.create(plugin);
  }

  @Override
  public void registerCommands() {

    //Register our commands
    command.register(new AdminCommand());
    command.register(new ModuleCommand());
    command.register(new MoneyCommand());
    command.register(new TransactionCommand());
  }

  @Override
  public void registerCallbacks() {
    this.callbackManager.addConsumer(TNECallbacks.ACCOUNT_TYPES, (callback->{
      if(Bukkit.getPluginManager().isPluginEnabled("Towny")) {
        log().debug("Adding Towny Account Types");
        TownyHandler.addTypes();
      }

      if(Bukkit.getPluginManager().isPluginEnabled("Factions")) {
        log().debug("Adding Factions Account Types");
      }
      return false;
    }));
  }

  public static FoliaCore instance() {
    return (FoliaCore)TNECore.instance();
  }
}