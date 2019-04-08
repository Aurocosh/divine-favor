package aurocosh.divinefavor.common.lib.distributed_random;

import java.util.HashMap;
import java.util.Map;

public class DistributedRandom {
    private Map<Integer, Double> distributions;
    private double distributionsSum;

    public DistributedRandom() {
        distributions = new HashMap<>();
    }

    public void addNumber(int value, double distribution) {
        if (distributions.get(value) != null)
            distributionsSum -= distributions.get(value);
        distributions.put(value, distribution);
        distributionsSum += distribution;
    }

    public int getRandomNumber() {
        double rand = Math.random();
        double ratio = 1.0f / distributionsSum;
        double tempDist = 0;
        for (Map.Entry<Integer, Double> entry : distributions.entrySet()) {
            tempDist += entry.getValue();
            if (rand / ratio <= tempDist)
                return entry.getKey();
        }
        return 0;
    }

    public void clear() {
        distributions.clear();
        distributionsSum = 0;
    }
}
