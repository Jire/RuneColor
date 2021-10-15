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

import org.jire.runecolor.buildsrc.RuneLite.runelite

plugins {
	kotlin("jvm")
}

group = "org.jire.runecolor"
version = "0.1.0"

repositories {
	mavenCentral()
	runelite()
}

dependencies {
	implementation(kotlin("stdlib"))
	
	api(runelite("runelite-api"))
	api(runelite("cache"))
}