package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncFavor;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionToadicJump extends ModPotionToggleLimited {
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

        entity.motionY += ConfigSpells.toadicJump.jumpBoost;
        if (entity.world.isRemote)
            return;

        SpiritData spiritData = PlayerData.get(player).getSpiritData();
        ItemSpellTalisman talisman = ModPotions.toadic_jump.getTalisman();
        ModSpirit spirit = talisman.getSpirit();
        if (!spiritData.consumeFavor(spirit.getId(), talisman.getFavorCost()))
            return;
        new MessageSyncFavor(spirit, spiritData).sendTo(player);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
