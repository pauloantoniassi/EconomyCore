package net.tnemc.sponge.impl;
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

import net.tnemc.core.compatibility.ProxyProvider;

/**
 * SpongeProxyProvider
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class SpongeProxyProvider implements ProxyProvider {
  /**
   * Used to register an incoming plugin message channel.
   *
   * @param channel The channel to register.
   */
  @Override
  public void registerIncoming(String channel) {

  }

  /**
   * Used to register an outgoing plugin message channel.
   *
   * @param channel The channel to register.
   */
  @Override
  public void registerOutgoing(String channel) {

  }

  /**
   * Used to send a message through a specific plugin message channel.
   *
   * @param channel The channel to send the message through.
   * @param bytes   The byte data to send.
   */
  @Override
  public void send(String channel, byte[] bytes) {

  }
}