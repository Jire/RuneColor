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

import net.runelite.cache.IndexType
import net.runelite.cache.definitions.loaders.ModelLoader
import net.runelite.cache.fs.Store
import java.io.File

object RS2CacheHSLColorPalette {
	
	private fun loadPaletteArray(store: Store, modelLoader: ModelLoader): ShortArray =
		store.getIndex(IndexType.MODELS).archives
			.mapNotNull { archive ->
				val rawData = store.storage.loadArchive(archive)
				val data = archive.decompress(rawData)
				modelLoader.load(archive.archiveId, data)?.faceColors
			}
			.flatMap { it.asSequence() }
			.distinct()
			.toShortArray()
	
	fun load(store: Store, modelLoader: ModelLoader = ModelLoader()) =
		RS2HSLColorPalette(loadPaletteArray(store, modelLoader))
	
	fun load(storeFile: File, modelLoader: ModelLoader = ModelLoader()) =
		load(Store(storeFile).apply { load() }, modelLoader)
	
	fun load(storePath: String = "cache", modelLoader: ModelLoader = ModelLoader()) = load(File(storePath), modelLoader)
	
}