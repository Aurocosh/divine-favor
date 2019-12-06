package aurocosh.divinefavor.common.item.gem_pouch

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.gem_pouch.capability.GemPouchDataHandler.CAPABILITY_GEM_POUCH
import aurocosh.divinefavor.common.item.gem_pouch.capability.GemPouchProvider
import aurocosh.divinefavor.common.item.gem_pouch.capability.GemPouchStorage
import aurocosh.divinefavor.common.item.gems.ItemWarpMarker
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContextGenerator
import aurocosh.divinefavor.common.item.talisman.IStackContainerProvider
import aurocosh.divinefavor.common.item.talisman_tools.CastableAdapter
import aurocosh.divinefavor.common.item.talisman_tools.IStackContainer
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.extensions.isNotEmpty
import aurocosh.divinefavor.common.util.UtilItemStack
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class ItemGemPouch : ModItem("gem_pouch", "gem_pouch", ConstMainTabOrder.CONTAINERS), IStackContainerProvider {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

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
            player.openGui(DivineFavor, ConstGuiIDs.GEM_POUCH, world, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
        } else {
            val (gemStack, gem) = CastableAdapter.getCastableStack<ItemWarpMarker>(stack) ?: return true
            val context = CastContextGenerator.rightClick(world, player, hand, gemStack, stack)
            gem.cast(context)
        }
        return true
    }

    override fun initCapabilities(item: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return if (item.item is ItemGemPouch) GemPouchProvider() else null
    }

    override fun getStackContainer(stack: ItemStack): IStackContainer = stack.cap(CAPABILITY_GEM_POUCH)

    override fun getSelectedStack(stack: ItemStack): ItemStack {
        return if (stack.item !== this)
            ItemStack.EMPTY
        else stack.cap(CAPABILITY_GEM_POUCH).getSelectedStack()
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)

        val talismanTool = getStackContainer(stack)
        val talismanCount = talismanTool.getAllStacks().filter(ItemStack::isNotEmpty).count()
        val countMessage = I18n.format("tooltip.divinefavor:gem_pouch.gem_count", talismanCount)
        tooltip.add(countMessage)
    }

    override fun getShareTag(): Boolean {
        return true
    }

    override fun getNBTShareTag(stack: ItemStack): NBTTagCompound? {
        var tag = super.getNBTShareTag(stack)
        if (tag == null)
            tag = NBTTagCompound()

        val pouchHandler = stack.cap(CAPABILITY_GEM_POUCH)
        val tagShare = GemPouchStorage.getNbtBase(pouchHandler)
        tag.setTag(TAG_SHARE, tagShare)
        return tag
    }

    override fun readNBTShareTag(stack: ItemStack, nbt: NBTTagCompound?) {
        super.readNBTShareTag(stack, nbt)
        if (nbt == null)
            return

        val pouchHandler = stack.cap(CAPABILITY_GEM_POUCH)
        val tagShare = nbt.getCompoundTag(TAG_SHARE)
        GemPouchStorage.readNbtBase(pouchHandler, tagShare)
    }

    companion object {
        const val SLOT_COUNT = 27
        private const val TAG_SHARE = "GemPouch"
    }
}

