package com.ncl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


interface CellViewHolderFactory<VM, VH : GenericViewHolder<VM>> {
    fun createViewHolder(layoutInflater: LayoutInflater, viewGroup: ViewGroup): VH
}
