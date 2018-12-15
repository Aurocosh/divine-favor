package aurocosh.divinefavor.common.player_data.focused_fury;

import aurocosh.divinefavor.common.entity.common.MobGrudgeRecognizer;
import net.minecraft.entity.monster.IMob;

// The default implementation of the capability. Holds all the logic.
public class DefaultFocusedFuryHandler implements IFocusedFuryHandler {
    private int mobTypeId;

    public DefaultFocusedFuryHandler() {
        this.mobTypeId = -1;
    }

    @Override
    public int getMobTypeId() {
        return mobTypeId;
    }

    @Override
    public void setMobTypeId(int typeId) {
        mobTypeId = typeId;
    }

    @Override
    public void setFury(IMob mob) {
        mobTypeId = MobGrudgeRecognizer.getMobType(mob);
    }

    @Override
    public boolean hasFury(IMob mob) {
        return MobGrudgeRecognizer.checkMobType(mobTypeId, mob);
    }

    @Override
    public boolean hasFury() {
        return mobTypeId >= 0;
    }

    @Override
    public String getMobName() {
        return MobGrudgeRecognizer.getMobName(mobTypeId);
    }
}
