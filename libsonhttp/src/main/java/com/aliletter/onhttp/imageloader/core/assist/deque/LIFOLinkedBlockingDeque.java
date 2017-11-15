package com.aliletter.onhttp.imageloader.core.assist.deque;

import java.util.NoSuchElementException;


public class LIFOLinkedBlockingDeque<T> extends LinkedBlockingDeque<T> {

	private static final long serialVersionUID = -4114786347960826192L;

	
	@Override
	public boolean offer(T e) {
		return super.offerFirst(e);
	}

	
	@Override
	public T remove() {
		return super.removeFirst();
	}
}