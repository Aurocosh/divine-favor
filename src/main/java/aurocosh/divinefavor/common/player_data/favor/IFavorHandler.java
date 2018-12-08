package aurocosh.divinefavor.common.player_data.favor;

import java.util.Map;

public interface IFavorHandler {
    int getFavor(int favorType);
    void setFavor(int favorType, int favorCount);

    int addFavor(int favorType, int favorCount);
    boolean consumeFavor(int favorType, int favorCount);

    Map<Integer,Integer> getAllFavorTypes();
    void setAllFavorTypes(Map<Integer,Integer> favorValues);
}
