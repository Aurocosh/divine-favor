package aurocosh.divinefavor.common.block;

import aurocosh.divinefavor.common.block.base.IDivineFavorBlock;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.lib.LibBlockNames;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import vazkii.arl.block.BlockMod;

public class BlockFastFurnace extends BlockMod implements IDivineFavorBlock {
    public BlockFastFurnace() {
        super(LibBlockNames.FAST_FURNACE, Material.IRON);
        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }
}
