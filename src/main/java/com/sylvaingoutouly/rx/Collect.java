package com.sylvaingoutouly.rx;
import java.util.Arrays;

import rx.Observable;


public class Collect {

	public static void main(final String[] args) throws Exception {
	    Observable.from(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12))
	            .groupBy(item -> item % 4)
	            .flatMap(Observable::toList)
	            .filter(integers -> integers.size() > 2)
	            .toList()
	            .subscribe(result -> {
	                System.out.printf("%d results %s%n", result.size(), result);
	            });
	}

}
