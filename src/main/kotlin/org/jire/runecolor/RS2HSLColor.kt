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

import kotlin.math.roundToInt

@JvmInline
value class RS2HSLColor(val hsl: Short) {
	constructor(int: Int) : this(int.toShort())
	
	constructor(h: Int, s: Int, l: Int) : this(hsl(h, s, l))
	constructor(h: Float, s: Float, l: Float) : this(h.toInt(), s.toInt(), l.toInt())
	
	val rgb get() = toRGB(hsl.toInt())
	
	companion object {
		
		fun hsl(h: Int, s: Int, l: Int) = when {
			h and 0x3F.inv() != 0 -> throw IllegalArgumentException("h invalid (h=$h,s=$s,l=$l)")
			s and 7.inv() != 0 -> throw IllegalArgumentException("s invalid (h=$h,s=$s,l=$l)")
			l and 0x7F.inv() != 0 -> throw IllegalArgumentException("l invalid (h=$h,s=$s,l=$l)")
			
			else -> ((h and 0x3F) shl 10) or ((s and 0x7) shl 7) or (l and 0x7F)
		}
		
		fun toRGB(hsl: Int): RGBColor {
			val h = (hsl ushr 10) and 0x3F
			val s = (hsl ushr 7) and 7
			val l = hsl and 0x7F
			return toRGB(h.toFloat() / 63, s.toFloat() / 7, l.toFloat() / 127)
		}
		
		fun toRGB(h: Float, s: Float, l: Float): RGBColor {
			val r: Float
			val g: Float
			val b: Float
			if (s == 0F) {
				b = l
				g = b
				r = g // achromatic
			} else {
				val q = if (l < 0.5) l * (1 + s) else l + s - l * s
				val p = 2 * l - q
				r = hueToRGB(p, q, h + 1F / 3)
				g = hueToRGB(p, q, h)
				b = hueToRGB(p, q, h - 1F / 3)
			}
			return RGBColor(
				(r * 255).roundToInt(),
				(g * 255).roundToInt(),
				(b * 255).roundToInt()
			)
		}
		
		private fun hueToRGB(p: Float, q: Float, h: Float): Float {
			val h = when {
				h < 0 -> h + 1
				h > 1 -> h - 1
				else -> h
			}
			if (6 * h < 1)
				return p + (q - p) * 6 * h
			if (2 * h < 1)
				return q
			if (3 * h < 2)
				return p + (q - p) * 6 * (2F / 3F - h)
			return p
		}
		
	}
	
}