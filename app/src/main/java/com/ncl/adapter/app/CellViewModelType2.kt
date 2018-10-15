package com.ncl.adapter.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ncl.adapter.CellViewModel
import com.ncl.adapter.GenericAdapter
import com.ncl.adapter.GenericViewHolder


class CellViewModelType2(val position: Int): CellViewModel<ViewHolderType2>() {

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolderType2 {
        return ViewHolderType2(inflater.inflate(R.layout.cell_2, parent, false))
    }

    override fun bindViewHolder(adapter: GenericAdapter<out CellViewModel<GenericViewHolder>>, holder: ViewHolderType2) {
        holder.label.append(" , pos: $position")
    }

    override fun unbindViewHolder(adapter: GenericAdapter<out CellViewModel<GenericViewHolder>>, holder: ViewHolderType2) {
    }

}
