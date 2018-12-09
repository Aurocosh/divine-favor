package aurocosh.divinefavor.common.effect.effect;

import aurocosh.divinefavor.common.constants.ConstEffectNames;
import aurocosh.divinefavor.common.effect.common.ModPotionEffects;
import aurocosh.divinefavor.common.effect.base.ModPotion;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.core.handlers.BlockClickTracker;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionEmpowerAxe extends ModPotion {

    public PotionEmpowerAxe() {
        super(ConstEffectNames.EMPOWER_AXE, true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if (world.isRemote)
            return;

        if (!event.getEntityPlayer().isPotionActive(ModPotionEffects.empower_axe))
            return;

        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.isEmpty())
            return;

        if (!stack.getItem().getToolClasses(stack).contains("axe"))
            return;

        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (!block.isToolEffective("axe", state))
            return;

        if (BlockClickTracker.wasBlockLeftClicked(player, pos))
            return;

        boolean doSomething = UtilRandom.getPercentChance(50);
        if (!doSomething)
            return;

        UtilBlock.removeBlockWithDrops(player, world, stack, pos, true);
        stack.damageItem(5, player);
    }
}
