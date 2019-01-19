package aurocosh.divinefavor.common.item.talismans;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockReed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class TalismanHarvest extends ItemTalisman {
    public static int EFFECT_RADIUS = 12;
    private static final int USES = 10;

    public static final ImmutableSet<Material> ALLOWED_MATERIALS =
            ImmutableSet.of(net.minecraft.block.material.Material.WEB,
                    net.minecraft.block.material.Material.LEAVES,
                    net.minecraft.block.material.Material.VINE,
                    net.minecraft.block.material.Material.GOURD,
                    net.minecraft.block.material.Material.CACTUS);

    public TalismanHarvest() {
        super("harvest", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;
        EntityPlayer player = context.player;
        ItemStack stack = player.getHeldItem(context.hand);

        List<BlockPos> posList = UtilCoordinates.getBlocksInSphere(player.getPosition(), EFFECT_RADIUS);
        List<BlockPos> plantList = UtilList.filterList(posList, element -> isValidCrop(element, world));
        for (BlockPos pos : plantList)
            UtilBlock.removeBlockWithDrops(player, world, stack, pos, false, false);
    }

    private boolean isValidCrop(BlockPos pos, World world) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if(block instanceof BlockReed && !(world.getBlockState(pos.down()).getBlock() instanceof BlockReed))
            return true;
        if(block instanceof BlockCrops && ((BlockCrops) block).isMaxAge(state))
            return true;
        if(block instanceof BlockNetherWart && state.getValue(BlockNetherWart.AGE) == 3)
            return true;
        return ALLOWED_MATERIALS.contains(state.getMaterial());
    }
}
