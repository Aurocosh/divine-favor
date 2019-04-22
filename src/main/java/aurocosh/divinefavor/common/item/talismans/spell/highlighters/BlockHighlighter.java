package aurocosh.divinefavor.common.item.talismans.spell.highlighters;

import aurocosh.divinefavor.common.particles.ModParticles;
import aurocosh.divinefavor.common.particles.particles.OreHighlightParticle;
import aurocosh.divinefavor.common.util.UtilBlockPos;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Color3f;
import java.util.List;
import java.util.function.Predicate;

@SideOnly(Side.CLIENT)
public class BlockHighlighter {
    private static final int MAX_BLOCKS_HIGHLIGHTED = 500;

    public static void highlightOre(World world, BlockPos origin, int radius, float maxShift, float minShift, int particles, Color3f color3f, Predicate<BlockPos> predicate) {
        List<BlockPos> spherePosList = UtilCoordinates.getBlocksInSphere(origin, radius);
        List<BlockPos> highlightedPosList = UtilList.select(spherePosList, predicate);
        if (highlightedPosList.size() > MAX_BLOCKS_HIGHLIGHTED)
            highlightedPosList = UtilRandom.selectRandom(highlightedPosList, MAX_BLOCKS_HIGHLIGHTED);
        spawnParticles(color3f, maxShift, minShift, particles, world, highlightedPosList);
    }

    private static void spawnParticles(Color3f color3f, float maxShift, float minShift, int particles, World world, List<BlockPos> highlightedPosList) {
        for (BlockPos pos : highlightedPosList) {
            for (int i = 0; i < particles; i++) {
                Vec3d directionShift = UtilRandom.nextDirection();
                Vec3d shift = directionShift.scale(UtilRandom.nextFloat(minShift, maxShift));
                Vec3d position = UtilBlockPos.add(pos, shift);

                ModParticles.noDepth.createParticle(() -> new OreHighlightParticle(world, position, color3f));
            }
        }
    }
}
