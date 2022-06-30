package net.tnemc.core.transaction;

/*
 * The New Economy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * Represents a source of a transaction.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface TransactionSource {

  /**
   * The human-friendly identifier for this {@link TransactionSource}.
   * @return The human-friendly identifier for this source.
   */
  String identifier();
}