package aurocosh.divinefavor.common.item

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.EmptyConst.emptyUUID
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isPropertySet
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncSelectedTemplate
import aurocosh.divinefavor.common.stack_properties.IPropertyAccessor
import aurocosh.divinefavor.common.stack_properties.PropertyGenerator
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.util.UtilItem.actionResult
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

open class ItemMemoryDrop(name: String, texturePath: String, orderIndex: Int = 0) : ModItem(name, texturePath, orderIndex), ITemplateContainer {
    protected val propertyHandler: StackPropertyHandler = StackPropertyHandler(name)
    val properties: IPropertyAccessor = propertyHandler

    init {
        propertyHandler.registerProperty(uuid)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {

        return EnumActionResult.PASS
    }

    override fun onItemRightClick(worldIn: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        val templateId = getSelectedTemplateId(stack) ?: return actionResult(false, stack)
        player.divinePlayerData.templateData.currentTemplate = templateId
        MessageSyncSelectedTemplate(templateId).sendTo(player)
        return actionResult(true, stack)
    }

    override fun getSelectedTemplateId(stack: ItemStack): UUID? {
        if (stack.isPropertySet(uuid))
            return stack.get(uuid)
        return null
    }

    override fun getTemplatesIds(stack: ItemStack): List<UUID> {
        if (stack.isPropertySet(uuid))
            return listOf(stack.get(uuid))
        return emptyList()
    }

    companion object {
        val uuid = PropertyGenerator.stack.makeUUIDProperty("uuid", emptyUUID(), showInTooltip = false, showInGui = false)
    }
}

