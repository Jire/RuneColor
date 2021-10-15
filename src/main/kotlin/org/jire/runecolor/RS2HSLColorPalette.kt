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

class RS2HSLColorPalette(val rs2HSLColors: ShortArray) {
	constructor(vararg rs2HSLColors: Int) : this(rs2HSLColors.map { it.toShort() }.toShortArray())
	
	val rgbColors by lazy(LazyThreadSafetyMode.NONE) {
		rs2HSLColors.map { RS2HSLColor(it).rgb.rgb }.toIntArray()
	}
	
	val rs2HSLToRGB by lazy(LazyThreadSafetyMode.NONE) {
		rs2HSLColors.mapIndexed { index, hsl -> hsl to rgbColors[index] }
	}
	
	fun closest(
		rgbColor: RGBColor,
		rgbColorDeltaFormula: RGBColorDeltaFormula = RGBColorDeltaFormula.DEFAULT
	) = rs2HSLToRGB
		.map { RS2HSLColor(it.first) to RGBColor(it.second) }
		.sortedBy { rgbColorDeltaFormula.delta(rgbColor, it.second) }
		.map { it.first }
	
	fun closest(rgb: Int, rgbColorDeltaFormula: RGBColorDeltaFormula = RGBColorDeltaFormula.DEFAULT) =
		closest(RGBColor(rgb), rgbColorDeltaFormula)
	
	fun closest(r: Int, g: Int, b: Int, rgbColorDeltaFormula: RGBColorDeltaFormula = RGBColorDeltaFormula.DEFAULT) =
		closest(RGBColor(r, g, b), rgbColorDeltaFormula)
	
}