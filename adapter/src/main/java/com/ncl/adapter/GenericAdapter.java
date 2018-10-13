package com.ncl.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ncl.adapter.exception.CellViewModelCreateException;
import java.util.Collection;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class GenericAdapter<T extends CellViewModel> extends RecyclerView.Adapter<GenericViewHolder> {

    private final CellTypeManager<T> cellTypeManager;
    private LayoutInflater layoutInflater;
    private AdapteeCollection<T> collection;

    public GenericAdapter(CellTypeManager<T> cellTypeManager) {
        this(cellTypeManager, new ListAdapteeCollection<T>());
    }

    public GenericAdapter(CellTypeManager<T> cellTypeManager, AdapteeCollection<T> collection) {
        this.cellTypeManager = cellTypeManager;
        this.collection = collection;
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO(Pablo): Do not return position return the real itemId if the content has.
        return position;
    }

    /**
     * Indicate to the RecyclerView the type of CellViewModel used to one position using a numeric value.
     *
     * @param position to analyze.
     * @return the id associated to the CellViewModel used to render the content given a position.
     */
    @Override
    public int getItemViewType(int position) {
        return cellTypeManager.getItemViewType(getItem(position));
    }

    /**
     * One of the two main methods in this class. Creates a GenericViewHolder instance with a
     * CellViewModel inside ready to be used. The CellTypeManager to create a GenericViewHolder using the
     * information given as parameter.
     *
     * @param viewGroup used to create the ViewHolder.
     * @param viewType  associated to the renderer.
     * @return ViewHolder extension with the CellViewModel it has to use inside.
     */
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if (layoutInflater == null) {
            // Reuse the inflater since it is associated to the same Activity Context
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
/*
        CellViewModel cellViewModel = cellTypeManager.buildGenericViewHolder(viewType);

        if (cellViewModel == null) {
            throw new CellViewModelCreateException("CellTypeManager have to return a not null viewHolder");
        }

        cellViewModel.onCreate(layoutInflater, viewGroup);
*/

        return cellTypeManager.buildGenericViewHolder(viewType, layoutInflater, viewGroup);
    }

    /**
     * Given a GenericViewHolder passed as argument and a position renders the view using the
     * CellViewModel previously stored into the GenericViewHolder.
     *
     * @param viewHolder with a CellViewModel class inside.
     * @param position   to render.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder viewHolder, int position) {
        T cellViewModel = getItem(position);

        cellViewModel.onBound();
    }

    public T getItem(int position) {
        return collection.get(position);
    }

    /**
     * Add an element to the AdapteeCollection.
     *
     * @param element to add.
     * @return if the element has been added.
     */
    public boolean add(T element) {
        return collection.add(element);
    }

    /**
     * Remove an element from the AdapteeCollection.
     *
     * @param element to remove.
     * @return if the element has been removed.
     */
    public boolean remove(Object element) {
        return collection.remove(element);
    }

    /**
     * Add a Collection of elements to the AdapteeCollection.
     *
     * @param elements to add.
     * @return if the elements have been added.
     */
    public boolean addAll(Collection<? extends T> elements) {
        return collection.addAll(elements);
    }

    /**
     * Remove a Collection of elements to the AdapteeCollection.
     *
     * @param elements to remove.
     * @return if the elements have been removed.
     */
    public boolean removeAll(Collection<?> elements) {
        return collection.removeAll(elements);
    }

    /**
     * Remove all elements inside the AdapteeCollection.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Allows the client code to access the AdapteeCollection from subtypes of GenericAdapter.
     *
     * @return collection used in the adapter as the adaptee class.
     */
    protected AdapteeCollection<T> getCollection() {
        return collection;
    }

    public void setCollection(AdapteeCollection<T> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("The AdapteeCollection configured can't be null");
        }

        this.collection = collection;
    }

    @Override
    public void onViewRecycled(GenericViewHolder holder) {
        super.onViewRecycled(holder);
        //holder.getCellViewModel().onRecycle();
    }

}
