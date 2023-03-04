package net.tnemc.bukkit.impl;

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

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.tnemc.bukkit.TNE;
import net.tnemc.core.TNECore;
import net.tnemc.core.compatibility.Location;
import net.tnemc.core.compatibility.PlayerProvider;
import net.tnemc.core.io.message.MessageData;
import net.tnemc.core.io.message.MessageHandler;
import net.tnemc.core.menu.Menu;
import net.tnemc.core.menu.icon.Icon;
import net.tnemc.item.AbstractItemStack;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * BukkitPlayerProvider
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class BukkitPlayerProvider implements PlayerProvider {

  private final OfflinePlayer player;

  public BukkitPlayerProvider(OfflinePlayer player) {
    this.player = player;
  }

  /**
   * Used to get the {@link UUID} of this player.
   *
   * @return The {@link UUID} of this player.
   */
  @Override
  public UUID getUUID() {
    return player.getUniqueId();
  }

  /**
   * Used to get the name of this player.
   *
   * @return The name of this player.
   */
  @Override
  public String getName() {
    return player.getName();
  }

  /**
   * Used to get the location of this player.
   *
   * @return The location of this player.
   */
  @Override
  public Optional<Location> getLocation() {
    if(player.getPlayer() == null) {
      return Optional.empty();
    }

    final org.bukkit.Location locale = player.getPlayer().getLocation();

    return Optional.of(new Location(locale.getWorld().getName(),
                                    locale.getX(), locale.getY(),
                                    locale.getZ()
    ));
  }

  /**
   * Used to get the name of the region this player is in. This could be the world itself, or maybe
   * a third-party related region such as world guard.
   *
   * @param resolve Whether the returned region should be resolved to using the {@link net.tnemc.core.region.RegionProvider}.
   *
   * @return The name of the region.
   */
  @Override
  public String getRegion(final boolean resolve) {
    String world = TNECore.server().defaultWorld();

    if(player.getPlayer() != null) {
      world = player.getPlayer().getWorld().getName();
    }

    if(resolve) {
      return TNECore.eco().region().resolveRegion(world);
    }

    return world;
  }

  /**
   * Used to get the amount of experience this player has.
   *
   * @return The amount of levels this player has.
   */
  @Override
  public int getExp() {
    if(player.getPlayer() == null) {
      return 0;
    }
    return (int)player.getPlayer().getExp();
  }

  /**
   * Used to set the amount of experience this player has.
   *
   * @param exp The amount of experience to set for this player.
   */
  @Override
  public void setExp(int exp) {
    if(player.getPlayer() != null) {
      player.getPlayer().setTotalExperience(exp);
    }
  }

  /**
   * Used to get the amount of experience levels this player has.
   *
   * @return The amount of experience levels this player has.
   */
  @Override
  public int getExpLevel() {
    if(player.getPlayer() == null) {
      return 0;
    }
    return player.getPlayer().getLevel();
  }

  /**
   * Used to set the amount of experience levels this player has.
   *
   * @param level The amount of experience levels to set for this player.
   */
  @Override
  public void setExpLevel(int level) {
    if(player.getPlayer() != null) {
      player.getPlayer().setLevel(level);
    }
  }

  @Override
  public Inventory getInventory(boolean ender) {
    if(player.getPlayer() == null) return null;

    if(ender) {
      return player.getPlayer().getEnderChest();
    }
    return player.getPlayer().getInventory();
  }

  @Override
  public void openInventory(Object inventory) {
    if(inventory instanceof Inventory && player.getPlayer() != null) {
      player.getPlayer().openInventory((Inventory)inventory);
    }
  }

  @Override
  public Inventory build(Menu menu, int page) {
    Inventory inventory = Bukkit.createInventory(null, menu.getSize(), menu.getTitle());

    for(Map.Entry<Integer, Icon> entry : menu.getPages().get(page).getIcons().entrySet()) {

      inventory.setItem(entry.getKey(), (ItemStack)entry.getValue().getItem().locale());
    }

    return inventory;
  }

  /**
   * Used to update the menu the player is in with a new item for a specific slot.
   *
   * @param slot The slot to update.
   * @param item The item to update the specified slot with.
   */
  @Override
  public void updateMenu(int slot, AbstractItemStack<?> item) {

  }

  /**
   * Used to determine if this player has the specified permission node.
   *
   * @param permission The node to check for.
   *
   * @return True if the player has the permission, otherwise false.
   */
  @Override
  public boolean hasPermission(String permission) {
    if(player.getPlayer() == null) {
      return false;
    }
    return player.getPlayer().hasPermission(permission);
  }

  /**
   * Used to send a message to this command source.
   *
   * @param messageData The message data to utilize for this translation.
   */
  @Override
  public void message(final MessageData messageData) {
    if(player.getPlayer() == null) {
      return;
    }

    try(BukkitAudiences provider = BukkitAudiences.create(TNE.instance())) {
      MessageHandler.translate(messageData, player.getUniqueId(), provider.sender(player.getPlayer()));
    }
  }

  public static BukkitPlayerProvider find(final String identifier) {
    try {
      return new BukkitPlayerProvider(Bukkit.getOfflinePlayer(UUID.fromString(identifier)));
    } catch (Exception ignore) {
      return new BukkitPlayerProvider(Bukkit.getOfflinePlayer(identifier));
    }
  }
}
