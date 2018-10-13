package com.ncl.cell.renderer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * CellViewModel created only for testing purposes.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class SubObjectRenderer extends Renderer<Object> {

  private View view;

  @Override protected void setUpView(View rootView) {

  }

  @Override protected void hookListeners(View rootView) {

  }

  @Override protected View inflate(LayoutInflater inflater, ViewGroup parent) {
    return view;
  }

  @Override public void render() {

  }

  public void setView(View view) {
    this.view = view;
  }
}
