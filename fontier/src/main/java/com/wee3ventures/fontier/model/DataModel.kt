package com.wee3ventures.fontier.model

import android.net.Uri
import com.wee3ventures.fontier.Enumaration.Fonts

data class LoginModel(
    val logo : Any,
    val background : Any,
    val passwordError : Any,
    val usernameError : Any,
    val fontPath : String,
    val fontName : Fonts
)

data class AboutModel(
    val logo : Any ?= null,
    val app_name : Any ?= null,
    val description : Any ?= null,
    val app_version : Any ?= null,
    val play_store_url : Any ?= null,
    val toolbar_title : Any ?= null,
    val descFontName : Fonts ?= null,
    val app_name_fontName : Fonts ?= null,
    val version_fontName : Fonts ?= null,
    val common_fontName : Fonts ?= null
)

data class Social(
    val facebook_url : Any ?= null,
    val twitter_url : Any ?= null
)

internal data class Album(
    val name: String,
    val thumbnailUri: Uri,
    val mediaUris: List<Media>
) {
    val mediaCount: Int = mediaUris.size
}

internal data class Media(
    val albumName: String,
    val uri: Uri,
    val dateTimeMills: Long
)