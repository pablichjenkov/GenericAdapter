package com.ncl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup


abstract class CellViewModel<VH : GenericViewHolder> {

    var children: List<CellViewModel<*>>? = null

    // Selection Adapter
    var isSelected: Boolean = false

    // Sticky Adapter
    var isSticky: Boolean = false

    // Expandable Adapter
    var isGroup: Boolean = false
    var isExpanded: Boolean = false


    abstract fun createViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): VH

    abstract fun bindViewHolder(adapter: GenericAdapter<out CellViewModel<GenericViewHolder>>, holder: VH)

    abstract fun unbindViewHolder(adapter: GenericAdapter<out CellViewModel<GenericViewHolder>>, holder: VH)

}
