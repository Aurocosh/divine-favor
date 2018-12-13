package aurocosh.divinefavor.common.potions.effect;

import aurocosh.divinefavor.common.constants.ConstEffectNames;
import aurocosh.divinefavor.common.potions.base.ModEffectCharge;
import aurocosh.divinefavor.common.potions.base.ModPotionCharge;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.network.message.MessageSyncPotionCharge;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionCrushingPalm extends ModPotionCharge {

    public PotionCrushingPalm() {
        super(ConstEffectNames.CRUSHING_PALM, true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if (world.isRemote)
            return;

        EntityPlayer player = event.getEntityPlayer();
        if (!player.isPotionActive(ModPotions.crushing_palm))
            return;

        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (!block.isToolEffective("pickaxe", state))
            return;

        ModEffectCharge effectCharge = (ModEffectCharge) player.getActivePotionEffect(ModPotions.crushing_palm);
        assert effectCharge != null;
        int charges = effectCharge.consumeCharge();
        if(player instanceof EntityPlayerMP) {
            MessageSyncPotionCharge message = new MessageSyncPotionCharge(ModPotions.crushing_palm,charges);
            NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) player);
        }

        ItemStack stack = player.getHeldItemMainhand();
        UtilBlock.removeBlockWithDrops(player, world, stack, pos, false,true);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
