package net.tnemc.core.account.holdings;
/*
 * The New Economy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

import net.tnemc.core.account.holdings.modify.HoldingsModifier;

import java.math.BigDecimal;

/**
 * Represents an entry for holdings. This contains all the information including region, currency
 * and the actual BigDecimal Holdings.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class HoldingsEntry {

  /**
   * The name of the region involved. This is usually a world, but could be something else such as a
   * world guard region name/identifier.
   */
  private String region;

  /**
   * The identifier of the currency involved.
   */
  private String currency;

  /**
   * The {@link BigDecimal amount} that this charge is for.
   */
  private BigDecimal amount;

  /**
   * Constructs an object that represents a charge during a financial transaction.
   *
   * @param region The name of the region involved. This is usually a world, but could be something
   *               else such as a world guard region name/identifier.
   * @param currency The identifier of the currency involved.
   * @param amount The {@link BigDecimal amount} that this charge is for.
   */
  public HoldingsEntry(String region, String currency, BigDecimal amount) {
    this.region = region;
    this.currency = currency;
    this.amount = amount;
  }

  public void modify(final HoldingsModifier modifier) {
    amount = modifier.modify(amount);
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}