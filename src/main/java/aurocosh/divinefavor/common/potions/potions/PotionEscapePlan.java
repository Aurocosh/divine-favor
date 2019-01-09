package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.player_data.escape_plan.IEscapePlanHandler;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static aurocosh.divinefavor.common.player_data.escape_plan.EscapePlanDataHandler.CAPABILITY_ESCAPE_PLAN;

@Mod.EventBusSubscriber
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

        IEscapePlanHandler planHandler = player.getCapability(CAPABILITY_ESCAPE_PLAN, null);
        assert planHandler != null;
        UtilEntity.teleport(player, planHandler.getGlobalPosition());
        player.removePotionEffect(ModPotions.escape_plan);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
