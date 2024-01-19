package model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomListGenerator {
    //Static method generating list of random values from given list.
    public static <T> List<T> getRandomValues(List<T> startList, int numberOfWantedRandomValues) {
        List<T> resultList = new ArrayList<>();

        int count = startList.size();
        for (int i = 0; i < numberOfWantedRandomValues && i < startList.size(); i++) {
            int randIndex = ThreadLocalRandom.current().nextInt(count);
            count -= 1;

            resultList.add(startList.get(randIndex));
            startList.set(randIndex, startList.get(count));
        }
        return resultList;
    }
}
