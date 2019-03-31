package aurocosh.divinefavor.common.potions.blends;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.mineral.MineralAuraData;
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

@Mod.EventBusSubscriber
public class PotionMineralAura extends ModPotion {
    public PotionMineralAura() {
        super("mineral_aura", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        MineralAuraData auraData = PlayerData.get(player).getMineralAuraData();
        auraData.reset();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if (!player.isPotionActive(ModBlendEffects.mineral_aura))
            return;
        IBlockState state = event.getState();
        if (state.getMaterial() != Material.ROCK)
            return;
        if (!ModSpirits.romol.isActive())
            return;
        MineralAuraData auraData = PlayerData.get(player).getMineralAuraData();
        if (auraData.count()) {
            player.removePotionEffect(ModBlendEffects.mineral_aura);
            player.addPotionEffect(new ModEffect(ModBlessings.industrious_presence, ConfigPresence.industriousPresence.duration));
        }
    }
}
