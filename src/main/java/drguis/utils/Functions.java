package drguis.utils;

import java.util.HashMap;
import java.util.Iterator;
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
	
	public static <K, V> Map<K, V> zip(Iterable<K> iterable1, Iterable<V> iterable2) {
		Map<K, V> zippedMap = new HashMap<>();
		Iterator<K> iterator1 = iterable1.iterator();
		Iterator<V> iterator2 = iterable2.iterator();
		while (iterator1.hasNext() && iterator2.hasNext()) {
			zippedMap.put(iterator1.next(), iterator2.next());
		}
		return zippedMap;
	}
	
}
