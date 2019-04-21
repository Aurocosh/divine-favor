package aurocosh.divinefavor.common.muliblock;
import aurocosh.divinefavor.common.muliblock.validators.StateValidator;
import com.google.gson.annotations.Expose;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiBlockPart {
    @Expose
    public final StateValidator validator;
    @Expose
    public final List<BlockPos> positions;

    public MultiBlockPart(StateValidator validator, List<BlockPos> positions) {
        this.validator = validator;
        this.positions = Collections.unmodifiableList(new ArrayList<>(positions));
    }

    public boolean isAllValid(World world, BlockPos controller){
        for (BlockPos position : positions) {
            BlockPos blockPosition = position.add(controller);
            IBlockState state = world.getBlockState(blockPosition);
            if(!validator.isValid(state))
                return false;
        }
        return true;
    }
}
