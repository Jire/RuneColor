# RuneColor

_Easily convert RS2 HSL to/from(approximate) RGB_

---

### The problem

We often need to approximate a 16-bit RS2 HSL color given an RGB.

Typically, people simply used a direct translation from RGB to HSL, with a function like:

```kotlin
fun rgbToRS2HSL(r: Int, g: Int, b: Int): Short {
	val hsb = Color.RGBtoHSB(r, g, b, null)
	val hue = hsb[0]
	val saturation = hsb[1]
	val brightness = hsb[2]
	val encodedHue = (hue * 63F).toInt() // to 6-bits
	val encodedSaturation = (saturation * 7F).toInt() // to 3-bits
	val encodedBrightness = (brightness * 127F).toInt() // to 7-bits
	return ((encodedHue shl 10) + (encodedSaturation shl 7) + encodedBrightness).toShort()
}
```

This will then produce a result, for example:

```kotlin
val redHSL = rgbToRS2HSL(255, 0, 0)
println(redHSL) // HSL 1023, which is RGB color #ff0000
```

The problem is, within the RuneScape world, such a color does not actually exist, making for strange visuals:

![Strange red tint](https://i.imgur.com/hGMNxHj.png)

### The solution

`RuneColor` allows you to easily build and search an RS2 color palette for the closest match to an RGB color.

```kotlin
val palette = RS2CacheHSLColorPalette.load("cache")

val redHSL = palette.closest(255, 0, 0).first() // HSLColor(hsl=960)
println("${redHSL.hsl}(#${redHSL.rgb.toString(16)})") // 960(#ff0202)
```

Using the HSL color we calculated with `RuneColor` (`960`), you can see it looks great when applied in-game:

![Proper red](https://i.imgur.com/fBV4r3K.png)

### Under the hood

Under the hood, by default `RuneColor` by default loads all game model's face colors to use as the search palette.

This color palette is mapped into RGB, and then sorted
by [CIEDE2000](https://en.wikipedia.org/wiki/Color_difference#CIEDE2000) to find the closest matching RS2 HSL colors for
the given RGB color.

It's possible and often that you might want to use a limited color palette, which you can easily pass a `ShortArray` to
create:

```kotlin
val palette = RS2HSLColorPalette(shortArray)
```

Or you can pass a `vararg Int`:

```kotlin
val palette = RS2HSLColorPalette(1, 2, 3, 4, 5, ...)
```