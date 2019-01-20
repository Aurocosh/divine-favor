package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.custom_data.player.gills.IGillsHandler;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

import static aurocosh.divinefavor.common.custom_data.player.gills.GillsDataHandler.CAPABILITY_GILLS;

public class PotionGills extends ModPotionToggle {
    public PotionGills() {
        super("potion_gills", true, 0x7FB8A4);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
        if (!(entityLivingBase instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) entityLivingBase;
        IGillsHandler gillsHandler= player.getCapability(CAPABILITY_GILLS, null);
        assert gillsHandler != null;

        if (entityLivingBase.isInWater()) {
            gillsHandler.resetTime();
            entityLivingBase.setAir(300);
            return;
        }
        if (!gillsHandler.tick())
            return;

        player.attackEntityFrom(DamageSource.DROWN, 4);
        gillsHandler.delay();
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
