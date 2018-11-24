package aurocosh.divinefavor.common.effect.effect;

import aurocosh.divinefavor.common.effect.ModPotionEffects;
import aurocosh.divinefavor.common.lib.LibEffectNames;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilBlockClick;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionEmpowerAxe extends DivineFavorPotion {

    public PotionEmpowerAxe() {
      super(LibEffectNames.EMPOWER_AXE, true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if(world.isRemote)
            return;

        if(!event.getEntityPlayer().isPotionActive(ModPotionEffects.empower_axe))
            return;

        BlockPos pos = event.getPos();
        if(UtilBlockClick.wasBlockLeftClicked(pos))
            return;

        boolean doSomething = UtilRandom.getPercentChance(50);
        if(!doSomething)
            return;

        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        if(stack.isEmpty())
            return;

        if(!stack.getItem().getToolClasses(stack).contains("axe"))
            return;

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if(!block.isToolEffective("axe", state))
            return;

        UtilBlock.removeBlockWithDrops(player, world, stack, pos,true);
        stack.damageItem(5, player);
    }
}
