package com.ncl.adapter.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ncl.adapter.*
import java.util.Random


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var genericAdapter: GenericAdapter<CellViewModel<GenericViewHolder>>
    private val viewModelCollection: ArrayList<CellViewModel<GenericViewHolder>> = ArrayList()

    private var factoryMap: HashMap<Int, (pos: Int) -> CellViewModel<GenericViewHolder>> = HashMap()

    init {

        this.factoryMap[1] = { pos: Int ->
            CellViewModelType1(pos) as CellViewModel<GenericViewHolder>
        }

        this.factoryMap[2] = { pos: Int ->
            CellViewModelType2(pos) as CellViewModel<GenericViewHolder>
        }

        this.factoryMap[3] = { pos: Int ->
            CellViewModelType3(pos) as CellViewModel<GenericViewHolder>
        }

        this.factoryMap[4] = { pos: Int ->
            CellViewModelType4(pos) as CellViewModel<GenericViewHolder>
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.genericRecycler)

        recyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)

        genericAdapter = GenericAdapter()
        recyclerView.adapter = genericAdapter
    }

    override fun onResume() {
        super.onResume()
        populateViewModels()
        genericAdapter.setCollection(viewModelCollection)
    }

    private fun populateViewModels() {

        val random = Random()

        for (i in 0..19) {

            val nextViewModelType = random.nextInt(4) + 1

            factoryMap[nextViewModelType]
                    ?.invoke(i)
                    ?.let {
                        viewModelCollection.add(it)
                    }

        }

    }

}
