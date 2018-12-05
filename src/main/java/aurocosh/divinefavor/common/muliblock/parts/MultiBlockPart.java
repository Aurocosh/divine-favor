package aurocosh.divinefavor.common.muliblock.parts;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiBlockPart {
    public final StateValidator validator;
    public final List<Vector3i> positions;

    public MultiBlockPart(StateValidator validator, List<Vector3i> positions) {
        this.validator = validator;
        this.positions = Collections.unmodifiableList(new ArrayList<>(positions));
    }

    public boolean isAllValid(World world, Vector3i controller){
        for (Vector3i position : positions) {
            Vector3i blockPosition = position.add(controller);
            IBlockState state = world.getBlockState(blockPosition.toBlockPos());
            if(!validator.isValid(state))
                return false;
        }
        return true;
    }
}
