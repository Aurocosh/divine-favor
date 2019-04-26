package aurocosh.divinefavor.common.lib.wrapper;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class MaterialPredicate extends PredicateWrapper<BlockPos, Material> {
    public MaterialPredicate(World world, Material material) {
        super(blockPos -> world.getBlockState(blockPos).getMaterial(), value -> value == material);
    }

    public MaterialPredicate(World world, Predicate<Material> predicate) {
        super(blockPos -> world.getBlockState(blockPos).getMaterial(), predicate);
    }
}
