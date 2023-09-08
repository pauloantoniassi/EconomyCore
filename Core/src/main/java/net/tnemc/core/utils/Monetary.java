package net.tnemc.core.utils;

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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * This class is used to add useful helper methods for ease of use purposes when working with
 * BigDecimals.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class Monetary {

  private final int scale;

  BigInteger major = BigInteger.ZERO;

  String minor = "0";

  public Monetary(final BigDecimal decimal, final int scale) {
    this.scale = scale;
    calculate(decimal, scale);
  }

  /**
   * Used to calculate the major and minor for this BigDecimal.
   */
  private void calculate(final BigDecimal decimal,
                         final int scale) {

    final BigDecimal value = decimal.setScale(scale, RoundingMode.DOWN);
    final String[] split = value.toPlainString().split("\\.");

    major = new BigInteger(split[0]);
    if(split.length > 1) {
      minor = split[1];
    }
  }

  public BigInteger major() {
    return major;
  }

  public String minor() {
    return minor;
  }

  public BigInteger minorAsInt() {
    return new BigInteger(minor);
  }

  public int scale() {
    return scale;
  }
}
