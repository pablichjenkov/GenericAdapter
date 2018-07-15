package com.ncl.adapter.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ncl.cell.renderer.Cell;
import com.ncl.cell.renderer.RendererAdapter;
import com.ncl.cell.renderer.RendererBuilder;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RendererBuilder<Cell> rendererBuilder = new RendererBuilder<>();

        rendererBuilder.bind(CellType1.class, new CellType1Renderer());

        RendererAdapter<Cell> rendererAdapter = new RendererAdapter<Cell>(rendererBuilder);

    }
}
