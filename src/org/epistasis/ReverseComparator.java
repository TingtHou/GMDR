package org.epistasis;

import java.util.Comparator;

public class ReverseComparator implements Comparator {
	private Comparator cmp;

	public ReverseComparator(Comparator cmp) {
		this.cmp = cmp;
	}

	public int compare(Object o1, Object o2) {
		return -cmp.compare(o1, o2);
	}
}
