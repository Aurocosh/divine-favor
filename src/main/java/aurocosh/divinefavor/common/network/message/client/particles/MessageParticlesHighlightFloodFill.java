package aurocosh.divinefavor.common.network.message.client.particles;

import aurocosh.divinefavor.common.constants.BlockPosConstants;
import aurocosh.divinefavor.common.item.talismans.spell.sense.SenseBlockPredicate;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Color3f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class MessageParticlesHighlightFloodFill extends MessageParticlesHighlight {
    public int floodLimit;
    public int searchLimit;
    public EnumFacing facing;

    public MessageParticlesHighlightFloodFill() {
    }

    public MessageParticlesHighlightFloodFill(int particles, int dimensionId, BlockPos position, float minShift, float maxShift, Color3f color3f, SenseBlockPredicate senseBlockPredicate, String blockName, int floodLimit, int searchLimit, EnumFacing facing) {
        super(particles, dimensionId, position, minShift, maxShift, color3f, senseBlockPredicate, blockName);
        this.floodLimit = floodLimit;
        this.searchLimit = searchLimit;
        this.facing = facing;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected List<BlockPos> getHighlightPositions(EntityPlayer player, Predicate<BlockPos> predicate) {
        BlockPos blockPos = UtilCoordinates.INSTANCE.findPosition(this.position, floodLimit, pos -> player.world.isAirBlock(pos), pos -> pos.offset(facing));
        if(blockPos == null)
            return new ArrayList<>(0);
        return UtilCoordinates.INSTANCE.floodFill(Collections.singletonList(blockPos), BlockPosConstants.INSTANCE.getDIRECT_NEIGHBOURS(), predicate::test, floodLimit);
    }
}
