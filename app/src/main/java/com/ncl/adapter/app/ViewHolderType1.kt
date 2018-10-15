package com.ncl.adapter.app

import android.view.View
import android.widget.TextView
import com.ncl.adapter.GenericViewHolder


class ViewHolderType1(var view: View) : GenericViewHolder(view) {

    var label: TextView = view.findViewById(R.id.label)

}
