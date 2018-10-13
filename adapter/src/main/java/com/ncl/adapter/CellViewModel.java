package com.ncl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ncl.adapter.exception.NotInflateViewException;
import androidx.recyclerview.widget.RecyclerView;


public abstract class CellViewModel<VH extends RecyclerView.ViewHolder> {

    private View rootView;

    //public abstract VH createViewHolder(LayoutInflater layoutInflater);

    public abstract void bindViewHolder(GenericAdapter<CellViewModel> adapter, VH holder);

    /*
    @Override
    public void onViewAttached(FlexibleAdapter<IFlexible> adapter, VH holder, int position) {
    }

    @Override
    public void onViewDetached(FlexibleAdapter<IFlexible> adapter, VH holder, int position) {
    }

    */

    /**
     * Method called when the renderer is going to be created. This method has the responsibility of
     * inflate the xml layout using the layoutInflater and the parent ViewGroup, set itself to the
     * tag and call setUpView and hookListeners methods.
     *
     * @param layoutInflater used to inflate the view.
     * @param parent         used to inflate the view.
     */
    /* package */ void onCreate(LayoutInflater layoutInflater, ViewGroup parent) {

        if (rootView == null) {
            throw new NotInflateViewException(
                    "CellViewModel instances have to return a not null view in inflateView method");
        }

        onSetUpView(rootView);
        onHookListeners(rootView);
    }

    /**
     * Method to access the root view rendered in the CellViewModel.
     *
     * @return top view in the view hierarchy of one CellViewModel.
     */
    public View getRootView() {
        return rootView;
    }

    /**
     * Method to access to the current CellViewModel Context.
     *
     * @return the context associated to the root view or null if the root view has not been
     * initialized.
     */
    protected Context getContext() {
        return getRootView() != null ? getRootView().getContext() : null;
    }

    /**
     * Map all the widgets from the rootView to CellViewModel members.
     *
     * @param rootView inflated using previously.
     */
    protected abstract void onSetUpView(View rootView);

    /**
     * Set all the listeners to members mapped in setUpView method.
     *
     * @param rootView inflated using previously.
     */
    protected abstract void onHookListeners(View rootView);

    /**
     * Method where the presentation logic algorithm have to be declared or implemented.
     */
    public abstract void onBound();

    /**
     * Method called when the CellViewModel has been recycled. This method has the responsibility of
     * update the content stored in the renderer.
     *
     */
    public void onRecycle() {}

}
