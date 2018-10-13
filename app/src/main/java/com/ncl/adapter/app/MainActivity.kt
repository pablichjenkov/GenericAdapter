package com.ncl.adapter.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ncl.adapter.*


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var genericAdapter: GenericAdapter<CellViewModel<*>>
    private val viewModelCollection: ListAdapteeCollection<CellViewModel<*>> = ListAdapteeCollection()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.genericRecycler)

        val cellTypeManager = CellTypeManager<CellViewModel<*>>()

        cellTypeManager.bind(CellViewModelType1::class.java, object: CellViewHolderFactory<CellViewModelType1, ViewHolderType1> {

            override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolderType1 {
                        return ViewHolderType1(inflater.inflate(R.layout.cell_1, parent, false))
                    }

                })

        cellTypeManager.bind(CellViewModelType2::class.java, object: CellViewHolderFactory<CellViewModelType2, ViewHolderType2> {

            override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolderType2 {
                        return ViewHolderType2(inflater.inflate(R.layout.cell_2, parent, false))
                    }

                })

        cellTypeManager.bind(CellViewModelType3::class.java, object: CellViewHolderFactory<CellViewModelType3, ViewHolderType3> {

            override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolderType3 {
                        return ViewHolderType3(inflater.inflate(R.layout.cell_3, parent, false))
                    }

                })

        // todo: complete sample app
        genericAdapter = GenericAdapter(cellTypeManager)

        recyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)


        recyclerView.adapter = genericAdapter
    }

    override fun onResume() {
        super.onResume()
        populateViewModels()
        genericAdapter.setCollection(viewModelCollection)
    }

    private fun populateViewModels() {
        viewModelCollection.add(CellViewModelType1())
        viewModelCollection.add(CellViewModelType2())
        viewModelCollection.add(CellViewModelType3())
        viewModelCollection.add(CellViewModelType2())
        viewModelCollection.add(CellViewModelType3())
        viewModelCollection.add(CellViewModelType1())
        viewModelCollection.add(CellViewModelType3())
        viewModelCollection.add(CellViewModelType1())
        viewModelCollection.add(CellViewModelType2())
        viewModelCollection.add(CellViewModelType1())
        viewModelCollection.add(CellViewModelType2())
        viewModelCollection.add(CellViewModelType3())
    }

}
