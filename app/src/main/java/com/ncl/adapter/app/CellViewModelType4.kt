package com.ncl.adapter.app

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ncl.adapter.CellViewModel
import com.ncl.adapter.GenericAdapter
import com.ncl.adapter.GenericViewHolder


class CellViewModelType4(val position: Int) : CellViewModel<ViewHolderType4>() {

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolderType4 {
        return ViewHolderType4(inflater.inflate(R.layout.cell_4, parent, false))
    }

    override fun bindViewHolder(adapter: GenericAdapter<out CellViewModel<GenericViewHolder>>, holder: ViewHolderType4) {
        holder.label.append(" , pos: $position")
    }

    override fun unbindViewHolder(adapter: GenericAdapter<out CellViewModel<GenericViewHolder>>, holder: ViewHolderType4) {

    }

}
