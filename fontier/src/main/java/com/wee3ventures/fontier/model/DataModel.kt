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