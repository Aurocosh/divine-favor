package aurocosh.divinefavor.common.muliblock.patchouli;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IStateMatcher;

import java.util.List;
import java.util.function.Predicate;

public class MultiBlockMatcher implements IStateMatcher {
    private final List<ResourceLocation> names;

    public MultiBlockMatcher(List<ResourceLocation> names) {
        this.names = names;
    }

    @Override
    public IBlockState getDisplayedState() {
        ResourceLocation location = names.get(0);
        Block block = Block.getBlockFromName(location.toString());
        return block.getDefaultState();
    }

    @Override
    public Predicate<IBlockState> getStatePredicate() {
        return this::checkBlockState;
    }

    private boolean checkBlockState(IBlockState iBlockState) {
        for (ResourceLocation name : names)
            if (iBlockState.getBlock().getRegistryName().equals(name))
                return true;
        return false;
    }
}
