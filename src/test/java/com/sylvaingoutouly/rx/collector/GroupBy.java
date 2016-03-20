package com.sylvaingoutouly.rx.collector;

import java.util.Arrays;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.junit.Test;

import rx.Observable;

public class GroupBy {

	@Test
	public void groupByList() {
		 Observable.from(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12))
         .groupBy(item -> item % 4)
         .flatMap(Observable::toList)
         .filter(integers -> integers.size() > 2)
         .toList()
         .subscribe(result -> {
             System.out.printf("%d results %s%n", result.size(), result);
         });
	}

	@Test
	public void listOfList() {
		 Observable.from(Arrays.asList(Group.of("un"), Group.of("deux")))
		 .flatMap(group -> group.attributes().toList())
		 .toList()
		 .subscribe(result -> {
             System.out.printf("results %s", result.toString());
         });
	}
	
	@Test
	public void mapOfMap() {
		 Observable.from(Arrays.asList(Group.of("un"), Group.of("deux")))
		  //on utilise flatMap pour créer les maps intermédiaires car attributes renvoie un observable de pair
		 .flatMap(group -> group.attributes().toMap(pair -> pair.getLeft(), pair -> pair.getRight()))
		 .toMap(groupMap -> groupMap.get("0")) // la map de map
		 .subscribe(result -> {
             System.out.printf("results %s", result.toString());
         });
	}
	
	@RequiredArgsConstructor(staticName = "of") @Getter
	private static class Group {
		private final String name;
		public Observable<Pair> attributes() {
			return Observable.from(Arrays.asList(Pair.of("0",name), Pair.of("2","3"),Pair.of("4","5"), Pair.of("6","7")));
		}
	}
	
	@RequiredArgsConstructor(staticName = "of") @Getter
	private static class Pair {
		private final String left;
		private final String right;
		public String toString() {
			return "["+ left + "," + right + "]";
		}
	}
	
}
