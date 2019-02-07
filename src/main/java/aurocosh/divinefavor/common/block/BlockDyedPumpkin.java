package aurocosh.divinefavor.common.block;

import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockDyedPumpkin extends ModBlock {
    public BlockDyedPumpkin() {
        super("dyed_pumpkin", Material.PLANTS);
        setHardness(1.0F);
        setResistance(1);
        setSoundType(SoundType.PLANT);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }
}
