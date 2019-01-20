package aurocosh.divinefavor.common.custom_data.player.focused_fury;

import net.minecraft.entity.monster.IMob;

public interface IFocusedFuryHandler {
    int getMobTypeId();
    void setMobTypeId(int typeId);

    void setFury(IMob mob);
    boolean hasFury(IMob mob);
    boolean hasFury();
    String getMobName();
}
