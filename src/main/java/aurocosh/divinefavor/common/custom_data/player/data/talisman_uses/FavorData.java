package aurocosh.divinefavor.common.custom_data.player.data.talisman_uses;

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

    public int getFavor(int favorId) {
        return favorValues[favorId].getFavor();
    }

    public int setFavor(int favorId, int count) {
        FavorValue favorValue = favorValues[favorId];
        favorValue.setFavor(count);
        return favorValue.getFavor();
    }

    public int getMaxLimit(int favorId) {
        return favorValues[favorId].getMaxLimit();
    }

    public int setMaxLimit(int favorId, int count) {
        FavorValue favorValue = favorValues[favorId];
        favorValue.setMaxLimit(count);
        return favorValue.getFavor();
    }

    public int addFavor(int favorId, int count) {
        FavorValue favorValue = favorValues[favorId];
        favorValue.setFavor(count + favorValue.getFavor());
        return favorValue.getFavor();
    }

    public boolean consumeFavor(int favorId) {
        FavorValue favorValue = favorValues[favorId];
        int spellUses = favorValue.getFavor();
        if (spellUses == 0)
            return false;
        favorValue.setFavor(spellUses - 1);
        return true;
    }

    public int refreshFavor(int favorId) {
        FavorValue favorValue = favorValues[favorId];
        favorValue.setFavor(favorValue.getFavor());
        return favorValue.getFavor();
    }

    public int addMinFavor(int favorId, int count) {
        FavorValue usesData = favorValues[favorId];
        usesData.setMinLimit(count + usesData.getMinLimit());
        return usesData.getFavor();
    }

    public int addMaxFavor(int favorId, int count) {
        FavorValue usesData = favorValues[favorId];
        usesData.setMaxLimit(count + usesData.getMaxLimit());
        return usesData.getFavor();
    }

    public FavorValue[] getFavorValues() {
        return favorValues;
    }

    public void setFavorValues(FavorValue[] talismanUses) {
        System.arraycopy(talismanUses, 0, favorValues, 0, talismanUses.length);
    }
}
