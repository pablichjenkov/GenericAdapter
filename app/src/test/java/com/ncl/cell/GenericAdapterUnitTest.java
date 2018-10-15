package com.ncl.cell;

import com.ncl.adapter.CellViewModel;
import com.ncl.adapter.GenericAdapter;
import com.ncl.adapter.app.CellViewModelType1;
import com.ncl.adapter.app.CellViewModelType2;
import com.ncl.adapter.app.CellViewModelType3;
import org.junit.Test;

import static org.junit.Assert.*;


public class GenericAdapterUnitTest {

    @Test
    public void getItemType() {

        GenericAdapter<CellViewModel> genericAdapter = new GenericAdapter<>();

        ListAdapteeCollection<CellViewModel> collection = new ListAdapteeCollection();
        collection.add(new CellViewModelType1(0));
        collection.add(new CellViewModelType2(1));
        collection.add(new CellViewModelType3(2));

        collection.add(new CellViewModelType2(3));
        collection.add(new CellViewModelType3(4));
        collection.add(new CellViewModelType1(5));

        collection.add(new CellViewModelType3(6));
        collection.add(new CellViewModelType1(7));
        collection.add(new CellViewModelType2(8));

        genericAdapter.setCollection(collection);

        int itemViewType = genericAdapter.getItemViewType(0);
        assertEquals(0, itemViewType);

        itemViewType = genericAdapter.getItemViewType(1);
        assertEquals(1, itemViewType);

        itemViewType = genericAdapter.getItemViewType(2);
        assertEquals(2, itemViewType);

        itemViewType = genericAdapter.getItemViewType(3);
        assertEquals(1, itemViewType);

        itemViewType = genericAdapter.getItemViewType(4);
        assertEquals(2, itemViewType);

        itemViewType = genericAdapter.getItemViewType(5);
        assertEquals(0, itemViewType);

        itemViewType = genericAdapter.getItemViewType(6);
        assertEquals(2, itemViewType);

        itemViewType = genericAdapter.getItemViewType(7);
        assertEquals(0, itemViewType);

        itemViewType = genericAdapter.getItemViewType(8);
        assertEquals(1, itemViewType);
    }

}