package aurocosh.divinefavor.common.custom_data.player.data.talisman_uses;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;

import java.util.List;

// The default implementation of the capability. Holds all the logic.
public class TalismanUsesData {
    private final TalismanUses[] talismanUses;

    public TalismanUsesData() {
        List<ItemTalisman> talismans = ModMappers.talismans.getValues();
        talismanUses = new TalismanUses[talismans.size()];

        for (int i = 0; i < talismans.size(); i++) {
            ItemTalisman talisman = talismans.get(i);
            int uses = talisman.getStartingSpellUses();
            talismanUses[i] = new TalismanUses(uses, uses);
        }
    }

    public int getUses(int talismanId) {
        return talismanUses[talismanId].getUses();
    }

    public int getMaxUses(int talismanId) {
        return talismanUses[talismanId].getMaxUses();
    }

    public int setUses(int talismanId, int count) {
        TalismanUses usesData = talismanUses[talismanId];
        usesData.setUses(count);
        return usesData.getUses();
    }

    public int setMaxUses(int talismanId, int count) {
        TalismanUses usesData = talismanUses[talismanId];
        usesData.setMaxUses(count);
        return usesData.getMaxUses();
    }

    public int addUses(int talismanId, int count) {
        TalismanUses usesData = talismanUses[talismanId];
        usesData.setUses(count + usesData.getUses());
        return usesData.getUses();
    }

    public boolean consumeUse(int talismanId) {
        TalismanUses usesData = talismanUses[talismanId];
        int spellUses = usesData.getUses();
        if (spellUses == 0)
            return false;
        usesData.setUses(spellUses - 1);
        return true;
    }

    public int refreshUses(int talismanId) {
        TalismanUses usesData = talismanUses[talismanId];
        usesData.setUses(usesData.getMaxUses());
        return usesData.getUses();
    }

    public int addMaxUses(int talismanId, int count) {
        TalismanUses usesData = talismanUses[talismanId];
        usesData.setMaxUses(count + usesData.getMaxUses());
        return usesData.getMaxUses();
    }

    public TalismanUses[] getAllUses() {
        return talismanUses;
    }

    public void setAllUses(TalismanUses[] talismanUses) {
        System.arraycopy(talismanUses, 0, this.talismanUses, 0, talismanUses.length);
    }
}
