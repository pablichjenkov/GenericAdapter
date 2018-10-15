package com.ncl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.HashMap
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView


class GenericAdapter<VM : CellViewModel<GenericViewHolder>> : RecyclerView.Adapter<GenericViewHolder>() {

    private var collection: ArrayList<VM>
    private var layoutInflater: LayoutInflater? = null
    private val classCellTypeMap: HashMap<Class<out CellViewModel<GenericViewHolder>>, Int>
    private val typeInstanceMap: HashMap<Int, VM>

    init {
        this.collection = ArrayList()
        this.classCellTypeMap = HashMap()
        this.typeInstanceMap = HashMap()
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun getItemId(position: Int): Long {
        // TODO(Pablo): Do not return position return the real itemId if the content has.
        return position.toLong()
    }

    fun getItem(position: Int): VM {
        return collection.get(position)
    }

    override fun getItemViewType(position: Int): Int {
        val cellViewModel = getItem(position)
        val cellViewModelClass = cellViewModel.javaClass
        var adapterViewType: Int? = classCellTypeMap[cellViewModelClass]

        if (adapterViewType == null) {
            adapterViewType = classCellTypeMap.size
            // register a cell viewType for this model class
            classCellTypeMap[cellViewModelClass] = adapterViewType
            typeInstanceMap[adapterViewType] = cellViewModel
        }

        return adapterViewType
    }

    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, viewType: Int): GenericViewHolder {

        val inflater: LayoutInflater = layoutInflater ?: run {
            val newInflater = LayoutInflater.from(viewGroup.context)
            layoutInflater = newInflater
            newInflater
        }

        // TODO(Pablo): Remove the force unwrapping and return a Default Empty ViewHolder
        return typeInstanceMap[viewType]!!.createViewHolder(inflater, viewGroup)
    }

    override fun onBindViewHolder(@NonNull viewHolder: GenericViewHolder, position: Int) {
        val cellViewModel = getItem(position)
        viewHolder.cellViewModel = cellViewModel
        cellViewModel.bindViewHolder(this, viewHolder)
    }

    override fun onViewRecycled(holder: GenericViewHolder) {
        super.onViewRecycled(holder)
        holder.cellViewModel?.unbindViewHolder(this, holder)
    }


    // region: Collection Methods

    /**
     * Add an element to the AdapteeCollection.
     *
     * @param element to add.
     * @return if the element has been added.
     */
    fun add(element: VM): Boolean {
        return collection.add(element)
    }

    /**
     * Remove an element from the AdapteeCollection.
     *
     * @param element to remove.
     * @return if the element has been removed.
     */
    fun remove(element: Any): Boolean {
        return collection.remove(element)
    }

    /**
     * Add a Collection of elements to the AdapteeCollection.
     *
     * @param elements to add.
     * @return if the elements have been added.
     */
    fun addAll(elements: Collection<VM>): Boolean {
        return collection.addAll(elements)
    }

    /**
     * Remove a Collection of elements to the AdapteeCollection.
     *
     * @param elements to remove.
     * @return if the elements have been removed.
     */
    fun removeAll(elements: Collection<*>): Boolean {
        return collection.removeAll(elements)
    }

    /**
     * Remove all elements inside the AdapteeCollection.
     */
    fun clear() {
        collection.clear()
    }

    /**
     * Allows the client code to access the AdapteeCollection from subtypes of GenericAdapter.
     *
     * @return collection used in the adapter as the adaptee class.
     */
    protected fun getCollection(): ArrayList<VM> {
        return collection
    }

    fun setCollection(collection: ArrayList<VM>) {
        this.collection = collection
    }

    // endregion

}
