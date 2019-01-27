package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.talisman_uses.FavorData;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSpellUses;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionToadicJump extends ModPotionToggleLimited {
    private static float JUMP_BOOST = 0.3f;

    public PotionToadicJump() {
        super("toadic_jump", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        Entity entity = event.getEntityLiving();
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if (!player.isPotionActive(ModPotions.toadic_jump))
            return;

        entity.motionY += JUMP_BOOST;
        if(entity.world.isRemote)
            return;

        FavorData usesData = PlayerData.get(player).getFavorData();
        ItemSpellTalisman talisman = ModPotions.toadic_jump.getTalisman();
        if (!usesData.consumeFavor(talisman.getFavorId()))
            return;
        new MessageSyncSpellUses(talisman.getFavorId(), usesData).sendTo(player);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
