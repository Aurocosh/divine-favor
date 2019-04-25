package aurocosh.divinefavor.common.network.message.client.particles;

import aurocosh.divinefavor.common.item.talismans.spell.sense.SenseBlockPredicate;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Color3f;
import java.util.List;
import java.util.function.Predicate;

public class MessageParticlesHighlightInSphere extends MessageParticlesHighlight {
    public int radius;

    public MessageParticlesHighlightInSphere() {
    }

    public MessageParticlesHighlightInSphere(int particles, int dimensionId, BlockPos position, float minShift, float maxShift, Color3f color3f, SenseBlockPredicate senseBlockPredicate, String blockName, int radius) {
        super(particles, dimensionId, position, minShift, maxShift, color3f, senseBlockPredicate, blockName);
        this.radius = radius;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected List<BlockPos> getHighlighPositions(EntityPlayer player, Predicate<BlockPos> predicate) {
        List<BlockPos> spherePosList = UtilCoordinates.getBlocksInSphere(position, radius);
        return UtilList.select(spherePosList, predicate);
    }
}
