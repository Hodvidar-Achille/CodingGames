package com.hodvidar.adventofcode.y2019;

import java.util.Map;

public final class Screen {
    private final Map<Integer, Layer> layers;
    private final Layer finalLayer;

    public Screen(final Map<Integer, Layer> layers, final int wide, final int height) {
        this.layers = layers;
        this.finalLayer = new Layer(wide, height);
        this.buildFinalLayer();
    }

    private void buildFinalLayer() {
        for (int i = 0; i < this.finalLayer.length; i++) {
            this.finalLayer.pixels[i] = getTopNonTransparentPixel(i);
        }
    }

    private int getTopNonTransparentPixel(final int p) {
        for (int i = 1; i <= this.layers.size(); i++) {
            final Layer l = this.layers.get(i);
            final int pixel = l.pixels[p];
            if (pixel == 0 || pixel == 1)
                return pixel;
            // if '2' continue;
        }
        return 2;
    }

    public String print() {
        return this.finalLayer.print();
    }

    public String print2() {
        return this.finalLayer.print2();
    }

}
