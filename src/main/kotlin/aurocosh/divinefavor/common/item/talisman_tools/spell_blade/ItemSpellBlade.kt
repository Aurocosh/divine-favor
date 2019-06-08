package aurocosh.divinefavor.common.item.talisman_tools.spell_blade

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.SpellBladeDataHandler.CAPABILITY_SPELL_BLADE
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.SpellBladeProvider
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability.SpellBladeStorage
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.ItemSpellBow
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.extensions.compound
import net.minecraft.entity.Entity
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

    override fun onLeftClickEntity(stack: ItemStack, player: EntityPlayer, entity: Entity?): Boolean {
//        if (player.world.isRemote)
//            return false
//        if (entity !is EntityLivingBase)
//            return false
//        val compound = stack.compound
//        val chance = compound.getFloat(TAG_AWAKENING_CHANCE)
//        if (UtilRandom.rollDiceFloat(chance)) {
//            stack.shrink(1)
//            player.addItemStackToInventory(ItemStack(ModItems.bone_dagger_awakened, 1))
//        }
//        compound.setFloat(TAG_AWAKENING_CHANCE, chance + ConfigItem.boneDagger.awakeningSpeed)
        return false
    }


    override fun onItemUse(playerIn: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = playerIn.getHeldItem(hand)
        if (stack.item !is ItemSpellBlade)
            return EnumActionResult.PASS

        val bladeHandler = stack.cap(CAPABILITY_SPELL_BLADE)
        val talismanStack = bladeHandler.getSelectedStack()
        if (talismanStack.isEmpty)
            return EnumActionResult.PASS

        val talisman = talismanStack.item as ItemSpellTalisman
        talisman.castItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ)
        return EnumActionResult.SUCCESS
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(hand)
        if (stack.item !is ItemSpellBlade)
            return ActionResult(EnumActionResult.PASS, stack)

        val compound = stack.compound
        val isBook = compound.getBoolean(ItemSpellBow.TAG_IS_IN_BOOK_MODE)
        if (playerIn.isSneaking) {
            compound.setBoolean(ItemSpellBow.TAG_IS_IN_BOOK_MODE, !isBook)
            return ActionResult(EnumActionResult.SUCCESS, stack)
        }
        return if (isBook)
            doBookAction(worldIn, playerIn, stack)
        else
            doBladeAction(worldIn, playerIn, hand, stack)
    }

    private fun doBookAction(world: World, player: EntityPlayer, stack: ItemStack): ActionResult<ItemStack> {
        player.openGui(DivineFavor, ConstGuiIDs.SPELL_BLADE, world, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
        return ActionResult(EnumActionResult.SUCCESS, stack)
    }

    private fun doBladeAction(worldIn: World, playerIn: EntityPlayer, hand: EnumHand, stack: ItemStack): ActionResult<ItemStack> {

        val bladeHandler = stack.cap(CAPABILITY_SPELL_BLADE)
        val talismanStack = bladeHandler.getSelectedStack()
        if (talismanStack.isEmpty)
            return ActionResult(EnumActionResult.PASS, stack)

        val talisman = talismanStack.item as ItemSpellTalisman
        talisman.castRightClick(worldIn, playerIn, hand)
        return ActionResult(EnumActionResult.SUCCESS, stack)
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

