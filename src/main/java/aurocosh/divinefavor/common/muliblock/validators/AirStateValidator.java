package aurocosh.divinefavor.common.muliblock.validators;

import com.google.gson.annotations.Expose;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import vazkii.patchouli.common.multiblock.StateMatcher;

public class AirStateValidator extends StateValidator {
    @Expose
    int test;
    @Override
    public boolean isValid(IBlockState state) {
        return state.getBlock() == Blocks.AIR;
    }

    @Override
    public Object getPatchouliMatcher() {
        return StateMatcher.AIR;
    }
}
