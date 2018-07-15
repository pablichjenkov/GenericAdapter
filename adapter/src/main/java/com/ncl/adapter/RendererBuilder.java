/*
 * Copyright (C) 2014 Pedro Vicente Gómez Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ncl.adapter;

import com.ncl.adapter.exception.NullContentException;
import com.ncl.adapter.exception.NullPrototypeException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class created to work as builder for Renderer objects. This class provides methods to create a
 * Renderer instances using a fluent API.
 * <p>
 * The library users have to extends RendererBuilder and create a new one with prototypes. The
 * RendererBuilder implementation will have to declare the mapping between objects from the
 * AdapteeCollection and Renderer instances passed to the prototypes collection.
 * <p>
 * This class is not going to implement the view recycling if is used with the RecyclerView widget
 * because RecyclerView class already implements the view recycling for us.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
public class RendererBuilder<T extends Cell> {

    private HashMap<Class<? extends T>, Renderer<? extends T>> classRendererMap;
    private HashMap<Class<?>, Integer> classCellTypeMap;


    public RendererBuilder() {
        classCellTypeMap = new HashMap<>();
        classRendererMap = new HashMap<>();
    }

    /**
     * Given a class configures the classRendererMap between a class and a Renderer class.
     *
     * @param clazz     to bind.
     * @param prototype used as Renderer.
     * @return the current RendererBuilder instance.
     */
    public <G extends T> RendererBuilder<T> bind(Class<G> clazz, Renderer<G> prototype) {
        if (clazz == null || prototype == null) {
            throw new IllegalArgumentException("RendererBuilder.bind(...) does not accept null parameters");
        }

        Integer viewType = classCellTypeMap.get(clazz);

        if (viewType == null) {
            viewType = classCellTypeMap.size() + 1;
        }

        // register a cell viewType for this model class
        classCellTypeMap.put(clazz, viewType);

        // register a cell Renderer for this model class
        classRendererMap.put(clazz, prototype);

        return this;
    }

    /**
     * Return the item view type used by the adapter to implement recycle mechanism.
     *
     * @param content to be rendered.
     * @return an integer that represents the renderer inside the adapter.
     */
    int getItemViewType(T content) {
        Integer viewType = classCellTypeMap.get(content.getClass());
        validateViewType(viewType);
        return viewType;
    }

    /**
     * Main method of this class related to RecyclerView widget. This method is the responsible of
     * create a new Renderer instance with all the needed information to implement the rendering.
     * This method will validate all the attributes passed in the builder constructor and will create
     * a RendererViewHolder instance.
     * <p>
     * This method is used with RecyclerView because the view recycling mechanism is implemented out
     * of this class and we only have to return new RendererViewHolder instances.
     *
     * @return ready to use RendererViewHolder instance.
     */
    RendererViewHolder buildRendererViewHolder(Integer viewType) {
        Renderer renderer = getRendererByIntegerType(viewType);
        validateRenderer(renderer);
        Renderer clonedRenderer = renderer.copy();

        return new RendererViewHolder(clonedRenderer);
    }

    private Renderer getRendererByIntegerType(Integer viewType) {

        for (Map.Entry<Class<?>, Integer> entry : classCellTypeMap.entrySet()) {
            if (entry.getValue().equals(viewType)) {
                Class<?> classKey = entry.getKey();
                return classRendererMap.get(classKey);
            }
        }

        // The Renderer for this viewType was not found
        return null;
    }

    private void validateRenderer(Renderer renderer) {
        if (renderer == null) {
            throw new NullPrototypeException(
                    "Your getRendererByIntegerType method implementation can't return a null class");
        }
    }

    private void validateViewType(Integer viewType) {
        if (viewType == null) {
            throw new NullContentException(
                    "RendererBuilder needs a view type to create a RendererViewHolder");
        }
    }

}
