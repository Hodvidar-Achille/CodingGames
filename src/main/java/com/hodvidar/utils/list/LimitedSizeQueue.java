package com.hodvidar.utils.list;

import java.util.ArrayList;

public class LimitedSizeQueue<K> extends ArrayList<K> {

    private final int maxSize;

    public LimitedSizeQueue(final int size) {
        this.maxSize = size;
    }

    public boolean add(final K k) {
        final boolean r = super.add(k);
        if (size() > maxSize) {
            removeRange(0, size() - maxSize);
        }
        return r;
    }

    public K getYoungest() {
        return get(size() - 1);
    }

    public K getOldest() {
        return get(0);
    }
}
