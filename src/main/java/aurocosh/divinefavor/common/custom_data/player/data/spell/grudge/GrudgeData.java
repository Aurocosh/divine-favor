package aurocosh.divinefavor.common.custom_data.player.data.spell.grudge;

import aurocosh.divinefavor.common.entity.common.MobGrudgeRecognizer;
import net.minecraft.entity.monster.IMob;

// The default implementation of the capability. Holds all the logic.
public class GrudgeData {
    private int mobTypeId;

    public GrudgeData() {
        this.mobTypeId = -1;
    }

    public int getMobTypeId() {
        return mobTypeId;
    }

    public void setMobTypeId(int typeId) {
        mobTypeId = typeId;
    }

    public void setGrudge(IMob mob) {
        mobTypeId = MobGrudgeRecognizer.getMobType(mob);
    }

    public boolean hasGrudge(IMob mob) {
        return MobGrudgeRecognizer.checkMobType(mobTypeId, mob);
    }

    public String getMobName() {
        return MobGrudgeRecognizer.getMobName(mobTypeId);
    }
}
