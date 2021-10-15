/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.jire.runecolor

import java.awt.Color

@JvmInline
value class RGBColor(val rgb: Int) {
	constructor(r: Int, g: Int, b: Int) : this(rgb(r, g, b))
	
	val r get() = r(rgb)
	val g get() = g(rgb)
	val b get() = b(rgb)
	
	fun toString(radix: Int) = rgb.toString(radix)
	
	val rs2HSL get() = toRS2HSL(r, g, b)
	
	companion object {
		
		fun rgb(r: Int, g: Int, b: Int): Int = when {
			r and 0xFF.inv() != 0 -> throw IllegalArgumentException("r invalid (r=$r,g=$g,b=$b)")
			g and 0xFF.inv() != 0 -> throw IllegalArgumentException("g invalid (r=$r,g=$g,b=$b)")
			b and 0xFF.inv() != 0 -> throw IllegalArgumentException("b invalid (r=$r,g=$g,b=$b)")
			
			else -> ((r and 0xFF) shl 16) or ((g and 0xFF) shl 8) or (b and 0xFF)
		}
		
		fun r(rgb: Int) = (rgb ushr 16) and 0xFF
		fun g(rgb: Int) = (rgb ushr 8) and 0xFF
		fun b(rgb: Int) = rgb and 0xFF
		
		fun toRS2HSL(r: Int, g: Int, b: Int): RS2HSLColor {
			val hsb = Color.RGBtoHSB(r, g, b, null)
			val hue = hsb[0]
			val saturation = hsb[1]
			val brightness = hsb[2]
			val encodedHue = (hue * 63F).toInt() // to 6-bits
			val encodedSaturation = (saturation * 7F).toInt() // to 3-bits
			val encodedBrightness = (brightness * 127F).toInt() // to 7-bits
			return RS2HSLColor(((encodedHue shl 10) + (encodedSaturation shl 7) + encodedBrightness).toShort())
		}
		
	}
	
}