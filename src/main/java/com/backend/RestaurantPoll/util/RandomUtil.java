package com.backend.RestaurantPoll.util;

import java.util.HashSet;
import java.util.Random;

/*
 * This class for generate
 * not repetitive numbers
 * to get a menu of different dishes
 */

public class RandomUtil {
    private final HashSet<Integer> input = new HashSet<>();
    private final Random random;
    private final int Count;
    private int genCount = 0;

    public RandomUtil(int in) {
        Count = in;
        random = new Random(in);
    }

    public int generate() {
        if (genCount >= Count)
            return -1;
        int next;
        do {
            next = random.nextInt(Count);
        }
        while (!input.add(next));
        genCount++;
        return next;
    }
}
