package com.wee3ventures.fontier.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.builder.type.MediaType
import com.wee3ventures.fontier.model.Album
import com.wee3ventures.fontier.model.Media
import io.reactivex.Single
import java.io.File

internal class GalleryUtil {
    companion object {

        private const val INDEX_MEDIA_URI = MediaStore.MediaColumns.DATA
        private lateinit var albumName: String
        private lateinit var dateTaken: String
        internal fun getMedia(context: Context, mediaType: MediaType): Single<List<Album>> {
            return Single.create { emitter ->
                try {

                    val uri: Uri

                    if (mediaType === MediaType.IMAGE) {
                        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        dateTaken = MediaStore.Images.Media.DATE_TAKEN
                        albumName = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                    } else {
                        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        dateTaken = MediaStore.Video.Media.DATE_TAKEN
                        albumName = MediaStore.Video.Media.BUCKET_DISPLAY_NAME
                    }

                    val sortOrder = "$dateTaken DESC"
                    val projection = arrayOf(INDEX_MEDIA_URI, albumName, dateTaken)
                    val cursor =
                        context.contentResolver.query(uri, projection, null, null, sortOrder)
                    val albumList: List<Album> = cursor?.let {

                        val totalImageList =
                            generateSequence { if (cursor.moveToNext()) cursor else null }
                                .map { getImage(it) }
                                .toList()

                        val albumList: List<Album> = totalImageList.asSequence()
                            .groupBy { media: Media -> media.albumName }
                            .toSortedMap(Comparator { albumName1: String, albumName2: String ->
                                if (albumName2 == "Camera") {
                                    1
                                } else {
                                    albumName1.compareTo(albumName2, true)
                                }
                            })
                            .map { entry -> getAlbum(entry) }
                            .toList()


                        val totalAlbum = totalImageList.run {
                            val albumName = context.getString(R.string.ted_image_picker_album_all)
                            Album(
                                albumName,
                                getOrElse(0) { Media(albumName, Uri.EMPTY, 0) }.uri,
                                this
                            )
                        }

                        mutableListOf(totalAlbum).apply {
                            addAll(albumList)
                        }
                    } ?: emptyList()

                    cursor?.close()
                    emitter.onSuccess(albumList)
                } catch (exception: Exception) {
                    emitter.onError(exception)
                }

            }
        }

        private fun getAlbum(entry: Map.Entry<String, List<Media>>) =
            Album(entry.key, entry.value[0].uri, entry.value)

        private fun getImage(cursor: Cursor): Media {
            return cursor.run {
                val folderName = getString(getColumnIndex(albumName))
                val mediaPath = getString(getColumnIndex(INDEX_MEDIA_URI))
                val mediaUri: Uri = Uri.fromFile(File(mediaPath))
                val dateTimeMills = getString(getColumnIndex(dateTaken)).toLong()
                Media(folderName, mediaUri, dateTimeMills)
            }
        }


    }
}