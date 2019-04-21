package aurocosh.divinefavor.common.block;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlockHorizontal;
import aurocosh.divinefavor.common.constants.ConstMainTabOrder;
import aurocosh.divinefavor.common.item.ItemBlockEnderPumpkin;
import aurocosh.divinefavor.common.item.base.ModItemBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockEnderPumpkin extends ModBlockHorizontal {
    public BlockEnderPumpkin() {
        super("ender_pumpkin", Material.PLANTS, ConstMainTabOrder.OTHER_BLOCKS);
        setHardness(1.0F);
        setResistance(1.0F);
        setSoundType(SoundType.PLANT);
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    @Override
    protected ModItemBlock getItemBlock() {
        return new ItemBlockEnderPumpkin(this, getOrderIndex());
    }
}
