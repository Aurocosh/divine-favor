package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionArmorOfPacifist extends ModPotionToggle {
    public PotionArmorOfPacifist() {
        super("armor_of_pacifist", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        protectPlayer(event);
        preventAttackFromPlayer(event);
    }

    private static void protectPlayer(LivingDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if(!player.isPotionActive(ModPotions.armor_of_pacifist))
            return;
        DamageSource source = event.getSource();
        if (source == DamageSource.CACTUS || canProtectFromEntity(source))
            event.setCanceled(true);
    }

    private static boolean canProtectFromEntity(DamageSource source){
        if (!(source instanceof EntityDamageSource))
            return false;
        Entity attacker = source.getTrueSource();
        if(attacker instanceof AbstractSkeleton)
            return false;
        if(attacker instanceof EntityWitch)
            return false;
        return attacker instanceof IMob && !(attacker instanceof EntityDragon);
    }

    private static void preventAttackFromPlayer(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (!(source instanceof EntityDamageSource))
            return;
        Entity entity = source.getTrueSource();
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if(!player.isPotionActive(ModPotions.armor_of_pacifist))
            return;
        event.setCanceled(true);
    }
}
