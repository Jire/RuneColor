/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.jire.runecolor

import kotlin.math.*

object CIEDE2000RGBColorDeltaFormula : RGBColorDeltaFormula {
	
	override fun delta(
		r1: Int, g1: Int, b1: Int,
		r2: Int, g2: Int, b2: Int
	): Double {
		val b1 = b1.toDouble()
		val b2 = b2.toDouble()
		
		val Lmean = (r1 + r2) / 2.0
		val C1 = sqrt(g1.toDouble().pow(2) + b1.pow(2))
		val C2 = sqrt(g2.toDouble().pow(2) + b2.pow(2))
		val Cmean = (C1 + C2) / 2.0
		val G = (1 - sqrt(Cmean.pow(7.0) / (Cmean.pow(7.0) + 25.0.pow(7.0)))) / 2
		val a1prime = g1 * (1 + G)
		val a2prime = g2 * (1 + G)
		val C1prime = sqrt(a1prime * a1prime + b1 * b1)
		val C2prime = sqrt(a2prime * a2prime + b2 * b2)
		val Cmeanprime = (C1prime + C2prime) / 2
		val h1prime = atan2(b1, a1prime) + 2 * Math.PI * if (atan2(b1, a1prime) < 0) 1 else 0
		val h2prime = atan2(b2, a2prime) + 2 * Math.PI * if (atan2(b2, a2prime) < 0) 1 else 0
		val Hmeanprime =
			if (abs(h1prime - h2prime) > Math.PI) (h1prime + h2prime + 2 * Math.PI) / 2
			else (h1prime + h2prime) / 2
		val T =
			1.0 - 0.17 *
					cos(Hmeanprime - Math.PI / 6.0) + 0.24 *
					cos(2 * Hmeanprime) + 0.32 *
					cos(3 * Hmeanprime + Math.PI / 30) - 0.2 *
					cos(4 * Hmeanprime - 21 * Math.PI / 60)
		val deltahprime =
			if (abs(h1prime - h2prime) <= Math.PI) h2prime - h1prime
			else if (h2prime <= h1prime) h2prime - h1prime + 2 * Math.PI
			else h2prime - h1prime - 2 * Math.PI
		val deltaLprime = r2 - r1
		val deltaCprime = C2prime - C1prime
		val deltaHprime = 2.0 * sqrt(C1prime * C2prime) * sin(deltahprime / 2.0)
		val SL = 1.0 + 0.015 * (Lmean - 50) * (Lmean - 50) / sqrt(20 + (Lmean - 50) * (Lmean - 50))
		val SC = 1.0 + 0.045 * Cmeanprime
		val SH = 1.0 + 0.015 * Cmeanprime * T
		val deltaTheta = 30 * Math.PI / 180 * exp(
			-((180 / Math.PI * Hmeanprime - 275) / 25) *
					((180 / Math.PI * Hmeanprime - 275) / 25)
		)
		val RC = 2 * sqrt(Cmeanprime.pow(7.0) / (Cmeanprime.pow(7.0) + 25.0.pow(7.0)))
		val RT = -RC * sin(2 * deltaTheta)
		val KL = 1.0
		val KC = 1.0
		val KH = 1.0
		return sqrt(
			deltaLprime / (KL * SL) * (deltaLprime / (KL * SL)) +
					deltaCprime / (KC * SC) * (deltaCprime / (KC * SC)) +
					deltaHprime / (KH * SH) * (deltaHprime / (KH * SH)) +
					RT * (deltaCprime / (KC * SC)) * (deltaHprime / (KH * SH))
		)
	}
	
}