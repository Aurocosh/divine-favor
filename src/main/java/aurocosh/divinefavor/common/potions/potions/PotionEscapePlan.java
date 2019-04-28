package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.spell.escape_plan.EscapePlanData;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionEscapePlan extends ModPotion {

    public PotionEscapePlan() {
        super("escape_plan", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if (player.getHealth() > player.getMaxHealth() / 2)
            return;
        if (!player.isPotionActive(ModPotions.escape_plan))
            return;

        EscapePlanData planData = PlayerData.get(player).getEscapePlanData();
        UtilEntity.teleport(player, planData.getGlobalPosition());
        player.removePotionEffect(ModPotions.escape_plan);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
