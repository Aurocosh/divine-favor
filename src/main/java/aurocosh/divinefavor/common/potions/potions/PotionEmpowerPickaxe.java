package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.core.handlers.BlockClickTracker;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionEmpowerPickaxe extends ModPotion {

    public PotionEmpowerPickaxe() {
        super("empower_pickaxe", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if (world.isRemote)
            return;

        if (!event.getEntityPlayer().isPotionActive(ModPotions.empower_pickaxe))
            return;

        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.isEmpty())
            return;

        if (!stack.getItem().getToolClasses(stack).contains("pickaxe"))
            return;

        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (!block.isToolEffective("pickaxe", state))
            return;

        if (BlockClickTracker.wasBlockLeftClicked(player, pos))
            return;

        boolean doSomething = UtilRandom.rollDice(ConfigSpells.empowerPickaxe.instantBreakChance);
        if (!doSomething)
            return;

        UtilBlock.removeBlock(player, world, stack, pos, true, true, true);
        stack.damageItem(ConfigSpells.empowerPickaxe.toolDamage, player);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
