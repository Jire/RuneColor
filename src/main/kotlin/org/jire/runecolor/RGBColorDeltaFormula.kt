/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.jire.runecolor

fun interface RGBColorDeltaFormula {
	
	fun delta(
		r1: Int, g1: Int, b1: Int,
		r2: Int, g2: Int, b2: Int
	): Double
	
	fun delta(
		rgb1: RGBColor,
		rgb2: RGBColor
	) = delta(
		rgb1.r, rgb1.g, rgb1.b,
		rgb2.r, rgb2.g, rgb2.b
	)
	
	companion object {
		val DEFAULT: RGBColorDeltaFormula = EuclideanRGBColorDeltaFormula
	}
	
}