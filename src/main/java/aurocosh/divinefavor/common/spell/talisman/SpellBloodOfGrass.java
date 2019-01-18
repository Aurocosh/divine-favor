package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class SpellBloodOfGrass extends ModSpell {
    public static int EFFECT_RADIUS = 6;
    public static float HEALTH_PER_GRASS = 0.1f;

    @Override
    protected void performActionServer(SpellContext context) {
        World world = context.world;
        EntityPlayer player = context.player;
        ItemStack stack = player.getHeldItem(context.hand);

        List<BlockPos> posList = UtilCoordinates.getBlocksInSphere(player.getPosition(), EFFECT_RADIUS);
        List<BlockPos> plantList = UtilList.filterList(posList, pos -> world.getBlockState(pos).getMaterial() == Material.GRASS);
        for (BlockPos pos : plantList)
            consumeGrass(pos, world, player, stack);
    }

    private void consumeGrass(BlockPos pos, World world, EntityPlayer player, ItemStack stack) {
        if (!UtilBlock.canBreakBlock(player, world, pos, false))
            return;
        world.setBlockState(pos, Blocks.DIRT.getDefaultState());
        player.heal(HEALTH_PER_GRASS);
    }
}
