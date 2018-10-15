package com.ncl.adapter.app

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ncl.adapter.CellViewModel
import com.ncl.adapter.GenericAdapter
import com.ncl.adapter.GenericViewHolder


class CellViewModelType3(val position: Int) : CellViewModel<ViewHolderType3>() {

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolderType3 {
        return ViewHolderType3(inflater.inflate(R.layout.cell_3, parent, false))
    }

    override fun bindViewHolder(adapter: GenericAdapter<out CellViewModel<GenericViewHolder>>, holder: ViewHolderType3) {
        holder.label.append(" , pos: $position")
    }

    override fun unbindViewHolder(adapter: GenericAdapter<out CellViewModel<GenericViewHolder>>, holder: ViewHolderType3) {

    }

}
