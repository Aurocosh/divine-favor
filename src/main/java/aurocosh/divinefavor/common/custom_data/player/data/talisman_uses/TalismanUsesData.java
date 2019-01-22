package aurocosh.divinefavor.common.custom_data.player.data.talisman_uses;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSpellUses;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// The default implementation of the capability. Holds all the logic.
public class TalismanUsesData {
    private Map<Integer, TalismanUses> talismanUses = new HashMap<>();

    public int getUses(int talismanId) {
        return talismanUses.computeIfAbsent(talismanId, this::getDefaultData).getUses();
    }

    public int getMaxUses(int talismanId) {
        return talismanUses.computeIfAbsent(talismanId, this::getDefaultData).getMaxUses();
    }

    public int setUses(int talismanId, int count) {
        TalismanUses usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        usesData.setUses(count);
        return usesData.getUses();
    }

    public int setMaxUses(int talismanId, int count) {
        TalismanUses usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        usesData.setMaxUses(count);
        return usesData.getMaxUses();
    }

    public int addUses(int talismanId, int count) {
        TalismanUses usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        usesData.setUses(count + usesData.getUses());
        return usesData.getUses();
    }

    public boolean consumeUse(int talismanId) {
        TalismanUses usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        int spellUses = usesData.getUses();
        if (spellUses == 0)
            return false;
        usesData.setUses(spellUses - 1);
        return true;
    }

    public int refreshUses(int talismanId) {
        TalismanUses usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        usesData.setUses(usesData.getMaxUses());
        return usesData.getUses();
    }

    public int addMaxUses(int talismanId, int count) {
        TalismanUses usesData = talismanUses.computeIfAbsent(talismanId, this::getDefaultData);
        usesData.setMaxUses(count + usesData.getMaxUses());
        return usesData.getMaxUses();
    }

    public Map<Integer, TalismanUses> getAllUses() {
        return Collections.unmodifiableMap(talismanUses);
    }

    public void setAllUses(Map<Integer, TalismanUses> spellUses) {
        this.talismanUses = new HashMap<>(spellUses);
    }

    private TalismanUses getDefaultData(int talismanId) {
        ItemTalisman talisman = ModMappers.talismans.getIdMap().get(talismanId);
        int talismanUses = 3;
        if (talisman != null)
            talismanUses = talisman.getStartingSpellUses();
        return new TalismanUses(talismanUses, talismanUses);
    }
}
