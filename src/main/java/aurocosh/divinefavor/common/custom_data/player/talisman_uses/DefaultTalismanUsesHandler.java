package aurocosh.divinefavor.common.custom_data.player.talisman_uses;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// The default implementation of the capability. Holds all the logic.
public class DefaultTalismanUsesHandler implements ITalismanUsesHandler {
    private Map<Integer, TalismanUsesData> talismanUses = new HashMap<>();

    @Override
    public int getUses(int talismanId) {
        return talismanUses.computeIfAbsent(talismanId, this::getDefaultData).getSpellUses();
    }

    @Override
    public int getMaxUses(int talismanId) {
        return talismanUses.computeIfAbsent(talismanId, this::getDefaultData).getMaxSpellUses();
    }

    @Override
    public int setUses(int talismanId, int count) {
        TalismanUsesData usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        usesData.setSpellUses(count);
        return usesData.getSpellUses();
    }

    @Override
    public int setMaxUses(int talismanId, int count) {
        TalismanUsesData usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        usesData.setMaxSpellUses(count);
        return usesData.getMaxSpellUses();
    }

    @Override
    public int addUses(int talismanId, int count) {
        TalismanUsesData usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        usesData.setSpellUses(count + usesData.getSpellUses());
        return usesData.getSpellUses();
    }

    @Override
    public boolean consumeUse(int talismanId) {
        TalismanUsesData usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        int spellUses = usesData.getSpellUses();
        if (spellUses == 0)
            return false;
        usesData.setSpellUses(spellUses - 1);
        return true;
    }

    @Override
    public int refreshUses(int talismanId) {
        TalismanUsesData usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        usesData.setSpellUses(usesData.getMaxSpellUses());
        return usesData.getSpellUses();
    }

    @Override
    public int addMaxUses(int talismanId, int count) {
        TalismanUsesData usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        usesData.setMaxSpellUses(count + usesData.getMaxSpellUses());
        return usesData.getMaxSpellUses();
    }

    @Override
    public Map<Integer, TalismanUsesData> getAllUses() {
        return Collections.unmodifiableMap(talismanUses);
    }

    @Override
    public void setAllUses(Map<Integer, TalismanUsesData> spellUses) {
        this.talismanUses = new HashMap<>(spellUses);
    }

    private TalismanUsesData getDefaultData(int talismanId) {
        ItemTalisman talisman = ModMappers.talismans.getIdMap().get(talismanId);
        int talismanUses = 3;
        if (talisman != null)
            talismanUses = talisman.getStartingSpellUses();
        return new TalismanUsesData(talismanUses, talismanUses);
    }
}
