package aurocosh.divinefavor.common.muliblock.serialization;

import aurocosh.divinefavor.common.muliblock.MultiBlockPart;
import aurocosh.divinefavor.common.util.UtilBlockPos;
import com.google.gson.annotations.Expose;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class MultiBlockData {
    @Expose
    public boolean symmetrical;
    @Expose
    public BlockPos basePosition;
    @Expose
    public BlockPos controllerPosition;
    @Expose
    public List<MultiBlockPart> parts;

    public MultiBlockData() {
        symmetrical = true;
        basePosition = UtilBlockPos.ZERO;
        controllerPosition = UtilBlockPos.ZERO;
        parts = new ArrayList<>();
    }

    public MultiBlockData(boolean symmetrical, BlockPos basePosition, BlockPos controllerPosition, List<MultiBlockPart> parts) {
        this.symmetrical = symmetrical;
        this.basePosition = basePosition;
        this.controllerPosition = controllerPosition;
        this.parts = parts;
    }
}
