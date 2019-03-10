package aurocosh.divinefavor.common.block;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlockHorizontal;
import aurocosh.divinefavor.common.item.ItemBlockEnderPumpkin;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class BlockEnderPumpkin extends ModBlockHorizontal {
    public BlockEnderPumpkin() {
        super("ender_pumpkin", Material.PLANTS);
        setHardness(1.0F);
        setResistance(1.0F);
        setSoundType(SoundType.PLANT);
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    @Override
    protected ItemBlock getItemBlock() {
        return new ItemBlockEnderPumpkin(this);
    }
}
