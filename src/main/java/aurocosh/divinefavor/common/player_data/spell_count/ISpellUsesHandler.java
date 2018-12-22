package aurocosh.divinefavor.common.player_data.spell_count;

import java.util.Map;

public interface ISpellUsesHandler {
    int getSpellUses(int talismanId);
    int getMaxSpellUses(int talismanId);

    int setSpellUses(int talismanId, int count);
    int setMaxSpellUses(int talismanId, int count);

    int addSpellUses(int talismanId, int count);
    boolean consumeSpellUse(int talismanId);

    int addMaxSpellUses(int talismanId, int count);

    Map<Integer, SpellUsesData> getAllSpellUses();
    void setAllSpellUses(Map<Integer, SpellUsesData> maxSpellCounts);
}
