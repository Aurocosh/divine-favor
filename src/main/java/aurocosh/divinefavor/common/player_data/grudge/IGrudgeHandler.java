package aurocosh.divinefavor.common.player_data.grudge;

import net.minecraft.entity.monster.IMob;

public interface IGrudgeHandler {
    int getMobTypeId();
    void setMobTypeId(int typeId);

    void setGrudge(IMob mob);
    boolean hasGrudge(IMob mob);
    String getMobName();
}
