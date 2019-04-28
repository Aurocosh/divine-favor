package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.custom_data.living.LivingData;
import aurocosh.divinefavor.common.custom_data.living.data.soul_theft.SoulTheftData;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionSoulTheft extends ModPotionToggle {
    public static float EXTRA_DAMAGE = 6;

    public PotionSoulTheft() {
        super("soul_theft", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        SoulTheftData theftData = LivingData.get(livingBase).getSoulTheftData();
        theftData.reset();
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (!(source instanceof EntityDamageSource))
            return;
        Entity entityAttacker = source.getTrueSource();
        if (!(entityAttacker instanceof EntityLivingBase))
            return;
        EntityLivingBase attacker = (EntityLivingBase) entityAttacker;
        if (!attacker.isPotionActive(ModCurses.soul_theft))
            return;
        EntityLivingBase victim = event.getEntityLiving();
        if (!(victim instanceof EntityPlayer))
            return;
        SoulTheftData theftData = LivingData.get(attacker).getSoulTheftData();
        if (!theftData.isThief((EntityPlayer) victim))
            return;
        event.setAmount(event.getAmount() + EXTRA_DAMAGE);
    }
}
