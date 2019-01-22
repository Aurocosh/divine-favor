package aurocosh.divinefavor.common.custom_data.player.data.focused_fury;

import aurocosh.divinefavor.common.entity.common.MobGrudgeRecognizer;
import net.minecraft.entity.monster.IMob;

// The default implementation of the capability. Holds all the logic.
public class FocusedFuryData {
    private int mobTypeId;

    public FocusedFuryData() {
        this.mobTypeId = -1;
    }

    public int getMobTypeId() {
        return mobTypeId;
    }

    public void setMobTypeId(int typeId) {
        mobTypeId = typeId;
    }

    public void setFury(IMob mob) {
        mobTypeId = MobGrudgeRecognizer.getMobType(mob);
    }

    public boolean hasFury(IMob mob) {
        return MobGrudgeRecognizer.checkMobType(mobTypeId, mob);
    }

    public boolean hasFury() {
        return mobTypeId >= 0;
    }

    public String getMobName() {
        return MobGrudgeRecognizer.getMobName(mobTypeId);
    }
}
