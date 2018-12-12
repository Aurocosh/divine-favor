package aurocosh.divinefavor.common.effect.effect;

import aurocosh.divinefavor.common.constants.ConstEffectNames;
import aurocosh.divinefavor.common.effect.base.ModPotion;
import aurocosh.divinefavor.common.effect.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Set;

@Mod.EventBusSubscriber
public class PotionStoneFever extends ModPotion {

    public PotionStoneFever() {
        super(ConstEffectNames.STONE_FEVER, true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if (world.isRemote)
            return;

        if (!event.getEntityPlayer().isPotionActive(ModPotions.stone_fever))
            return;

        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.isEmpty())
            return;

        if (!stack.getItem().getToolClasses(stack).contains("pickaxe")){
            punishPlayer(player);
            return;
        }

        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if(block != Blocks.STONE && block != Blocks.COBBLESTONE){
            punishPlayer(player);
            return;
        }

        UtilBlock.removeBlockWithDrops(player, world, stack, pos, true);
        stack.damageItem(1, player);
    }

    private static void punishPlayer(EntityPlayer player){
        player.removePotionEffect(ModPotions.stone_fever);

        player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1200));
        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200));
        player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 3600, 5));
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
