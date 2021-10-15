/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.jire.runecolor

import kotlin.math.pow
import kotlin.math.sqrt

object EuclideanRGBColorDeltaFormula : RGBColorDeltaFormula {
	
	override fun delta(
		r1: Int, g1: Int, b1: Int,
		r2: Int, g2: Int, b2: Int
	) = sqrt(
		(r2 - r1).toDouble().pow(2)
				+ (g2 - g1).toDouble().pow(2)
				+ (b2 - b1).toDouble().pow(2)
	)
	
}