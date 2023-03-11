package net.tnemc.sponge.impl;

/*
 * The New Economy
 * Copyright (C) 2022 Daniel "creatorfromhell" Vidmar
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

import net.tnemc.core.compatibility.InventoryProvider;
import net.tnemc.menu.sponge7.SpongeInventory;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.plugin.Plugin;

import java.util.UUID;

/**
 * SpongeInventoryProvider
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class SpongeInventoryProvider extends SpongeInventory implements InventoryProvider<Inventory> {

  public SpongeInventoryProvider(UUID id, Plugin plugin) {
    super(id, plugin);
  }

  /**
   * Used to get an inventory object.
   *
   * @param ender True if the ender chest object should be returned, otherwise false.
   *
   * @return The inventory object.
   */
  @Override
  public Inventory getInventory(boolean ender) {
    return null;
  }
}