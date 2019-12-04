package aurocosh.divinefavor.common.block

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.base.ModBlock
import aurocosh.divinefavor.common.constants.ConstBlockNames
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.talisman.ITalismanToolContainer
import aurocosh.divinefavor.common.item.talisman_tools.BookPropertyWrapper.Companion.isToolSealed
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isPropertySet
import aurocosh.divinefavor.common.lib.extensions.set
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class SealingStone(name: String) : ModBlock(ConstBlockNames.SEALING_STONE + "_" + name, Material.ROCK, ConstMainTabOrder.OTHER_BLOCKS) {

    init {
        setHardness(2.0f)
        setResistance(10.0f)
        soundType = SoundType.STONE
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {

        for (stack in player.heldEquipment) {
            if(stack.item is ITalismanToolContainer)
                stack.set(isToolSealed, !stack.get(isToolSealed))
        }

        return true
    }
}
