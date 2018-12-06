package aurocosh.divinefavor.common.muliblock.parts;

import com.google.gson.annotations.Expose;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import vazkii.patchouli.api.PatchouliAPI;

public class AirStateValidator extends StateValidator {
    @Expose
    int test;
    @Override
    public boolean isValid(IBlockState state) {
        return state.getBlock() == Blocks.AIR;
    }

    @Override
    public Object getPatchouliMatcher() {
        return PatchouliAPI.instance.anyMatcher();
    }
}
