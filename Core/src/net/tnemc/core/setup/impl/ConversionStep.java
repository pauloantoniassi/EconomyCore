package net.tnemc.core.setup.impl;

/*
 * The New Economy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

import net.tnemc.core.setup.Step;

/**
 * ConversionStep - Converts old economy data.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class ConversionStep implements Step {

  /**
   * The human-friendly identifier for this step.
   *
   * @return the human-friendly identifier for this step.
   */
  @Override
  public String identifier() {
    return "Conversion";
  }

  /**
   * Runs this step.
   *
   * @return True if this step ran successfully, otherwise false.
   */
  @Override
  public boolean run() {
    return false;
  }
}