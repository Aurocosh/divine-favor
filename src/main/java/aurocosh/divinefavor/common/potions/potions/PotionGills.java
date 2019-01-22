package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.gills.GillsData;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class PotionGills extends ModPotionToggle {
    public PotionGills() {
        super("potion_gills", true, 0x7FB8A4);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (!(livingBase instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) livingBase;
        GillsData gillsData = PlayerData.get(player).getGillsData();
        if (livingBase.isInWater()) {
            gillsData.resetTime();
            livingBase.setAir(300);
            return;
        }
        if (!gillsData.tick())
            return;

        player.attackEntityFrom(DamageSource.DROWN, 4);
        gillsData.delay();
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
