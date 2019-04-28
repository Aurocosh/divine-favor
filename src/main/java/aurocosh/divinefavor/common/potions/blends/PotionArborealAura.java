package aurocosh.divinefavor.common.potions.blends;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.arboreal.ArborealAuraData;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionArborealAura extends ModPotion {
    public PotionArborealAura() {
        super("arboreal_aura", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        ArborealAuraData auraData = PlayerData.get(player).getArborealAuraData();
        auraData.reset();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if (!player.isPotionActive(ModBlendEffects.arboreal_aura))
            return;
        IBlockState state = event.getState();
        if (state.getMaterial() != Material.WOOD)
            return;
        if (!ModSpirits.timber.isActive())
            return;
        ArborealAuraData auraData = PlayerData.get(player).getArborealAuraData();
        if (auraData.count()) {
            player.removePotionEffect(ModBlendEffects.arboreal_aura);
            player.addPotionEffect(new ModEffect(ModBlessings.towering_presence, ConfigPresence.toweringPresence.duration));
        }
    }
}
