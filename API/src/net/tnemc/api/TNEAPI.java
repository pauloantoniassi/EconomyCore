package net.tnemc.api;
/*
 * The New Economy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

import net.tnemc.core.account.Account;
import net.tnemc.core.account.NonPlayerAccount;
import net.tnemc.core.account.PlayerAccount;
import org.jetbrains.annotations.NotNull;

import java.util.Currency;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * The core TNE API class.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface TNEAPI {

  //Our Core API methods. Mainly account-related methods

  /**
   * Used to determine if an {@link Account} exists with the specified identifier.
   *
   * This method is safe to search for non-player accounts.
   *
   * @param identifier The string identifier for the account that is being looked for.
   *
   * @return True if an account with the specified identifier exists, otherwise false.
   *
   * @since 1.0.0
   */
  boolean hasAccount(@NotNull String identifier);

  /**
   * Used to determine if an {@link PlayerAccount} exists with the specified identifier.
   *
   * This method is not safe to search for non-player accounts.
   *
   * @param identifier The {@link UUID identifier} for the account that is being looked for.
   *
   * @return True if an account with the specified identifier exists, otherwise false.
   *
   * @since 1.0.0
   */
  boolean hasPlayerAccount(@NotNull UUID identifier);

  /**
   * Looks for an account based on the provided identifier and if none is found, creates it.
   *
   * This method could return any of the following account types:
   * - {@link NonPlayerAccount}
   * - {@link net.tnemc.core.account.SharedAccount}
   *
   * This method is safe to search for non-player accounts.
   *
   * @param identifier The string identifier for the account that is being looked for.
   *
   * @return The correlating {@link Account account} object if found, otherwise the one created.
   *
   * @since 1.0.0
   */
  Account getOrCreateAccount(@NotNull String identifier);

  /**
   * Looks for an account based on the provided identifier and if none is found, creates it.
   *
   * This method returns an {@link PlayerAccount}.
   *
   * This method is not safe to search for non-player accounts.
   *
   * @param identifier The {@link UUID} identifier for the account that is being looked for.
   *
   * @return The correlating {@link PlayerAccount account} object if found, otherwise the one created.
   *
   * @since 1.0.0
   */
  PlayerAccount getOrCreatePlayerAccount(@NotNull UUID identifier, @NotNull String name);

  /**
   * Attempts to create an account with the given identifier. This method returns true if the account
   * was created, otherwise false.
   *
   * This method is intended for non-player accounts.
   *
   * @param identifier The String identifier for the account that is being created.
   *
   * @return True if the account was created. If the account was not created this returns false.
   *
   * @since 1.0.0
   */
  boolean createAccount(@NotNull String identifier);

  /**
   * Attempts to create an account with the given identifier. This method returns true if the account
   * was created, otherwise false.
   *
   * This method is not intended for non-player accounts.
   *
   * @param identifier The {@link UUID} identifier for the account that is being created.
   * @param name The String representation of the name for the account being created, usually the username
   *             of the player.
   *
   * @return True if the account was created. If the account was not created this returns false.
   *
   * @since 1.0.0
   */
  boolean createPlayerAccount(@NotNull UUID identifier, @NotNull String name);

  /**
   * Looks for an account based on the provided identifier.
   *
   * This method could return any of the following account types:
   * - {@link PlayerAccount}
   * - {@link NonPlayerAccount}
   * - {@link net.tnemc.core.account.SharedAccount}
   *
   * This method is safe to search for non-player accounts.
   *
   * @param identifier The string identifier for the account that is being looked for.
   * @return An optional containing the {@link Account} if found, otherwise an empty optional.
   *
   * @since 1.0.0
   */
  Optional<Account> getAccount(@NotNull String identifier);

  /**
   * Looks for an {@link PlayerAccount} based on the provided {@link UUID identifier}.
   *
   * This method is not safe to search for non-player accounts.
   *
   * @param identifier The {@link UUID identifier} for the account that is being looked for.
   * @return An optional containing the {@link PlayerAccount}
   * if found, otherwise an empty optional.
   *
   * @since 1.0.0
   */
  Optional<PlayerAccount> getPlayerAccount(@NotNull UUID identifier);

  /*/**
   * Used to delete the specified account.
   *
   * This method is safe to search for non-player accounts.
   *
   * @param identifier The identifier associated with the account that you wish to delete.
   * @param source The {@link ActionSource source} response for this deletion call.
   *
   * @return The {@link EconomyResponse response} that should be returned based on the deletion action.
   *
   * @since 1.0.0
   */
  //EconomyResponse deleteAccount(@NotNull String identifier, @NotNull ActionSource source);

  /*/**
   * Used to delete the specified account.
   *
   * This method is not safe to search for non-player accounts.
   *
   * @param identifier The identifier associated with the account that you wish to delete.
   * @param source The {@link ActionSource source} response for this deletion call.
   *
   * @return The {@link EconomyResponse response} that should be returned based on the deletion action.
   *
   * @since 1.0.0
   **/
  //EconomyResponse deleteAccount(@NotNull UUID identifier, @NotNull ActionSource source);*/

  /**
   * Used to get the default currency. This could be the default currency for the server globally or
   * for the default world if the implementation supports multi-world.
   * @return The currency that is the default for the server if multi-world support is not available
   * otherwise the default for the default world.
   *
   * @since 1.0.0
   */
  @NotNull
  Currency getDefaultCurrency();

  /**
   * Used to get the default currency for the specified world if this implementation has multi-world
   * support, otherwise the default currency for the server.
   * @param world The world to get the default currency for.
   * @return The default currency for the specified world if this implementation has multi-world
   * support, otherwise the default currency for the server.
   *
   * @since 1.0.0
   */
  @NotNull
  Currency getDefaultCurrency(@NotNull String world);

  /**
   * Used to get a set of every  {@link Currency} object for the server.
   * @return A set of every {@link Currency} object that is available for the server.
   *
   * @since 1.0.0
   */
  Set<Currency> getCurrencies();

  /**
   * Used to get a set of every {@link Currency} object that is available in the specified world if
   * this implementation has multi-world support, otherwise all {@link Currency} objects for the server.
   * @param world The world we want to get the {@link Currency} objects for.
   * @return A set of every {@link Currency} object that is available in the specified world if
   * this implementation has multi-world support, otherwise all {@link Currency} objects for the server.
   *
   * @since 1.0.0
   */
  Set<Currency> getCurrencies(@NotNull String world);

  //Holdings-related API methods

  //BalTop-related API Methods
}