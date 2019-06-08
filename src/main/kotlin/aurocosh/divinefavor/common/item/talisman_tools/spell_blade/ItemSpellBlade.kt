package aurocosh.divinefavor.common.item.talisman_tools.spell_blade

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.SpellBladeDataHandler.CAPABILITY_SPELL_BLADE
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.SpellBladeProvider
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability.SpellBladeStorage
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.ItemSpellBow
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.util.UtilItem.actionResult
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.ICapabilityProvider

open class ItemSpellBlade constructor(name: String, override val texturePath: String, override val orderIndex: Int = 0) : ModItem(name, texturePath, orderIndex) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN

        addPropertyOverride(ResourceLocation("book_mode")) { stack, _, _ ->
            (if (stack.compound.getBoolean(TAG_IS_IN_BOOK_MODE)) 1 else 0).toFloat()
        }
    }

    override fun hitEntity(stack: ItemStack, target: EntityLivingBase, attacker: EntityLivingBase): Boolean {
        if (attacker !is EntityPlayer)
            return true
        if (getMode(stack, attacker) != SpellBladeMode.BLADE)
            return true

        getTalisman<ItemBladeTalisman>(stack)?.cast(stack, target, attacker)
        return true
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = player.getHeldItem(hand)
        if (getMode(stack, player) != SpellBladeMode.BLADE)
            return EnumActionResult.PASS

        val talisman = getTalisman<ItemSpellTalisman>(stack) ?: return EnumActionResult.PASS
        talisman.castItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ)
        return EnumActionResult.SUCCESS
    }

    override fun onItemRightClick(worldIn: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        val bladeMode = getMode(stack, player)
        val success = performRightClickAction(worldIn, player, hand, stack, bladeMode)
        return actionResult(stack, success)
    }

    private fun performRightClickAction(world: World, player: EntityPlayer, hand: EnumHand, stack: ItemStack, mode: SpellBladeMode): Boolean {
        if (mode == SpellBladeMode.INVALID)
            return false
        if (mode == SpellBladeMode.BOOK)
            player.openGui(DivineFavor, ConstGuiIDs.SPELL_BLADE, world, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
        else if (mode == SpellBladeMode.BLADE)
            getTalisman<ItemSpellTalisman>(stack)?.castRightClick(world, player, hand)
        return true
    }

    private fun getMode(stack: ItemStack, player: EntityPlayer): SpellBladeMode {
        if (stack.item !is ItemSpellBlade)
            return SpellBladeMode.INVALID

        val compound = stack.compound
        val isBook = compound.getBoolean(ItemSpellBow.TAG_IS_IN_BOOK_MODE)
        if (player.isSneaking) {
            compound.setBoolean(ItemSpellBow.TAG_IS_IN_BOOK_MODE, !isBook)
            return SpellBladeMode.INVALID
        }
        return if (isBook) SpellBladeMode.BOOK else SpellBladeMode.BLADE
    }

    inline fun <reified T : ItemTalisman> getTalisman(stack: ItemStack): T? {
        if (stack.item !is ItemSpellBlade)
            return null

        val bladeHandler = stack.cap(CAPABILITY_SPELL_BLADE)
        val talismanStack = bladeHandler.getSelectedStack()
        if (talismanStack.isEmpty)
            return null

        return talismanStack.item as? T ?: return null
    }

    override fun initCapabilities(item: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return if (item.item is ItemSpellBlade) SpellBladeProvider() else null
    }

    override fun getShareTag(): Boolean {
        return true
    }

    override fun getNBTShareTag(stack: ItemStack): NBTTagCompound? {
        var tag = super.getNBTShareTag(stack)
        if (tag == null)
            tag = NBTTagCompound()

        val grimoireHandler = stack.cap(CAPABILITY_SPELL_BLADE)
        val tagShare = SpellBladeStorage.getNbtBase(grimoireHandler)
        tag.setTag(TAG_SHARE, tagShare)
        return tag
    }

    override fun readNBTShareTag(stack: ItemStack, nbt: NBTTagCompound?) {
        super.readNBTShareTag(stack, nbt)
        if (nbt == null)
            return

        val grimoireHandler = stack.cap(CAPABILITY_SPELL_BLADE)
        val tagShare = nbt.getCompoundTag(TAG_SHARE)
        SpellBladeStorage.readNbtBase(grimoireHandler, tagShare)
    }

    companion object {
        const val SLOT_COUNT = 18
        const val TAG_IS_IN_BOOK_MODE = "IsInBookMode"
        private const val TAG_SHARE = "SpellBlade"
    }
}

