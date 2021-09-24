package com.codinginflow.imagesearchapp.utils

import com.codinginflow.imagesearchapp.BuildConfig

object Constants {
    const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
    const val BASE_URL = "https://api.unsplash.com/"
    const val UNSPLASH_STARTING_PAGE_INDEX = 1
    const val DEFAULT_QUERY = "cats"
}