package aurocosh.divinefavor.common.muliblock.validators;

import aurocosh.divinefavor.common.muliblock.patchouli.MultiBlockMatcher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class MultiBlockStateValidator extends StateValidator {
    public final List<ResourceLocation> names;

    public MultiBlockStateValidator(List<ResourceLocation> names) {
        this.names = names;
    }

    @Override
    public boolean isValid(IBlockState state) {
        for (ResourceLocation name : names)
            if (state.getBlock().getRegistryName().equals(name))
                return true;
        return false;
    }

    @Override
    public Object getPatchouliMatcher() {
        return new MultiBlockMatcher(names);
    }
}
