package aurocosh.divinefavor.common.item.gems.storage

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.constants.FacingConstants
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.gems.base.IUsableGemItem
import aurocosh.divinefavor.common.item.gems.properties.GemMaskProperties
import aurocosh.divinefavor.common.item.gems.properties.GemPositionProperties
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContextGenerator
import aurocosh.divinefavor.common.item.talisman.ISelectedStackProvider
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isPropertySet
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyAccessor
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyContainer
import aurocosh.divinefavor.common.util.UtilItemStack
import net.minecraft.block.BlockChest
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.inventory.InventoryLargeChest
import net.minecraft.item.ItemStack
import net.minecraft.stats.StatList
import net.minecraft.tileentity.TileEntityChest
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ILockableContainer
import net.minecraft.world.World

class ItemStorageGem(name: String, override val consumeOnUse: Boolean, override val favorCost: Int, override val spirit: ModSpirit, maxStackSize: Int) : ModItem(name, name, ConstGemTabOrder.OTHER_GEMS), IPropertyContainer, IUsableGemItem, ISelectedStackProvider {
    override val properties: IPropertyAccessor = propertyHandler

    init {
        setMaxStackSize(maxStackSize)
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        val context = CastContextGenerator.rightClick(world, player, hand, stack, stack)
        val result = cast(context)
        return UtilItemStack.actionResult(result, stack)
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = player.getHeldItem(hand)
        val context = CastContextGenerator.useCast(player, world, pos, hand, facing, stack, stack)
        val result = cast(context)
        return UtilItemStack.actionResultPass(result)
    }

    override fun cast(context: CastContext): Boolean {
        val world = context.world
        if (world.isRemote)
            return true

        val stack = context.stack
        if (stack.isEmpty)
            return false
        if (stack.item !is ItemStorageGem)
            return false
        if (!stack.isPropertySet(position))
            return false

        val player = context.player
        val dimension = stack.get(dimension)
        if (dimension != player.dimension)
            return false
        if (!consumeFavor(player))
            return false

        val pos = stack.get(position)
        val iLockableContainer = getContainer(world, pos) ?: return false

        player.displayGUIChest(StorageGemInventoryWrapper(iLockableContainer))
        player.addStat(StatList.CHEST_OPENED)
        if (consumeOnUse)
            stack.shrink(1)
        return true
    }

    private fun consumeFavor(player: EntityPlayer): Boolean {
        if (favorCost == 0)
            return true
        val spiritData = player.divinePlayerData.spiritData
        return spiritData.consumeFavor(spirit.id, favorCost)
    }

    private fun getContainer(worldIn: World, pos: BlockPos): ILockableContainer? {
        val tileEntity = worldIn.getTileEntity(pos) as? TileEntityChest ?: return null
        if (tileEntity.chestType != BlockChest.Type.BASIC)
            return null
        val connectedFacing = findConnectedChest(worldIn, pos) ?: return tileEntity

        val connectedPos = pos.offset(connectedFacing)
        val tileMergedChest = worldIn.getTileEntity(connectedPos)

        return if (connectedFacing != EnumFacing.WEST && connectedFacing != EnumFacing.NORTH)
            InventoryLargeChest("container.chestDouble", tileEntity, tileMergedChest as TileEntityChest)
        else
            InventoryLargeChest("container.chestDouble", tileMergedChest as TileEntityChest, tileEntity)
    }

    private fun findConnectedChest(worldIn: World, pos: BlockPos): EnumFacing? {
        for (facing in FacingConstants.horizontal) {
            val blockPos = pos.offset(facing)
            val block = worldIn.getBlockState(blockPos).block
            if (block === Blocks.CHEST)
                return facing
        }
        return null
    }

    override fun getSelectedStack(stack: ItemStack): ItemStack = stack

    companion object {
        val propertyHandler = StackPropertyHandler()
        val position = propertyHandler.registerProperty(GemPositionProperties.position)
        val dimension = propertyHandler.registerProperty(GemPositionProperties.dimension)
        val maskItemId = propertyHandler.registerProperty(GemMaskProperties.maskItemId)
        val maskItemMeta = propertyHandler.registerProperty(GemMaskProperties.maskItemMeta)
    }
}