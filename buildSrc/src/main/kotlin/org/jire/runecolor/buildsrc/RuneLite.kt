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

package org.jire.runecolor.buildsrc

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.maven

object RuneLite {
	
	const val VERSION = "1.7.25"
	
	const val GROUP = "net.runelite"
	const val MAVEN_REPOSITORY_URL = "https://repo.runelite.net"
	
	fun RepositoryHandler.runelite() = maven(MAVEN_REPOSITORY_URL)
	
	fun DependencyHandlerScope.runelite(module: String) = "$GROUP:$module:$VERSION"
	
}