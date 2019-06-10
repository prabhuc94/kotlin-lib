package com.wee3ventures.fontier.adapter

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.base.BaseSimpleHeaderAdapter
import com.wee3ventures.fontier.base.BaseViewHolder
import com.wee3ventures.fontier.builder.TedImagePickerBaseBuilder
import com.wee3ventures.fontier.builder.type.MediaType
import com.wee3ventures.fontier.databinding.ItemGalleryCameraBinding
import com.wee3ventures.fontier.databinding.ItemGalleryMediaBinding
import com.wee3ventures.fontier.model.Media
import com.wee3ventures.fontier.zoom.TedImageZoomActivity

internal class MediaAdapter(
    private val activity: Activity,
    private val builder: TedImagePickerBaseBuilder<*>
) :
    BaseSimpleHeaderAdapter<Media>(if (builder.showCameraTile) 1 else 0) {

    internal val selectedUriList: MutableList<Uri> = mutableListOf()
    var onMediaAddListener: (() -> Unit)? = null

    override fun getHeaderViewHolder(parent: ViewGroup) = CameraViewHolder(parent)
    override fun getItemViewHolder(parent: ViewGroup) = ImageViewHolder(parent)

    fun toggleMediaSelect(uri: Uri) {
        if (selectedUriList.contains(uri)) {
            removeMedia(uri)
        } else {
            addMedia(uri)
        }
    }


    private fun addMedia(uri: Uri) {
        if (selectedUriList.size == builder.maxCount) {
            val message =
                builder.maxCountMessage ?: activity.getString(builder.maxCountMessageResId)
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        } else {
            selectedUriList.add(uri)
            onMediaAddListener?.invoke()
            refreshSelectedView()
        }
    }

    private fun getViewPosition(it: Uri): Int =
        items.indexOfFirst { media -> media.uri == it } + headerCount


    private fun removeMedia(uri: Uri) {
        val position = getViewPosition(uri)
        selectedUriList.remove(uri)
        notifyItemChanged(position)
        refreshSelectedView()
    }

    private fun refreshSelectedView() {
        selectedUriList.forEach {
            val position: Int = getViewPosition(it)
            notifyItemChanged(position)
        }
    }

    inner class ImageViewHolder(parent: ViewGroup) :
        BaseViewHolder<ItemGalleryMediaBinding, Media>(parent, R.layout.item_gallery_media) {

        init {
            binding.run {
                selectType = builder.selectType
                viewZoomOut.setOnClickListener {
                    startZoomActivity(getItem(adapterPosition))
                }
                showZoom = false
            }

        }

        override fun bind(data: Media) {
            binding.run {
                media = data
                isSelected = selectedUriList.contains(data.uri)
                if (isSelected) {
                    selectedNumber = selectedUriList.indexOf(data.uri) + 1
                }

                showZoom =
                    !isSelected && (builder.mediaType == MediaType.IMAGE) && builder.showZoomIndicator
            }
        }

        override fun recycled() {
            Glide.with(itemView).clear(binding.ivImage)
        }

        private fun startZoomActivity(media: Media) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                binding.ivImage,
                media.uri.toString()
            ).toBundle()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                activity.startActivity(TedImageZoomActivity.getIntent(activity, media.uri), options)
            }

        }
    }

    inner class CameraViewHolder(parent: ViewGroup) : HeaderViewHolder<ItemGalleryCameraBinding>(
        parent, R.layout.item_gallery_camera
    ) {

        init {
            binding.ivImage.setImageResource(builder.cameraTileImageResId)
            itemView.setBackgroundResource(builder.cameraTileBackgroundResId)
        }

    }

}