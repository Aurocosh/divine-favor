package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;

import java.util.List;

// The default implementation of the capability. Holds all the logic.
public class FavorData {
    private final FavorValue[] favorValues;

    public FavorData() {
        List<ModFavor> favors = ModMappers.favors.getValues();
        favorValues = new FavorValue[favors.size()];

        for (int i = 0; i < favors.size(); i++) {
            ModFavor talisman = favors.get(i);
            favorValues[i] = talisman.getDefaultValues();
        }
    }

    public FavorValue get(int favorId) {
        return favorValues[favorId];
    }

    public FavorValue get(ModFavor favor) {
        return favorValues[favor.getId()];
    }

    public FavorValue[] getFavorValues() {
        return favorValues;
    }

    public void setFavorValues(FavorValue[] talismanUses) {
        System.arraycopy(talismanUses, 0, favorValues, 0, talismanUses.length);
    }
}
