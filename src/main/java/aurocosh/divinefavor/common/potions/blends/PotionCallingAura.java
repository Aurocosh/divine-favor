package aurocosh.divinefavor.common.potions.blends;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.calling.CallingAuraData;
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionCallingAura extends ModPotion {
    public PotionCallingAura() {
        super("calling_aura", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        CallingAuraData auraData = PlayerData.get(player).getCallingAuraData();
        auraData.reset();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlaceEvent(BlockEvent.PlaceEvent event) {
        EntityPlayer player = event.getPlayer();
        if (!player.isPotionActive(ModBlendEffects.calling_aura))
            return;
        if (!ModSpirits.loon.isActive())
            return;
        if (event.getPlacedBlock().getBlock() != Blocks.PUMPKIN)
            return;
        World world = event.getWorld();
        if (ModMultiBlocks.snowman.match(world, event.getPos()) == null)
            return;

        CallingAuraData auraData = PlayerData.get(player).getCallingAuraData();
        if (auraData.tryLuck()) {
            player.removePotionEffect(ModBlendEffects.calling_aura);
            player.addPotionEffect(new ModEffect(ModBlessings.manipulative_presence, ConfigPresence.manipulativePresence.duration));
        }
    }
}
