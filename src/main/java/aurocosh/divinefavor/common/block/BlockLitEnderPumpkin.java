package aurocosh.divinefavor.common.block;

import aurocosh.divinefavor.common.block.base.ModBlockHorizontal;
import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockLitEnderPumpkin extends ModBlockHorizontal {
    public BlockLitEnderPumpkin() {
        super("lit_ender_pumpkin", Material.PLANTS);
        setHardness(1.0F);
        setResistance(1.0F);
        setSoundType(SoundType.PLANT);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }
}
