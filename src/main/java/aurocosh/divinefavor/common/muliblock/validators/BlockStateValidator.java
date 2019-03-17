package aurocosh.divinefavor.common.muliblock.validators;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class BlockStateValidator extends StateValidator {
    public final ResourceLocation name;

    public BlockStateValidator(ResourceLocation name) {
        this.name = name;
    }

    @Override
    public boolean isValid(IBlockState state) {
        return state.getBlock().getRegistryName().equals(name);
    }

    @Override
    public Object getPatchouliMatcher() {
        return Block.getBlockFromName(name.toString());
    }
}
