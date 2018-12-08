package aurocosh.divinefavor.common.player_data.favor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// The default implementation of the capability. Holds all the logic.
public class DefaultFavorHandler implements IFavorHandler {
    private Map<Integer, Integer> favorValues = new HashMap<>();

    @Override
    public int getFavor(int favorType) {
        return favorValues.computeIfAbsent(favorType,integer -> 0);
    }

    @Override
    public void setFavor(int favorType, int favorCount) {
        favorValues.put(favorType,favorCount);
    }

    @Override
    public int addFavor(int favorType, int favorCount) {
        return favorValues.compute(favorType,(k, v) -> v + favorCount);
    }

    @Override
    public boolean consumeFavor(int favorType, int favorCount) {
        int currentFavor = getFavor(favorType);
        if(currentFavor < favorCount)
            return false;
        addFavor(favorType,-favorCount);
        return true;
    }

    @Override
    public Map<Integer, Integer> getAllFavorTypes() {
        return Collections.unmodifiableMap(favorValues);
    }

    @Override
    public void setAllFavorTypes(Map<Integer, Integer> favorValues) {
        this.favorValues = new HashMap<>(favorValues);
    }
}
