package aurocosh.divinefavor.common.item.gems.storage_gem

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.constants.FacingConstants
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.extensions.hasKey
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.lib.extensions.getBlockPos
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

class ItemStorageGem : ModItem("storage_gem", "storage_gem", ConstGemTabOrder.OTHER_GEMS) {
    init {
        setMaxStackSize(16)
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun onItemRightClick(world: World?, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        return if (!openChest(stack, world!!, player)) ActionResult(EnumActionResult.PASS, stack) else ActionResult(EnumActionResult.SUCCESS, stack)
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = player.getHeldItem(hand)
        return if (!openChest(stack, world, player)) EnumActionResult.PASS else EnumActionResult.SUCCESS
    }

    fun openChest(stack: ItemStack, world: World, playerIn: EntityPlayer): Boolean {
        if (world.isRemote)
            return true
        val compound = stack.compound
        if (!compound.hasKey(TAG_POSITION, TAG_DIMENSION))
            return false
        val pos = compound.getBlockPos(TAG_POSITION)
        val dimension = compound.getInteger(TAG_DIMENSION)
        if (playerIn.dimension != dimension)
            return false

        val iLockableContainer = getContainer(world, pos) ?: return false

        playerIn.displayGUIChest(StorageGemInventoryWrapper(iLockableContainer))
        playerIn.addStat(StatList.CHEST_OPENED)
        stack.shrink(1)
        return true
    }

    fun getContainer(worldIn: World, pos: BlockPos): ILockableContainer? {
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

    companion object {
        var TAG_POSITION = "Position"
        var TAG_DIMENSION = "Dimension"
    }
}