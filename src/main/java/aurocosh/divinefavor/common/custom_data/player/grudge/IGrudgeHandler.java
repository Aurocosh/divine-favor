package aurocosh.divinefavor.common.custom_data.player.grudge;

import net.minecraft.entity.monster.IMob;

public interface IGrudgeHandler {
    int getMobTypeId();
    void setMobTypeId(int typeId);

    void setGrudge(IMob mob);
    boolean hasGrudge(IMob mob);
    String getMobName();
}
