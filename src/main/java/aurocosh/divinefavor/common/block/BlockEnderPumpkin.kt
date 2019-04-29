package aurocosh.divinefavor.common.block

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.base.ModBlockHorizontal
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.ItemBlockEnderPumpkin
import aurocosh.divinefavor.common.item.base.ModItemBlock
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material

class BlockEnderPumpkin : ModBlockHorizontal("ender_pumpkin", Material.PLANTS, ConstMainTabOrder.OTHER_BLOCKS) {
    init {
        setHardness(1.0f)
        setResistance(1.0f)
        soundType = SoundType.PLANT
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun getItemBlock(): ModItemBlock? {
        return ItemBlockEnderPumpkin(this, orderIndex)
    }
}
