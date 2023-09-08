package net.tnemc.core.transaction;

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

import net.tnemc.core.TNECore;
import net.tnemc.core.account.Account;
import net.tnemc.core.account.holdings.HoldingsEntry;
import net.tnemc.core.actions.EconomyResponse;
import net.tnemc.core.compatibility.log.DebugLevel;

import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

/**
 * This is a class that handles the actual processing of a transaction.
 *
 * @see Transaction
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface TransactionProcessor {

  /**
   * Processes a transaction.
   * @param transaction The {@link Transaction transaction} to handle.
   * @return The {@link TransactionResult result} from performing the transaction.
   */
  default TransactionResult process(Transaction transaction) {
    final Optional<EconomyResponse> response = processChecks(transaction);

    if(response.isPresent() && !response.get().success()) {
      return new TransactionResult(false, response.get().response());
    }

    if(transaction.getFrom() != null) {

      final Optional<Account> from = TNECore.eco().account().findAccount(transaction.getFrom().getId());
      if(from.isPresent()) {
        for(HoldingsEntry entry : transaction.getFrom().getEndingBalances()) {
          from.get().setHoldings(entry, entry.getHandler());
        }
      }
    }

    if(transaction.getTo() != null) {

      final Optional<Account> to = TNECore.eco().account().findAccount(transaction.getTo().getId());
      if(to.isPresent()) {
        for(HoldingsEntry entry : transaction.getTo().getEndingBalances()) {
          to.get().setHoldings(entry, entry.getHandler());
        }
      }
    }

    final TransactionResult result = new TransactionResult(true, "");

    final Receipt receipt = new Receipt(UUID.randomUUID(),
                                        new Date().getTime(),
                                        transaction);
    result.setReceipt(receipt);
    return result;
  }

  default Optional<EconomyResponse> processChecks(Transaction transaction) {
    EconomyResponse response = null;
    for(final String str : getChecks()) {

      final Optional<TransactionCheck> check = TNECore.eco().transaction().findCheck(str);
      if(check.isPresent()) {
        response = check.get().process(transaction);

        TNECore.log().debug("Check: " + check.get().identifier() + " Result: " + response.success(), DebugLevel.DEVELOPER);

        if(!response.success())
          break;
      }
    }

    return Optional.ofNullable(response);
  }

  /**
   * Used to get the checks for this processor.
   * @return The checks for this processor.
   */
  LinkedList<String> getChecks();

  /**
   * Used to add {@link TransactionCheck check} to this processor.
   * @param check The check to add.
   */
  void addCheck(final TransactionCheck check);

  /**
   * Used to add {@link TransactionCheck checks} from a group to this processor.
   * @param group The group to add.
   */
  void addGroup(final TransactionCheckGroup group);
}