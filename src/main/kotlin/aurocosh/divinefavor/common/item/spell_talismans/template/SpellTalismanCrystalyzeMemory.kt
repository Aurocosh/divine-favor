package aurocosh.divinefavor.common.item.spell_talismans.template

import aurocosh.divinefavor.client.block_ovelay.BlockTemplateRendering
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.memory_drop.ItemMemoryDrop
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.ShiftedPositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.isInvalid
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanCrystalyzeMemory(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    protected val positionPropertyWrapper = ShiftedPositionPropertyWrapper(propertyHandler)

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean = positionPropertyWrapper.shouldRender(context)
    override fun raycastBlock(stack: ItemStack, castType: CastType) = positionPropertyWrapper.shouldRaycastBlock(stack)

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val uuid = player.divinePlayerData.templateData.currentTemplate
        if(uuid.isInvalid())
            return

        val stack = ItemStack(ModItems.memory_drop)
        stack.set(ItemMemoryDrop.uuid, uuid)
        UtilPlayer.addStackToInventoryOrDrop(player, stack)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val player = context.player
        val uuid = player.divinePlayerData.templateData.currentTemplate
        val position = positionPropertyWrapper.getPosition(context)
        BlockTemplateRendering.render(lastEvent, player, uuid, position)
    }
}
