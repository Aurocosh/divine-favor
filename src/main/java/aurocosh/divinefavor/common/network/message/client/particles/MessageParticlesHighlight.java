package aurocosh.divinefavor.common.network.message.client.particles;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.item.talismans.spell.sense.BlockHighlighter;
import aurocosh.divinefavor.common.item.talismans.spell.sense.SenseBlockPredicate;
import aurocosh.divinefavor.common.network.base.WrappedClientMessage;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Color3f;
import java.util.List;
import java.util.function.Predicate;

public abstract class MessageParticlesHighlight extends WrappedClientMessage {
    public int particles;
    public int dimensionId;
    public BlockPos position;

    public float minShift;
    public float maxShift;
    public Color3f color3f;

    public SenseBlockPredicate senseBlockPredicate;
    public String blockName;

    public MessageParticlesHighlight() {
    }

    public MessageParticlesHighlight(int particles, int dimensionId, BlockPos position, float minShift, float maxShift, Color3f color3f, SenseBlockPredicate senseBlockPredicate, String blockName) {
        this.particles = particles;
        this.dimensionId = dimensionId;
        this.position = position;
        this.minShift = minShift;
        this.maxShift = maxShift;
        this.color3f = color3f;
        this.senseBlockPredicate = senseBlockPredicate;
        this.blockName = blockName;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        World world = player.world;
        Predicate<BlockPos> predicate = getPredicate(world);
        if (predicate == null)
            return;
        if (dimensionId != world.provider.getDimension())
            return;

        List<BlockPos> positions = getHighlightPositions(player, predicate);
        List<Vec3d> vec3dList = UtilList.process(positions, Vec3d::new);
        BlockHighlighter.spawnParticles(color3f, maxShift, minShift, particles, world, vec3dList);
    }

    @SideOnly(Side.CLIENT)
    protected abstract List<BlockPos> getHighlightPositions(EntityPlayer player, Predicate<BlockPos> predicate);

    @SideOnly(Side.CLIENT)
    private Predicate<BlockPos> getPredicate(World world) {
        switch (senseBlockPredicate) {
            case BLOCK:
                Block block = Block.getBlockFromName(blockName);
                return block == null ? null : (pos -> world.getBlockState(pos).getBlock() == block);
            case WATER:
                return pos -> UtilBlock.isWater(world.getBlockState(pos).getBlock());
            case LAVA:
                return pos -> UtilBlock.isLava(world.getBlockState(pos).getBlock());
            case LIQUID:
                return pos -> UtilBlock.isLiquid(world.getBlockState(pos).getBlock());
            case ORE:
                return pos -> ConfigGeneral.ORE_BLOCKS.contains(world.getBlockState(pos).getBlock());
            case AIR:
                return world::isAirBlock;
        }
        return null;
    }
}
