package aurocosh.divinefavor.common.player_data.spell_count;

import aurocosh.divinefavor.common.item.talismans.ItemTalisman;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// The default implementation of the capability. Holds all the logic.
public class DefaultSpellUsesHandler implements ISpellUsesHandler {
    private Map<Integer, SpellUsesData> spellUses = new HashMap<>();

    @Override
    public int getSpellUses(int talismanId) {
        return spellUses.computeIfAbsent(talismanId, k -> getDefaultData(k)).getSpellUses();
    }

    @Override
    public int getMaxSpellUses(int talismanId) {
        return spellUses.computeIfAbsent(talismanId, k -> getDefaultData(k)).getMaxSpellUses();
    }

    @Override
    public int setSpellUses(int talismanId, int count) {
        SpellUsesData usesData = spellUses.computeIfAbsent(talismanId, k -> getDefaultData(k));
        usesData.setSpellUses(count);
        return usesData.getSpellUses();
    }

    @Override
    public int setMaxSpellUses(int talismanId, int count) {
        SpellUsesData usesData = spellUses.computeIfAbsent(talismanId, k -> getDefaultData(k));
        usesData.setMaxSpellUses(count);
        return usesData.getMaxSpellUses();
    }

    @Override
    public int addSpellUses(int talismanId, int count) {
        SpellUsesData usesData = spellUses.computeIfAbsent(talismanId, k -> getDefaultData(k));
        usesData.setSpellUses(count + usesData.getSpellUses());
        return usesData.getSpellUses();
    }

    @Override
    public boolean consumeSpellUse(int talismanId) {
        SpellUsesData usesData = spellUses.computeIfAbsent(talismanId, k -> getDefaultData(k));
        int spellUses = usesData.getSpellUses();
        if (spellUses == 0)
            return false;
        usesData.setSpellUses(spellUses - 1);
        return true;
    }

    @Override
    public int addMaxSpellUses(int talismanId, int count) {
        SpellUsesData usesData = spellUses.computeIfAbsent(talismanId, k -> getDefaultData(k));
        usesData.setMaxSpellUses(count + usesData.getMaxSpellUses());
        return usesData.getMaxSpellUses();
    }

    @Override
    public Map<Integer, SpellUsesData> getAllSpellUses() {
        return Collections.unmodifiableMap(spellUses);
    }

    @Override
    public void setAllSpellUses(Map<Integer, SpellUsesData> spellUses) {
        this.spellUses = new HashMap<>(spellUses);
    }

    private SpellUsesData getDefaultData(int talismanId) {
        ItemTalisman talisman = ModMappers.talismans.getIdMap().get(talismanId);
        int spellUses = 3;
        if (talisman != null)
            spellUses = talisman.getStartingSpellUses();
        return new SpellUsesData(spellUses, spellUses);
    }
}
