package drguis.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionsUtils {

	public static <T, S> Map<T,S> zip(List<T> list1, List<S> list2) {
		return IntStream.range(0, Math.min(list1.size(), list2.size())).boxed().collect(
				Collectors.toMap((Integer index) -> list1.get(index), (Integer index) -> list2.get(index)));
	}
	
}
