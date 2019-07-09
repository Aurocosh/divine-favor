package aurocosh.divinefavor.common.item.memory_drop

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.item.ITemplateContainer
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.EmptyConst.invalidUUID
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isPropertySet
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyAccessor
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyContainer
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyString
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyUUID
import aurocosh.divinefavor.common.stack_properties.properties.base.StackProperty
import aurocosh.divinefavor.common.util.UtilItem.actionResult
import aurocosh.divinefavor.common.util.UtilTemplate
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import java.util.*

open class ItemMemoryDrop(name: String, texturePath: String, orderIndex: Int = 0) : ModItem(name, texturePath, orderIndex), ITemplateContainer, IPropertyContainer {
    protected val propertyHandler: StackPropertyHandler = StackPropertyHandler(name)
    override val properties: IPropertyAccessor = propertyHandler

    init {
        propertyHandler.registerProperty(uuid)
        propertyHandler.registerProperty(templateName)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun findProperty(stack: ItemStack, item: Item, propertyName: String): Pair<ItemStack, StackProperty<out Any>>? {
        if (item != this)
            return null
        val property = propertyHandler.get(propertyName)
        return if (property != null) Pair(stack, property) else null
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        if (player.isSneaking) {
            player.openGui(DivineFavor, ConstGuiIDs.MEMORY_DROP, world, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
            return actionResult(true, stack)
        }

        val templateId = getSelectedTemplateId(stack) ?: return actionResult(false, stack)
        UtilTemplate.setCurrent(player,templateId)
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
        val uuid = StackPropertyUUID("uuid", invalidUUID(), showInTooltip = false, showInGui = false, orderIndex = 0)
        val templateName = StackPropertyString("template_name", "Template", showInTooltip = true, showInGui = false, orderIndex = 0)
    }
}

