package net.tnemc.core.io.storage;
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

import com.vdurmont.semver4j.Semver;
import org.intellij.lang.annotations.Language;

/**
 * Dialect
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface Dialect {

  @Language("SQL") String accountsTable();

  @Language("SQL") String accountsNonPlayerTable();

  @Language("SQL") String accountsPlayerTable();

  @Language("SQL") String accountMembersTable();

  @Language("SQL") String holdingsTable();

  @Language("SQL") String receiptsTable();

  @Language("SQL") String receiptsHoldingsTable();

  @Language("SQL") String receiptsParticipantsTable();

  @Language("SQL") String receiptsModifiersTable();

  @Language("SQL") String accountPurge(final int days);

  @Language("SQL") String receiptPurge(final int days);

  //player name save
  @Language("SQL") String saveName();

  //load accounts
  @Language("SQL") String loadAccounts();

  //account load
  @Language("SQL") String loadAccount();

  //account save
  @Language("SQL") String saveAccount();

  //non player load
  @Language("SQL") String loadNonPlayer();

  //non player save
  @Language("SQL") String saveNonPlayer();

  //player load
  @Language("SQL") String loadPlayer();

  //player save
  @Language("SQL") String savePlayer();

  //members load
  @Language("SQL") String loadMembers();

  //members save
  @Language("SQL") String saveMembers();

  //holdings load
  @Language("SQL") String loadHoldings();

  //holdings save
  @Language("SQL") String saveHoldings();

  //receipts load
  @Language("SQL") String loadReceipts();

  //receipt save
  @Language("SQL") String saveReceipt();

  //receipts load
  @Language("SQL") String loadReceiptHolding();

  //receipt holding save
  @Language("SQL") String saveReceiptHolding();

  //receipt participants load
  @Language("SQL") String loadParticipants();

  //receipt participant save
  @Language("SQL") String saveParticipant();

  //receipt modifiers load
  @Language("SQL") String loadModifiers();

  //receipt modifier save
  @Language("SQL") String saveModifier();

  /**
   * Provides the specific requirement for this dialect.
   * @return The version requirement for this dialect, or "none" if no requirement is required.
   */
  String requirement();

  /**
   * Used to parse version strings. Some dialects, such as Maria need their versions parsed because they format them in
   * a specific manner.
   * @param version The version to parse.
   * @return The parsed version.
   */
  default String parseVersion(final String version) {
    return version;
  }

  /**
   * Used to check if the provided version meets the requirement we need.
   * @param version The version to check.
   * @return True if the provided version meets our requirement.
   */
  default boolean checkRequirement(final String version) {
    return new Semver(version).isGreaterThanOrEqualTo(requirement());
  }
}