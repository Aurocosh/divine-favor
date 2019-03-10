package aurocosh.divinefavor.common.block;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.constants.ConstBlockNames;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockDiviner extends ModBlock {
    public BlockDiviner() {
        super(ConstBlockNames.DIVINER, Material.IRON);
        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setCreativeTab(DivineFavor.TAB_MAIN);
    }
}
