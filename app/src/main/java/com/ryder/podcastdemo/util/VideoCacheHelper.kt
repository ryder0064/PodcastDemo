package com.ryder.podcastdemo.util

import android.content.Context
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.Cache
import java.io.File

object VideoCacheHelper {
    private const val MAX_VIDEO_CACHE_SIZE_IN_BYTES = 300 * 1024 * 1024
    private var cache: Cache? = null
    @Synchronized
    fun getCache(context: Context): Cache {
        return if (cache != null) cache!! else SimpleCache(
            File(context.cacheDir, "video"), LeastRecentlyUsedCacheEvictor(
                MAX_VIDEO_CACHE_SIZE_IN_BYTES.toLong()
            ), ExoDatabaseProvider(context)
        ).also { cache = it }
    }

    fun clearCache() {
        if (cache != null) {
            cache!!.release()
            cache = null
        }
    }
}