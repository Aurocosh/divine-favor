package aurocosh.divinefavor.common.effect.effect;

import aurocosh.divinefavor.common.constants.ConstEffectNames;
import aurocosh.divinefavor.common.core.handlers.BlockClickTracker;
import aurocosh.divinefavor.common.effect.base.ModEffectCharge;
import aurocosh.divinefavor.common.effect.base.ModPotion;
import aurocosh.divinefavor.common.effect.base.ModPotionCharge;
import aurocosh.divinefavor.common.effect.common.ModPotions;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.network.message.MessageSyncFavor;
import aurocosh.divinefavor.common.network.message.MessageSyncPotionCharge;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionWoodenPunch extends ModPotionCharge {

    public PotionWoodenPunch() {
        super(ConstEffectNames.WOODEN_PUNCH, true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if (world.isRemote)
            return;

        EntityPlayer player = event.getEntityPlayer();
        if (!player.isPotionActive(ModPotions.wooden_punch))
            return;

        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (!block.isToolEffective("axe", state))
            return;

        ModEffectCharge effectCharge = (ModEffectCharge) player.getActivePotionEffect(ModPotions.wooden_punch);
        assert effectCharge != null;
        int charges = effectCharge.consumeCharge();
        if(player instanceof EntityPlayerMP) {
            MessageSyncPotionCharge message = new MessageSyncPotionCharge(ModPotions.wooden_punch,charges);
            NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) player);
        }

        ItemStack stack = player.getHeldItemMainhand();
        UtilBlock.removeBlockWithDrops(player, world, stack, pos, false, true);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}