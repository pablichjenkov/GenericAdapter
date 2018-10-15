package com.ncl.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView


open class GenericViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var cellViewModel: CellViewModel<GenericViewHolder>? = null

}
