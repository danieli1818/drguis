package drguis.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Functions {

	public static <K, V> Map<K, V> zip(List<K> collection1, List<V> collection2) {
		Map<K, V> zippedMap = new HashMap<>();
		int finalLength = Math.min(collection1.size(), collection2.size());
		for (int i = 0; i < finalLength; i++) {
			zippedMap.put(collection1.get(i), collection2.get(i));
		}
		return zippedMap;
	}
	
}
