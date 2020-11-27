package com.board_of_ads.service.interfaces;

public interface Combine<T> {

    public T mergeParent(T o1, T o2);
}