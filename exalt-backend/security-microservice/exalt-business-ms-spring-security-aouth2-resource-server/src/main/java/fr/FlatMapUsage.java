package fr;


import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FlatMapUsage {
    public static void main(String[] args) {
        List<List<Integer>> listOfLists = List.of(
                List.of(1,2,3,4,5),
                List.of(6,7,8,9,10,11,12,13),
                List.of(14,15,16),
                List.of(20,21,22,23),
                List.of(24,22,28,2,0,35));
        List<Integer> flattenedList = listOfLists.stream()
                .flatMap(list->list.stream().sorted((a,b)->(b-a))).toList();
        flattenedList.forEach(element -> log.error("element {}",element));

    }
}
