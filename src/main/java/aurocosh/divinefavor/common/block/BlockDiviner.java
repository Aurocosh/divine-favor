package aurocosh.divinefavor.common.block;

import aurocosh.divinefavor.common.block.base.IDivineFavorBlock;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.lib.LibBlockNames;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import vazkii.arl.block.BlockMod;

public class BlockDiviner extends BlockMod implements IDivineFavorBlock {

    public BlockDiviner() {
        super(LibBlockNames.DIVINER, Material.IRON);
        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public EnumRarity getBlockRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }
}
