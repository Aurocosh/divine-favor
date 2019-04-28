package aurocosh.divinefavor.common.item.talisman_container.grimoire

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.talisman_container.grimoire.capability.GrimoireDataHandler
import aurocosh.divinefavor.common.item.talisman_container.grimoire.capability.GrimoireProvider
import aurocosh.divinefavor.common.item.talisman_container.grimoire.capability.GrimoireStorage
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
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

class ItemGrimoire : ModItem("grimoire", "grimoire", ConstMainTabOrder.CONTAINERS) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onItemUse(playerIn: EntityPlayer, worldIn: World?, pos: BlockPos?, hand: EnumHand?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = playerIn.getHeldItem(hand)
        if (stack.item !is ItemGrimoire)
            return EnumActionResult.PASS

        val grimoireHandler = GrimoireDataHandler.get(stack)
        val talismanStack = grimoireHandler!!.getSelectedStack()
        if (talismanStack.isEmpty)
            return EnumActionResult.PASS

        val talisman = talismanStack.item as ItemSpellTalisman
        talisman.castItemUse(playerIn, worldIn!!, pos!!, hand!!, facing!!, hitX, hitY, hitZ)
        return EnumActionResult.SUCCESS
    }

    override fun onItemRightClick(worldIn: World?, playerIn: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(hand)
        if (stack.item !== ModItems.grimoire)
            return ActionResult(EnumActionResult.PASS, stack)

        if (playerIn.isSneaking) {
            playerIn.openGui(DivineFavor, ConstGuiIDs.GRIMOIRE, worldIn!!, playerIn.posX.toInt(), playerIn.posY.toInt(), playerIn.posZ.toInt())
            return ActionResult(EnumActionResult.SUCCESS, stack)
        }

        val grimoireHandler = GrimoireDataHandler.get(stack)
        val talismanStack = grimoireHandler!!.getSelectedStack()
        if (talismanStack.isEmpty)
            return ActionResult(EnumActionResult.PASS, stack)

        val talisman = talismanStack.item as ItemSpellTalisman
        talisman.castRightClick(worldIn!!, playerIn, hand)
        return ActionResult(EnumActionResult.SUCCESS, stack)
    }

    override fun initCapabilities(item: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return if (item.item === ModItems.grimoire) GrimoireProvider() else null
    }

    override fun getShareTag(): Boolean {
        return true
    }

    override fun getNBTShareTag(stack: ItemStack): NBTTagCompound? {
        var tag = super.getNBTShareTag(stack)
        if (tag == null)
            tag = NBTTagCompound()

        val grimoireHandler = GrimoireDataHandler.get(stack)
        val tagShare = GrimoireStorage.getNbtBase(grimoireHandler!!)
        tag.setTag(TAG_SHARE, tagShare)
        return tag
    }

    override fun readNBTShareTag(stack: ItemStack, nbt: NBTTagCompound?) {
        super.readNBTShareTag(stack, nbt)
        if (nbt == null)
            return

        val grimoireHandler = GrimoireDataHandler.get(stack)
        val tagShare = nbt.getCompoundTag(TAG_SHARE)
        GrimoireStorage.readNbtBase(grimoireHandler!!, tagShare)
    }

    companion object {
        val SLOT_COUNT = 27
        private val TAG_SHARE = "Grimoire"
    }
}

