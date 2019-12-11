package aurocosh.divinefavor.common.item.talisman_tools.grimoire

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContextGenerator
import aurocosh.divinefavor.common.item.talisman.IStackContainerProvider
import aurocosh.divinefavor.common.item.talisman_tools.IStackContainer
import aurocosh.divinefavor.common.item.talisman_tools.CastableAdapter
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability.GrimoireDataHandler.CAPABILITY_GRIMOIRE
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability.GrimoireProvider
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability.GrimoireStorage
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.extensions.isNotEmpty
import aurocosh.divinefavor.common.lib.interfaces.IBlockCatcher
import aurocosh.divinefavor.common.stack_actions.StackAction
import aurocosh.divinefavor.common.stack_actions.StackActionHandler
import aurocosh.divinefavor.common.stack_actions.interfaces.IActionAccessor
import aurocosh.divinefavor.common.stack_actions.interfaces.IActionContainer
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.interfaces.IStackPropertyAccessor
import aurocosh.divinefavor.common.stack_properties.interfaces.IStackPropertyContainer
import aurocosh.divinefavor.common.util.UtilItemStack
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class ItemGrimoire(name: String) : ModItem("grimoire_$name", "grimoires/$name", ConstMainTabOrder.CONTAINERS), IStackContainerProvider, IStackPropertyContainer, IActionContainer, IBlockCatcher {
    protected val propertyHandler: StackPropertyHandler = StackPropertyHandler("grimoire")
    override val properties: IStackPropertyAccessor = propertyHandler
    protected val actionHandler: StackActionHandler = StackActionHandler("grimoire")
    override val actions: IActionAccessor = actionHandler

    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun findProperty(stack: ItemStack, item: Item, propertyName: String) =
            CastableAdapter.findProperty(stack, item, propertyName, this, propertyHandler, CAPABILITY_GRIMOIRE)
    override fun findAction(stack: ItemStack, item: Item, actionName: String): Pair<ItemStack, StackAction>? =
            CastableAdapter.findAction(stack, item, actionName, this, actionHandler, CAPABILITY_GRIMOIRE)

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val containerStack = player.getHeldItem(hand)
        val (talismanStack, talisman) = CastableAdapter.getCastableStack<ItemSpellTalisman>(containerStack)
                ?: return EnumActionResult.PASS
        val context = CastContextGenerator.useCast(player, world, pos, hand, facing, talismanStack, containerStack)
        val success = talisman.cast(context)
        return UtilItemStack.actionResultPass(success || player.isSneaking)
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        val success = performRightClickAction(world, player, hand, stack)
        return UtilItemStack.actionResult(success, stack)
    }

    private fun performRightClickAction(world: World, player: EntityPlayer, hand: EnumHand, stack: ItemStack): Boolean {
        if (player.isSneaking) {
            player.openGui(DivineFavor, ConstGuiIDs.GRIMOIRE, world, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
        } else {
            val (talismanStack, talisman) = CastableAdapter.getCastableStack<ItemSpellTalisman>(stack) ?: return true
            val context = CastContextGenerator.rightClick(world, player, hand, talismanStack, stack)
            talisman.cast(context)
        }
        return true
    }

    override fun initCapabilities(item: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return if (item.item is ItemGrimoire) GrimoireProvider() else null
    }

    override fun getStackContainer(stack: ItemStack): IStackContainer = stack.cap(CAPABILITY_GRIMOIRE)

    override fun catchDrops(stack: ItemStack, toolStack: ItemStack, event: BlockEvent.HarvestDropsEvent) {
        val talismanStack = getSelectedStack(stack)
        if (talismanStack.isEmpty)
            return
        val catcher = talismanStack.item as IBlockCatcher
        catcher.catchDrops(talismanStack, toolStack, event)
    }

    override fun getSelectedStack(stack: ItemStack): ItemStack {
        if (stack.item !== this)
            return ItemStack.EMPTY
        return stack.cap(CAPABILITY_GRIMOIRE).getSelectedStack()
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)

        val talismanTool = getStackContainer(stack)
        val talismanCount = talismanTool.getAllStacks().filter (ItemStack::isNotEmpty).count()
        val countMessage = I18n.format("tooltip.divinefavor:talisman_tool.talisman_count", talismanCount)
        tooltip.add(countMessage)

        properties.getPropertyTooltip(stack).forEach { tooltip.add(it) }
    }

    override fun getShareTag(): Boolean {
        return true
    }

    override fun getNBTShareTag(stack: ItemStack): NBTTagCompound? {
        var tag = super.getNBTShareTag(stack)
        if (tag == null)
            tag = NBTTagCompound()

        val grimoireHandler = stack.cap(CAPABILITY_GRIMOIRE)
        val tagShare = GrimoireStorage.getNbtBase(grimoireHandler)
        tag.setTag(TAG_SHARE, tagShare)
        return tag
    }

    override fun readNBTShareTag(stack: ItemStack, nbt: NBTTagCompound?) {
        super.readNBTShareTag(stack, nbt)
        if (nbt == null)
            return

        val grimoireHandler = stack.cap(CAPABILITY_GRIMOIRE)
        val tagShare = nbt.getCompoundTag(TAG_SHARE)
        GrimoireStorage.readNbtBase(grimoireHandler, tagShare)
    }

    companion object {
        const val SLOT_COUNT = 27
        private const val TAG_SHARE = "Grimoire"
    }
}

