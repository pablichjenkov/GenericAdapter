package com.ncl.adapter.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ncl.adapter.CellViewModel
import com.ncl.adapter.GenericAdapter


class CellViewModelType1 : CellViewModel<ViewHolderType1>() {


    override fun bindViewHolder(adapter: GenericAdapter<CellViewModel<RecyclerView.ViewHolder>>?, holder: ViewHolderType1?) {
    }

    override fun onSetUpView(rootView: View?) {

    }

    override fun onHookListeners(rootView: View?) {

    }

    override fun onBound() {

    }

}
