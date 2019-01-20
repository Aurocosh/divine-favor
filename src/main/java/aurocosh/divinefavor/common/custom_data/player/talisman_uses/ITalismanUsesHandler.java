package aurocosh.divinefavor.common.custom_data.player.talisman_uses;

import java.util.Map;

public interface ITalismanUsesHandler {
    int getUses(int talismanId);
    int getMaxUses(int talismanId);

    int setUses(int talismanId, int count);
    int setMaxUses(int talismanId, int count);

    int addUses(int talismanId, int count);
    int addMaxUses(int talismanId, int count);

    boolean consumeUse(int talismanId);

    int refreshUses(int talismanId);

    Map<Integer, TalismanUsesData> getAllUses();
    void setAllUses(Map<Integer, TalismanUsesData> maxSpellCounts);
}
