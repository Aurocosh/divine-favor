package aurocosh.divinefavor.common.player_data.grudge;

import aurocosh.divinefavor.common.entity.common.MobGrudgeRecognizer;
import net.minecraft.entity.monster.IMob;

// The default implementation of the capability. Holds all the logic.
public class DefaultGrudgeHandler implements IGrudgeHandler {
    private int mobTypeId;

    public DefaultGrudgeHandler() {
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
    public void setGrudge(IMob mob) {
        mobTypeId = MobGrudgeRecognizer.getMobType(mob);
    }

    @Override
    public boolean hasGrudge(IMob mob) {
        return MobGrudgeRecognizer.checkMobType(mobTypeId, mob);
    }

    @Override
    public String getMobName() {
        return MobGrudgeRecognizer.getMobName(mobTypeId);
    }
}
