package aurocosh.divinefavor.common.item.spell_talismans.common_build_properties

import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyIBlockState
import net.minecraft.init.Blocks

class BlockSelectPropertyWrapper(propertyHandler: StackPropertyHandler) {
    val selectedBlock: StackPropertyIBlockState = propertyHandler.registerBlockStateProperty("selected_block", Blocks.STONE.defaultState)

    fun preprocess(context: TalismanContext): Boolean {
        if (!context.player.isSneaking)
            return true
        if(context.castType != CastType.RightCast && context.castType != CastType.UseCast)
            return true
        val state = context.world.getBlockState(context.pos)
        selectedBlock.setValue(context.stack, state, context.world.isRemote)
        return false
    }
}