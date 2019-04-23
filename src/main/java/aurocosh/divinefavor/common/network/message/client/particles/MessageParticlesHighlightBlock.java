package aurocosh.divinefavor.common.network.message.client.particles;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.item.talismans.spell.highlighters.BlockHighlighter;
import aurocosh.divinefavor.common.item.talismans.spell.highlighters.HighlightPredicate;
import aurocosh.divinefavor.common.network.base.WrappedClientMessage;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Color3f;
import java.util.function.Predicate;

public class MessageParticlesHighlightBlock extends WrappedClientMessage {
    public int radius;
    public int particles;
    public int dimensionId;
    public BlockPos position;

    public float minShift;
    public float maxShift;
    public Color3f color3f;

    public int highlightPredicate;
    public String blockName;

    public MessageParticlesHighlightBlock() {
    }

    public MessageParticlesHighlightBlock(int radius, int particles, int dimensionId, BlockPos position, float minShift, float maxShift, Color3f color3f, HighlightPredicate highlightPredicate, String blockName) {
        this.radius = radius;
        this.particles = particles;
        this.dimensionId = dimensionId;
        this.position = position;
        this.minShift = minShift;
        this.maxShift = maxShift;
        this.color3f = color3f;
        this.highlightPredicate = highlightPredicate.getIndex();
        this.blockName = blockName;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        World world = DivineFavor.proxy.getClientPlayer().world;
        Predicate<BlockPos> predicate = getPredicate(world);
        if (predicate == null)
            return;
        if (dimensionId == world.provider.getDimension())
            BlockHighlighter.highlightOre(world, position, radius, maxShift, minShift, particles, color3f, predicate);
    }

    private Predicate<BlockPos> getPredicate(World world) {
        HighlightPredicate highlightPredicate = HighlightPredicate.INDEXER.get(this.highlightPredicate);
        switch (highlightPredicate) {
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
        }
        return null;
    }
}
