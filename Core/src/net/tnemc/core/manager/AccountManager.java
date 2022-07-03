package net.tnemc.core.manager;

/*
 * The New Economy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

import net.tnemc.core.TNECore;
import net.tnemc.core.account.Account;
import net.tnemc.core.account.NonPlayerAccount;
import net.tnemc.core.account.PlayerAccount;
import net.tnemc.core.account.SharedAccount;
import net.tnemc.core.actions.EconomyResponse;
import net.tnemc.core.actions.response.AccountResponse;
import net.tnemc.core.compatibility.log.DebugLevel;
import net.tnemc.core.manager.id.UUIDPair;
import net.tnemc.core.manager.id.UUIDProvider;
import net.tnemc.core.manager.id.impl.provider.BaseUUIDProvider;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Manages everything related to accounts.
 *
 * @see Account
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class AccountManager {

  private final Map<String, Account> accounts = new ConcurrentHashMap<>();

  private final LinkedHashMap<Class<? extends SharedAccount>, Function<String, Boolean>> types = new LinkedHashMap<>();

  protected UUIDProvider uuidProvider = new BaseUUIDProvider();

  public AccountManager() {
    addDefaultTypes();
  }

  /**
   * Used to create a new account based on the provided identifier and name.
   * @param identifier The identifierr to use for the creation, if this is a player then this should
   *                   be the String value of the UUID for that player.
   * @param name The name to use for this account.
   * @return A correlating {@link EconomyResponse response} containing the results.
   */
  public EconomyResponse createAccount(final String identifier, final String name) {
    if(accounts.containsKey(identifier)) {
      return AccountResponse.ALREADY_EXISTS;
    }

    Account account;

    if(UUIDProvider.validate(name)) {
      try {
        final UUID id = UUID.fromString(identifier);
        account = new PlayerAccount(id, name);

        uuidProvider.store(new UUIDPair(id, name));
      } catch(Exception ignore) {
        return AccountResponse.CREATION_FAILED;
      }
    } else {
      final Optional<SharedAccount> nonPlayerAccount = createNonPlayerAccount(name);

      if(nonPlayerAccount.isEmpty()) {
        return AccountResponse.CREATION_FAILED;
      }
      account = nonPlayerAccount.get();
    }

    accounts.put(account.getIdentifier(), account);
    return AccountResponse.CREATED;
  }

  /**
   * Used to create a Non-Player account based on the name. This method will search the
   * {@link #types} map for a suitable alternative.
   *
   * @param name The name to use for the creation.
   * @return An Optional containing the new account class if it was able to be created, otherwise an
   * empty Optional.
   */
  public Optional<SharedAccount> createNonPlayerAccount(final String name) {

    for(Map.Entry<Class<? extends SharedAccount>, Function<String, Boolean>> entry : types.entrySet()) {
      if(entry.getValue().apply(name)) {
        try {
          return Optional.of(entry.getKey().getDeclaredConstructor(String.class, String.class)
                                  .newInstance(name, name));
        } catch(Exception e) {
          TNECore.log().error("An error occured while trying to create a new NonPlayer Account" +
                                  "for : " + name, e, DebugLevel.STANDARD);
        }
      }
    }

    return Optional.empty();
  }

  /**
   * Used to find an {@link Account account} from a {@link UUID unique identifier}.
   * @param id The id to use in the search.
   * @return An optional containing the {@link Account account} if it exists, otherwise an empty
   * optional.
   */
  public Optional<Account> findAccount(final UUID id) {
    return Optional.ofNullable(accounts.get(id.toString()));
  }

  /**
   * Used to find an {@link Account account} from a string identifier, this could be a name or a
   * different identifier.
   * @param identifier The identifier to use in the search.
   * @return An optional containing the {@link Account account} if it exists, otherwise an empty
   * optional.
   */
  public Optional<Account> findAccount(final String identifier) {

    //Check first to see if the identifier is in the accounts map.
    //This would return non-player accounts.
    final Account account = accounts.get(identifier);
    if(account != null) {
      return Optional.of(account);
    }

    final Optional<UUIDPair> id = uuidProvider.retrieve(identifier);

    if(id.isPresent()) {
      return findAccount(id.get().getIdentifier());
    }
    return Optional.empty();
  }

  /**
   * Adds a new {@link Account} type. These should extend the {@link SharedAccount}.
   * @param type The class for this type.
   * @param check The function that should be used to check if a given String identifier, usually name,
   *              is valid for this account type.
   */
  public void addAccountType(final Class<? extends NonPlayerAccount> type, Function<String, Boolean> check) {
    types.put(type, check);
  }

  /**
   * Adds our default built-in account types.
   */
  public void addDefaultTypes() {

    //TODO: Add third-party before this.

    addAccountType(NonPlayerAccount.class, (value)->!UUIDProvider.validate(value));
  }

  public UUIDProvider uuidProvider() {
    return uuidProvider;
  }
}