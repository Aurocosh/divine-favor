package aurocosh.divinefavor.common.item.spell_talismans.common_build_properties

import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.talisman.TalismanPropertyHandler
import aurocosh.divinefavor.common.stack_properties.StackPropertyIBlockState
import net.minecraft.init.Blocks

class BlockSelectPropertyHandler(propertyHandler: TalismanPropertyHandler) {
    val selectedBlock: StackPropertyIBlockState = propertyHandler.registerBlockStateProperty("selected_block", Blocks.AIR.defaultState)

    fun preprocess(context: TalismanContext): Boolean {
        if (!context.player.isSneaking)
            return true
        val state = context.world.getBlockState(context.pos)
        selectedBlock.setValue(context.stack, state, context.world.isRemote)
        return false
    }
}