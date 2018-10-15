package com.ncl.adapter.app

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ncl.adapter.GenericViewHolder


class ViewHolderType4(view: View) : GenericViewHolder(view) {

    var label: TextView = view.findViewById(R.id.label)

}