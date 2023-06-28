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
    private final int count;
    private int genCount = 0;

    public RandomUtil(int in) {
        count = in;
        random = new Random(in);
    }

    public int generate() {
        if (genCount >= count) return -1;
        int next;
        do {
            next = random.nextInt(count);
        }
        while (!input.add(next));
        genCount++;
        return next;
    }
}
