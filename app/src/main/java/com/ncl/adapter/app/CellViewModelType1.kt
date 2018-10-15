package com.ncl.adapter.app

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ncl.adapter.CellViewModel
import com.ncl.adapter.GenericAdapter
import com.ncl.adapter.GenericViewHolder


class CellViewModelType1(val position: Int): CellViewModel<ViewHolderType1>() {

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolderType1 {
        return ViewHolderType1(inflater.inflate(R.layout.cell_1, parent, false))
    }

    override fun bindViewHolder(adapter: GenericAdapter<out CellViewModel<GenericViewHolder>>, holder: ViewHolderType1) {
        holder.label.append(" , pos: $position")
    }

    override fun unbindViewHolder(adapter: GenericAdapter<out CellViewModel<GenericViewHolder>>, holder: ViewHolderType1) {
    }

}
