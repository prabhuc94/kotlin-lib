package com.wee3ventures.fontier.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class DataBody {
    fun create(data : Any) : RequestBody = "$data".toRequestBody("text/plain".toMediaTypeOrNull())
}