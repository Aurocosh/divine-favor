package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionStoneFever extends ModPotion {

    public PotionStoneFever() {
        super("stone_fever", true, 0x7FB8A4);
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

        if (!stack.getItem().getToolClasses(stack).contains("pickaxe")) {
            punishPlayer(player);
            return;
        }

        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block != Blocks.STONE && block != Blocks.COBBLESTONE) {
            punishPlayer(player);
            return;
        }

        UtilBlock.removeBlock(player, world, stack, pos, true, true, true);
        stack.damageItem(1, player);
    }

    private static void punishPlayer(EntityPlayer player) {
        player.removePotionEffect(ModPotions.stone_fever);

        player.addPotionEffect(new ModEffect(MobEffects.SLOWNESS, ConfigSpells.stoneFever.slownessDuration).setIsCurse());
        player.addPotionEffect(new ModEffect(MobEffects.BLINDNESS, ConfigSpells.stoneFever.blindnessDuration).setIsCurse());
        player.addPotionEffect(new ModEffect(MobEffects.MINING_FATIGUE, ConfigSpells.stoneFever.fatigueDuration, ConfigSpells.stoneFever.fatigueLevel).setIsCurse());
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
