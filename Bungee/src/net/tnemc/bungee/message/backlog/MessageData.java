package net.tnemc.bungee.message.backlog;
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

import java.util.HashMap;
import java.util.Map;

/**
 * MessageData
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class MessageData {

  private final Map<Long, BacklogEntry> backlog = new HashMap<>();

  private final String serverName;

  public MessageData(String serverName) {
    this.serverName = serverName;
  }

  public String getServerName() {
    return serverName;
  }

  public Map<Long, BacklogEntry> getBacklog() {
    return backlog;
  }
}