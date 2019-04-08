package aurocosh.divinefavor.common.lib;

import java.util.ArrayList;
import java.util.List;

public class DistributedRandomList<T> {
    private DistributedRandom distributedRandom;
    private List<T> values;

    public DistributedRandomList() {
        this.distributedRandom = new DistributedRandom();
        this.values = new ArrayList<>();
    }

    public void add(T value, double distribution) {
        int newIndex = values.size();
        values.add(value);
        distributedRandom.addNumber(newIndex, distribution);
    }

    public void clear() {
        values.clear();
        distributedRandom.clear();
    }

    public T getRandom() {
        if(values.isEmpty())
            return null;
        int index = distributedRandom.getRandomNumber();
        return values.get(index);
    }

    public int size() {
        return values.size();
    }

    public T get(int index) {
        return values.get(index);
    }
}
