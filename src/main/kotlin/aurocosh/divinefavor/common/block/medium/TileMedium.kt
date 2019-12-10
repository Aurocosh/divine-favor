package aurocosh.divinefavor.common.block.medium

import aurocosh.divinefavor.common.block.base.TickableTileEntity
import aurocosh.divinefavor.common.block.soulbound_lectern.*
import aurocosh.divinefavor.common.item.gems.base.IMediumGemItem
import aurocosh.divinefavor.common.lib.extensions.*
import aurocosh.divinefavor.common.lib.iterators.IItemHandlerSlot
import aurocosh.divinefavor.common.muliblock.IMultiblockController
import aurocosh.divinefavor.common.muliblock.common.MultiblockWatcher
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockSpiritInstance
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.items.wrapper.CombinedInvWrapper

class TileMedium : TickableTileEntity(false, true), IMultiblockController {
    private val syncHandler = TilePropertySyncHandler()

    private val stateDelegate = TileEntityPropertyDelegate(stateMediumProp, syncHandler::sync)
    private val stoneDelegate = TileEntityPropertyDelegate(stoneMediumProp, syncHandler::sync)

    var state: MediumState by stateDelegate
    var stone: MediumStone by stoneDelegate

    // server side
    private var extraActiveTime: Int = 0
    private var isRejecting: Boolean = false
    private var multiBlockSpiritInstance: MultiBlockSpiritInstance? = null

    private var stateId = 0 // increases by one every time something in inventory is changed
    private var lastCheckedStateId = -1

    val stoneStackHandler: ItemStackHandler = object : ItemStackHandler(1) {
        override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
            return stack.item is IMediumGemItem && stack.count == 1
        }

        override fun onContentsChanged(slot: Int) {
            if (!world.isRemote) {
                isRejecting = true
                if (multiBlockSpiritInstance == null)
                    tryToFormMultiBlockInternal()
                updateState()
                val blockState = world.getBlockState(pos)
                world.notifyBlockUpdate(pos, blockState, blockState, 3)
            }
            stateId++
            markDirty()
        }
    }

    val leftStackHandler: ItemStackHandler = object : ItemStackHandler(9) {
        override fun onContentsChanged(slot: Int) {
            stateId++
            markDirty()
        }
    }

    val rightStackHandler: ItemStackHandler = object : ItemStackHandler(9) {
        override fun onContentsChanged(slot: Int) {
            stateId++
            markDirty()
        }
    }

    internal var combinedHandler = CombinedInvWrapper(leftStackHandler, rightStackHandler)

    val gemStack: ItemStack get() = stoneStackHandler.getStackInSlot(0)

    init {
        extraActiveTime = 0
        isRejecting = false
        setTickRate(TICK_RATE)
    }

    override fun onLoad() {
        tryToFormMultiBlockInternal()
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        extraActiveTime = compound.get(extraActiveTimeProp)
        state = compound.get(stateMediumProp)
        stone = compound.get(stoneMediumProp)
        if (compound.isPropertySet(callingStoneProp))
            stoneStackHandler.deserializeNBT(compound.get(callingStoneProp))
        if (compound.isPropertySet(itemsLeftProp))
            leftStackHandler.deserializeNBT(compound.get(itemsLeftProp))
        if (compound.isPropertySet(itemsRightProp))
            rightStackHandler.deserializeNBT(compound.get(itemsRightProp))
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(compound)
        compound.set(extraActiveTimeProp, extraActiveTime)
        compound.set(stateMediumProp, state)
        compound.set(stoneMediumProp, stone)
        compound.set(callingStoneProp, stoneStackHandler.serializeNBT())
        compound.set(itemsLeftProp, leftStackHandler.serializeNBT())
        compound.set(itemsRightProp, rightStackHandler.serializeNBT())
        return compound
    }

    fun isUsableByPlayer(playerIn: EntityPlayer): Boolean {
        // If we are too far away from this tile entity you cannot gainFavor it
        if (isRejecting) {
            isRejecting = false
            return false
        }
        return state != MediumState.ACTIVE && !isInvalid && playerIn.getDistanceSq(pos.add(0.5, 0.5, 0.5)) <= 64.0
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return if (capability === ITEM_HANDLER_CAPABILITY) facing == EnumFacing.WEST || facing == EnumFacing.EAST else super.hasCapability(capability, facing)
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (capability === ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.WEST)
                return ITEM_HANDLER_CAPABILITY.cast<T>(leftStackHandler)
            else if (facing == EnumFacing.EAST)
                return ITEM_HANDLER_CAPABILITY.cast<T>(rightStackHandler)
        }
        return super.getCapability(capability, facing)
    }

    override fun getUpdateTag(): NBTTagCompound {
        val nbtTag = super.getUpdateTag()
        nbtTag.set(stateMediumProp, state)
        nbtTag.set(stoneMediumProp, stone)
        nbtTag.set(callingStoneProp, stoneStackHandler.serializeNBT())
        return nbtTag
    }

    override fun getUpdatePacket(): SPacketUpdateTileEntity? {
        return SPacketUpdateTileEntity(pos, 1, updateTag)
    }

    override fun onDataPacket(net: NetworkManager?, packet: SPacketUpdateTileEntity?) {
        if (!world.isRemote)
            return

        val compound = packet?.nbtCompound ?: return

        val stateChanged = stateDelegate.readIfNotEqual(compound)
        val stoneChanged = stoneDelegate.readIfNotEqual(compound)

        if (compound.isPropertySet(callingStoneProp))
            stoneStackHandler.deserializeNBT(compound.get(callingStoneProp))

        if (stateChanged || stoneChanged)
            world.markBlockRangeForRenderUpdate(pos, pos)
    }

    override fun updateFiltered() {
        val stoneStack = gemStack
        if (!stoneStack.isEmpty && multiBlockSpiritInstance != null) {
            val gemItem = stoneStack.item as IMediumGemItem
            val slotStacks = combinedHandler.asSlotSequence().filter { it.stack.isNotEmpty() }.toList()

            checkForOfferings(gemItem, slotStacks)
            processCraftingRecipes(gemItem, slotStacks)
        }

        updateState()

        if (extraActiveTime > 0)
            extraActiveTime -= TICK_RATE
    }

    private fun checkForOfferings(gemItem: IMediumGemItem, slotStacks: List<IItemHandlerSlot>) {
        if (!gemItem.acceptsOfferings)
            return

        val offering = gemItem.spirit.offering
        val offeringCount = gemItem.spirit.offeringCount

        val offeringSlots = slotStacks.filter { it.stack.item === offering && it.stack.count >= offeringCount }.toList()
        extraActiveTime = if (offeringSlots.isNotEmpty()) OFFERING_ACTIVE_TIME else 0
        offeringSlots.forEach { it.extractItem() }
    }

    private fun processCraftingRecipes(gemItem: IMediumGemItem, slotStacks: List<IItemHandlerSlot>) {
        if (!gemItem.spirit.isActive && extraActiveTime <= 0)
            return
        if (stateId == lastCheckedStateId)
            return

        gemItem.exchangeItems(slotStacks, leftStackHandler, rightStackHandler)
        lastCheckedStateId = stateId
    }

    override fun getMultiblockInstance(): MultiBlockInstance? {
        return multiBlockSpiritInstance
    }

    override fun getControllerWorld(): World {
        return world
    }

    override fun multiblockDeconstructed() {
        MultiblockWatcher.unRegisterController(this)
        multiBlockSpiritInstance = null
        updateState()
    }

    override fun multiblockDamaged(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState) {
        val instance = multiBlockSpiritInstance
        multiblockDeconstructed()
        if (instance != null && instance.spirit.isActive)
            instance.spirit.punishment.execute(player, world, pos, state, instance)
    }

    override fun tryToFormMultiBlock() {
        tryToFormMultiBlockInternal()
        updateState()
    }

    private fun tryToFormMultiBlockInternal() {
        if (world.isRemote)
            return
        if (multiBlockSpiritInstance != null)
            return
        val stack = stoneStackHandler.getStackInSlot(0)
        if (stack.isEmpty)
            return

        val gemItem = stack.item as IMediumGemItem
        val multiBlock = gemItem.multiBlock
        multiBlockSpiritInstance = multiBlock.makeMultiBlock(gemItem.spirit, world, pos)
        if (multiBlockSpiritInstance != null)
            MultiblockWatcher.registerController(this)
    }

    private fun updateState() {
        val stack = gemStack
        if (!stack.isEmpty) {
            val gemItem = stack.item as IMediumGemItem
            stone = gemItem.stoneType
            state = if (multiBlockSpiritInstance != null) {
                if (gemItem.spirit.isActive || extraActiveTime > 0)
                    MediumState.ACTIVE
                else
                    MediumState.VALID
            } else
                MediumState.INVALID
        } else {
            stone = MediumStone.NONE
            state = MediumState.INVALID
        }
    }

    companion object {
        const val SIZE = 27
        private val TICK_RATE = UtilTick.secondsToTicks(2f)
        private val OFFERING_ACTIVE_TIME = UtilTick.secondsToTicks(10f)

        private val callingStoneProp = NbtPropertyNbtTag("CallingStone", NBTTagCompound())
        private val itemsLeftProp = NbtPropertyNbtTag("ItemsLeft", NBTTagCompound())
        private val itemsRightProp = NbtPropertyNbtTag("ItemsRight", NBTTagCompound())
        private val stateMediumProp = NbtPropertyEnum("StateMedium", MediumState.INVALID, MediumState)
        private val stoneMediumProp = NbtPropertyEnum("StoneMedium", MediumStone.NONE, MediumStone)
        private val extraActiveTimeProp = NbtPropertyInt("ExtraActiveTime", 0)

    }
}