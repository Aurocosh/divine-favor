package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class SpellTalismanBloodOfGrass extends ItemSpellTalisman {
    public static int USES = 6;
    public static int EFFECT_RADIUS = 6;
    public static float HEALTH_PER_GRASS = 0.1f;

    public SpellTalismanBloodOfGrass(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;
        EntityPlayer player = context.player;

        List<BlockPos> posList = UtilCoordinates.getBlocksInSphere(player.getPosition(), EFFECT_RADIUS);
        List<BlockPos> plantList = UtilList.filterList(posList, pos -> world.getBlockState(pos).getMaterial() == Material.GRASS);
        for (BlockPos pos : plantList)
            consumeGrass(pos, world, player);
    }

    private void consumeGrass(BlockPos pos, World world, EntityPlayer player) {
        if (!UtilBlock.canBreakBlock(player, world, pos, false))
            return;
        world.setBlockState(pos, Blocks.DIRT.getDefaultState());
        player.heal(HEALTH_PER_GRASS);
    }
}
