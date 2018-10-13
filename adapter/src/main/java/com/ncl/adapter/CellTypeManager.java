package com.ncl.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ncl.adapter.exception.NullContentException;
import com.ncl.adapter.exception.NullPrototypeException;
import java.util.HashMap;


public class CellTypeManager<VM extends CellViewModel> {

    private HashMap<Class<? extends VM>, Integer> classCellTypeMap;
    private HashMap<Integer, CellViewHolderFactory<? extends CellViewModel, ? extends GenericViewHolder>> typeCellFactoryMap;


    public CellTypeManager() {
        classCellTypeMap = new HashMap<>();
        typeCellFactoryMap = new HashMap<>();
    }

    public <G extends VM, VH extends GenericViewHolder<G>> void bind(Class<G> cellViewModelClass
            , CellViewHolderFactory<G, VH> cellViewHolderFactory) {

        if (cellViewModelClass == null || cellViewHolderFactory == null) {
            throw new IllegalArgumentException("CellTypeManager.bind(...) does not accept null parameters");
        }

        Integer adapterViewType = classCellTypeMap.get(cellViewModelClass);

        if (adapterViewType == null) {
            adapterViewType = classCellTypeMap.size() + 1;
            // register a cell viewType for this model class
            classCellTypeMap.put(cellViewModelClass, adapterViewType);
        }

        // register a cell CellViewHolderFactory for this model class
        typeCellFactoryMap.put(adapterViewType, cellViewHolderFactory);

    }

    /**
     * Return the item view type used by the adapter to implement recycle mechanism.
     *
     * @param cellViewModel to be rendered.
     * @return an integer that represents the renderer inside the adapter.
     */
    /* package */ int getItemViewType(VM cellViewModel) {
        Integer viewType = classCellTypeMap.get(cellViewModel.getClass());
        validateViewType(viewType);

        return viewType;
    }

    /**
     * Main method of this class related to RecyclerView widget. This method is the responsible of
     * create a new CellViewModel instance with all the needed information to implement the rendering.
     * This method will validate all the attributes passed in the builder constructor and will create
     * a GenericViewHolder instance.
     * <p>
     * This method is used with RecyclerView because the view recycling mechanism is implemented out
     * of this class and we only have to return new GenericViewHolder instances.
     *
     * @return ready to use GenericViewHolder instance.
     */
    /*
    GenericViewHolder buildGenericViewHolder(Integer viewType) {
        CellViewHolderFactory<?> factory = getFactoryByViewType(viewType);
        CellViewModel newCellViewModel = factory.createViewHolder();
        validateRenderer(newCellViewModel);

        return new GenericViewHolder(newCellViewModel);
    }
*/
    /*
    CellViewModel getCellViewModelByType(Integer viewType) {
        CellViewHolderFactory<?> factory = getFactoryByViewType(viewType);
        CellViewModel newCellViewModel = factory.createViewHolder();
        validateRenderer(newCellViewModel);

        return newCellViewModel;
    }
*/
    /*
    private CellViewModel getRendererByIntegerType(Integer viewType) {

        for (Map.Entry<Class<?>, Integer> entry : classCellTypeMap.entrySet()) {
            if (entry.getValue().equals(viewType)) {
                Class<?> classKey = entry.getKey();
                return classRendererMap.get(classKey);
            }
        }

        // The CellViewModel for this viewType was not found
        return null;
    }
    */

    GenericViewHolder buildGenericViewHolder(Integer viewType, LayoutInflater layoutInflater, ViewGroup viewGroup) {
        CellViewHolderFactory factory = getFactoryByViewType(viewType);

        GenericViewHolder newGenericViewHolder = factory.createViewHolder(layoutInflater, viewGroup);
        //validateRenderer(newCellViewModel);

        return newGenericViewHolder;
    }

    private CellViewHolderFactory getFactoryByViewType(Integer viewType) {
        return typeCellFactoryMap.get(viewType);
    }

    private void validateRenderer(CellViewModel cellViewModel) {
        if (cellViewModel == null) {
            throw new NullPrototypeException(
                    "Your getRendererByIntegerType method implementation can't return a null class");
        }
    }

    private void validateViewType(Integer viewType) {
        if (viewType == null) {
            throw new NullContentException(
                    "CellTypeManager needs a view type to create a GenericViewHolder");
        }
    }

}
