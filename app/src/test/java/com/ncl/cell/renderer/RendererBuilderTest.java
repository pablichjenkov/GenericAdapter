package com.ncl.cell.renderer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intervalintl.renderer.exception.NeedsPrototypesException;
import com.intervalintl.renderer.exception.NullContentException;
import com.intervalintl.renderer.exception.NullLayoutInflaterException;
import com.intervalintl.renderer.exception.NullParentException;
import com.intervalintl.renderer.exception.NullPrototypeClassException;
import com.ncl.adapter.exception.NeedsPrototypesException;
import com.ncl.adapter.exception.NullContentException;
import com.ncl.adapter.exception.NullLayoutInflaterException;
import com.ncl.adapter.exception.NullParentException;
import com.ncl.adapter.exception.NullPrototypeClassException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Test class created to check the correct behaviour of RendererBuilder
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class RendererBuilderTest {

  private ObjectRendererBuilder rendererBuilder;

  private List<Renderer<Object>> prototypes;
  private ObjectRenderer objectRenderer;
  private SubObjectRenderer subObjectRenderer;

  @Mock private View mockedConvertView;
  @Mock private ViewGroup mockedParent;
  @Mock private LayoutInflater mockedLayoutInflater;
  @Mock private Object mockedContent;
  @Mock private View mockedRendererdView;

  @Before public void setUp() {
    initializeMocks();
    initializePrototypes();
    initializeRendererBuilder();
  }

  @Test(expected = NeedsPrototypesException.class)
  public void shouldThrowNeedsPrototypeExceptionIfPrototypesIsNull() {
    rendererBuilder = new ObjectRendererBuilder(null);
  }

  @Test(expected = NullContentException.class)
  public void shouldThrowNullContentExceptionIfBuildRendererWithoutContent() {
    buildRenderer(null, mockedConvertView, mockedParent, mockedLayoutInflater);
  }

  @Test(expected = NullParentException.class)
  public void shouldThrowNullParentExceptionIfBuildRendererWithoutParent() {
    buildRenderer(mockedContent, mockedConvertView, null, mockedLayoutInflater);
  }

  @Test(expected = NullPrototypeClassException.class)
  public void
  shouldThrowNullPrototypeClassExceptionIfRendererBuilderImplementationReturnsNullPrototypeClassAndGetItemViewType() {
    when(rendererBuilder.getPrototypeClass(mockedContent)).thenReturn(null);

    buildRenderer(mockedContent, mockedConvertView, mockedParent, mockedLayoutInflater);

    rendererBuilder.getItemViewType(mockedContent);
  }

  @Test(expected = NullPrototypeClassException.class)
  public void
  shouldThrowNullPrototypeClassExceptionIfRendererBuilderImplementationReturnsNullPrototypeClassAndBuildOneRenderer() {
    when(rendererBuilder.getPrototypeClass(mockedContent)).thenReturn(null);

    buildRenderer(mockedContent, mockedConvertView, mockedParent, mockedLayoutInflater);

    rendererBuilder.build();
  }

  @Test(expected = NullLayoutInflaterException.class)
  public void shouldThrowNullParentExceptionIfBuildARendererWithoutLayoutInflater() {

    buildRenderer(mockedContent, mockedConvertView, mockedParent, null);
  }

  @Test public void shouldReturnCreatedRenderer() {
    when(rendererBuilder.getPrototypeClass(mockedContent)).thenReturn(ObjectRenderer.class);

    Renderer<Object> renderer =
        buildRenderer(mockedContent, null, mockedParent, mockedLayoutInflater);

    assertEquals(objectRenderer.getClass(), renderer.getClass());
  }

  @Test public void shouldReturnRecycledRenderer() {
    when(rendererBuilder.getPrototypeClass(mockedContent)).thenReturn(ObjectRenderer.class);
    when(mockedConvertView.getTag()).thenReturn(objectRenderer);

    Renderer<Object> renderer =
        buildRenderer(mockedContent, mockedConvertView, mockedParent, mockedLayoutInflater);

    assertEquals(objectRenderer, renderer);
  }

  @Test public void shouldCreateRendererEvenIfTagInConvertViewIsNotNull() {
    when(rendererBuilder.getPrototypeClass(mockedContent)).thenReturn(ObjectRenderer.class);
    when(mockedConvertView.getTag()).thenReturn(subObjectRenderer);

    Renderer<Object> renderer =
        buildRenderer(mockedContent, mockedConvertView, mockedParent, mockedLayoutInflater);

    assertEquals(objectRenderer.getClass(), renderer.getClass());
  }

  @Test public void shouldReturnPrototypeSizeOnGetViewTypeCount() {
    assertEquals(prototypes.size(), rendererBuilder.getViewTypeCount());
  }

  @Test(expected = NeedsPrototypesException.class) public void shouldNotAcceptNullPrototypes() {
    RendererBuilder<Object> rendererBuilder = new RendererBuilder<Object>();

    rendererBuilder.withPrototypes(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldNotAcceptNullKeysBindingAPrototype() {
    RendererBuilder<Object> rendererBuilder = new RendererBuilder<Object>();

    rendererBuilder.bind(null, new ObjectRenderer());
  }

  @Test public void shouldAddPrototypeAndConfigureRendererBinding() {
    RendererBuilder<Object> rendererBuilder = new RendererBuilder<Object>();

    rendererBuilder.bind(Object.class, new ObjectRenderer());

    assertEquals(ObjectRenderer.class, rendererBuilder.getPrototypeClass(new Object()));
  }

  @Test public void shouldAddDescendantPrototypeAndConfigureRendererBinding() {
    RendererBuilder<Object> rendererBuilder = new RendererBuilder<Object>();

    rendererBuilder.bind(String.class, new StringRenderer());
    rendererBuilder.bind(Integer.class, new IntegerRenderer());


    assertEquals(StringRenderer.class, rendererBuilder.getPrototypeClass(""));
    assertEquals(IntegerRenderer.class, rendererBuilder.getPrototypeClass(0));
  }

  @Test public void shouldAddPrototypeAndConfigureBindingByClass() {
    RendererBuilder<Object> rendererBuilder = new RendererBuilder<Object>();

    rendererBuilder.withPrototype(new ObjectRenderer()).bind(Object.class, ObjectRenderer.class);

    assertEquals(ObjectRenderer.class, rendererBuilder.getPrototypeClass(new Object()));
  }

  @Test public void shouldAddDescendantPrototypesAndConfigureBindingByClass() {
    RendererBuilder<Object> rendererBuilder = new RendererBuilder<Object>();

    rendererBuilder
            .withPrototype(new IntegerRenderer()).bind(Integer.class, IntegerRenderer.class);

    assertEquals(StringRenderer.class, rendererBuilder.getPrototypeClass(""));
    assertEquals(IntegerRenderer.class, rendererBuilder.getPrototypeClass(0));
  }

  @Test public void shouldAddDescendantPrototypesBySetterAndConfigureBindingByClass() {
    RendererBuilder<Object> rendererBuilder = new RendererBuilder<Object>();

    rendererBuilder.setPrototypes(Arrays.asList(new StringRenderer(), new IntegerRenderer()));
    rendererBuilder.bind(String.class, StringRenderer.class);
    rendererBuilder.bind(Integer.class, IntegerRenderer.class);

    assertEquals(StringRenderer.class, rendererBuilder.getPrototypeClass(""));
    assertEquals(IntegerRenderer.class, rendererBuilder.getPrototypeClass(0));
  }

  @Test public void shouldAddDescendantPrototypesByConstructionAndConfigureBindingByClass() {
    RendererBuilder<Object> rendererBuilder =
            new RendererBuilder<Object>(Arrays.asList(new StringRenderer(), new IntegerRenderer()));

    rendererBuilder.bind(String.class, StringRenderer.class);
    rendererBuilder.bind(Integer.class, IntegerRenderer.class);

    assertEquals(StringRenderer.class, rendererBuilder.getPrototypeClass(""));
    assertEquals(IntegerRenderer.class, rendererBuilder.getPrototypeClass(0));
  }

  @Test public void shouldAddPrototyeAndconfigureBindingOnConstruction() {
    RendererBuilder<Object> rendererBuilder = new RendererBuilder<Object>(new ObjectRenderer());

    assertEquals(ObjectRenderer.class, rendererBuilder.getPrototypeClass(new Object()));
  }

  private void initializeMocks() {
    MockitoAnnotations.initMocks(this);
  }

  private void initializePrototypes() {
    prototypes = new LinkedList<Renderer<Object>>();
    objectRenderer = new ObjectRenderer();
    objectRenderer.setView(mockedRendererdView);
    subObjectRenderer = new SubObjectRenderer();
    subObjectRenderer.setView(mockedRendererdView);
    prototypes.add(objectRenderer);
    prototypes.add(subObjectRenderer);
  }

  private void initializeRendererBuilder() {
    rendererBuilder = new ObjectRendererBuilder(prototypes);
    rendererBuilder = spy(rendererBuilder);
  }

  private Renderer<Object> buildRenderer(Object content, View convertView, ViewGroup parent,
      LayoutInflater layoutInflater) {
    rendererBuilder.withContent(content);
    rendererBuilder.withParent(parent);
    rendererBuilder.withLayoutInflater(layoutInflater);
    rendererBuilder.withConvertView(convertView);
    return rendererBuilder.build();
  }
}
