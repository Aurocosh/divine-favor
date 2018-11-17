package aurocosh.divinefavor.block;

import aurocosh.divinefavor.ModBlocks;
import net.minecraft.block.material.Material;

public class BlockIronMedium extends BlockImmaterialMedium {

    public BlockIronMedium() {
        super(ModBlocks.IRON_MEDIUM, Material.IRON);
        setHarvestLevel("pickaxe",1);
    }
}
