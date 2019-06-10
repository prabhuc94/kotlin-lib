package com.wee3ventures.fontier.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.model.Album
import com.wee3ventures.fontier.base.BaseRecyclerViewAdapter
import com.wee3ventures.fontier.base.BaseViewHolder
import com.wee3ventures.fontier.databinding.ItemAlbumBinding

internal class AlbumAdapter : BaseRecyclerViewAdapter<Album, AlbumAdapter.AlbumViewHolder>() {

    private var selectedPosition = 0

    override fun getViewHolder(parent: ViewGroup, viewType: ViewType) = AlbumViewHolder(parent)

    fun setSelectedAlbum(album: Album) {
        val index = items.indexOf(album)
        if (index >= 0 && selectedPosition != index) {
            val lastSelectedPosition = selectedPosition
            selectedPosition = index
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    inner class AlbumViewHolder(parent: ViewGroup) :
        BaseViewHolder<ItemAlbumBinding, Album>(parent, R.layout.item_album) {
        override fun bind(data: Album) {
            binding.album = data
            binding.isSelected = adapterPosition == selectedPosition
        }

        override fun recycled() {
            Glide.with(itemView).clear(binding.ivImage)
        }
    }
}