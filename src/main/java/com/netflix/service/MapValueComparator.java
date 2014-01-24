package com.netflix.service;

import java.util.Comparator;
import java.util.Map;

public class MapValueComparator implements Comparator<Map.Entry<String,Integer>> {

	public int compare(Map.Entry<String,Integer> entry1, Map.Entry<String,Integer> entry2) {
		// Compare entry2 to entry1 for ascending order.
		if (entry2.getValue() >= entry1.getValue()) {
			return 1;
		} else {
			return -1;
		}
        
    }
}