package com.wee3ventures.fontier.adapter

import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.base.BaseRecyclerViewAdapter
import com.wee3ventures.fontier.base.BaseViewHolder
import com.wee3ventures.fontier.databinding.ItemSelectedMediaBinding

internal class SelectedMediaAdapter :
    BaseRecyclerViewAdapter<Uri, SelectedMediaAdapter.MediaViewHolder>() {

    var onClearClickListener: ((Uri) -> Unit)? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun getViewHolder(parent: ViewGroup, viewType: ViewType) = MediaViewHolder(parent)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutManager = recyclerView.layoutManager
    }


    inner class MediaViewHolder(parent: ViewGroup) :
        BaseViewHolder<ItemSelectedMediaBinding, Uri>(parent, R.layout.item_selected_media) {

        init {
            binding.ivClear.setOnClickListener {
                onClearClickListener?.invoke(getItem(adapterPosition))
            }
        }

        override fun bind(data: Uri) {
            Log.d("ted", "MediaViewHolder: $adapterPosition")
            binding.uri = data
        }

        override fun recycled() {
            Glide.with(itemView).clear(binding.ivImage)
        }
    }

}