package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionStarvation extends ModPotion {

    public PotionStarvation() {
        super("starvation", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        if (world.isRemote)
            return;

        EntityPlayer player = event.getEntityPlayer();
        if (!player.isPotionActive(ModPotions.starvation))
            return;
        if (!player.getHeldItemMainhand().isEmpty())
            return;
        if (!player.getHeldItemOffhand().isEmpty())
            return;

        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Material material = state.getMaterial();
        if (material != Material.GRASS && material != Material.LEAVES)
            return;
        if (!UtilBlock.canBreakBlock(player, world, pos, false))
            return;
        IBlockState stateNew;
        if (material == Material.GRASS)
            stateNew = Blocks.DIRT.getDefaultState();
        else
            stateNew = Blocks.AIR.getDefaultState();
        world.setBlockState(pos, stateNew);

        player.getFoodStats().addStats(ConfigSpells.starvation.foodPerGrass, ConfigSpells.starvation.saturationPerGrass);
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
