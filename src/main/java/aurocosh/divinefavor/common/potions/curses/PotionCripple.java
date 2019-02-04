package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.custom_data.living.LivingData;
import aurocosh.divinefavor.common.custom_data.living.data.cripple.CrippleData;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PotionCripple extends ModPotion {
    private final float SLOWNESS_FORCE = 0.2f;

    public PotionCripple() {
        super("cripple", true, 0x7FB8A4);
        setIsCurse(true);
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "b171212d-7cd8-4a62-8665-b2258adf68b8", -SLOWNESS_FORCE, 2);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        CrippleData crippleData = LivingData.get(livingBase).getCrippleData();
        crippleData.resetCureTimer();
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (livingBase.world.isRemote)
            return;
        if (livingBase.isSprinting()) {
            CrippleData crippleData = LivingData.get(livingBase).getCrippleData();
            if (crippleData.cureTick())
                livingBase.removePotionEffect(ModCurses.cripple);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
