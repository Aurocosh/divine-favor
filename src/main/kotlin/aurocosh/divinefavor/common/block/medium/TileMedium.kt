package aurocosh.divinefavor.common.block.medium

import aurocosh.divinefavor.common.block.base.TickableTileEntity
import aurocosh.divinefavor.common.item.gems.ItemCallingStone
import aurocosh.divinefavor.common.item.common.ModCallingStones
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.misc.SlotStack
import aurocosh.divinefavor.common.muliblock.IMultiblockController
import aurocosh.divinefavor.common.muliblock.common.MultiblockWatcher
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstanceAltar
import aurocosh.divinefavor.common.receipes.ModRecipes
import aurocosh.divinefavor.common.util.UtilHandler
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
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.items.wrapper.CombinedInvWrapper
import java.util.Arrays.asList

class TileMedium : TickableTileEntity(false, true), IMultiblockController {
    private var state = MediumState.INVALID
    private var stone = MediumStone.NONE

    // server side
    private var extraActiveTime: Int = 0
    private var isRejecting: Boolean = false
    private var multiBlockInstance: MultiBlockInstanceAltar? = null

    val stoneStackHandler: ItemStackHandler = object : ItemStackHandler(1) {
        override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
            return stack.item is ItemCallingStone && stack.count == 1
        }

        override fun onContentsChanged(slot: Int) {
            if (!world.isRemote) {
                isRejecting = true
                if (multiBlockInstance == null)
                    tryToFormMultiBlockInternal()
                updateState()
                val blockState = world.getBlockState(pos)
                world.notifyBlockUpdate(pos, blockState, blockState, 3)
            }
            markDirty()
        }
    }

    val leftStackHandler: ItemStackHandler = object : ItemStackHandler(9) {
        override fun onContentsChanged(slot: Int) {
            markDirty()
        }
    }

    val rightStackHandler: ItemStackHandler = object : ItemStackHandler(9) {
        override fun onContentsChanged(slot: Int) {
            markDirty()
        }
    }

    internal var combinedHandler = CombinedInvWrapper(leftStackHandler, rightStackHandler)

    val stoneStack: ItemStack
        get() = stoneStackHandler.getStackInSlot(0)

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
        extraActiveTime = compound.getInteger(TAG_EXTRA_ACTIVE_TIME)
        state = MediumState[compound.getInteger(TAG_STATE_MEDIUM)]
        stone = MediumStone[compound.getInteger(TAG_STONE_MEDIUM)]
        if (compound.hasKey(TAG_CALLING_STONE))
            stoneStackHandler.deserializeNBT(compound.getTag(TAG_CALLING_STONE) as NBTTagCompound)
        if (compound.hasKey(TAG_ITEMS_LEFT))
            leftStackHandler.deserializeNBT(compound.getTag(TAG_ITEMS_LEFT) as NBTTagCompound)
        if (compound.hasKey(TAG_ITEMS_RIGHT))
            rightStackHandler.deserializeNBT(compound.getTag(TAG_ITEMS_RIGHT) as NBTTagCompound)
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(compound)
        compound.setInteger(TAG_EXTRA_ACTIVE_TIME, extraActiveTime)
        compound.setInteger(TAG_STATE_MEDIUM, state.ordinal)
        compound.setInteger(TAG_STONE_MEDIUM, stone.ordinal)
        compound.setTag(TAG_CALLING_STONE, stoneStackHandler.serializeNBT())
        compound.setTag(TAG_ITEMS_LEFT, leftStackHandler.serializeNBT())
        compound.setTag(TAG_ITEMS_RIGHT, rightStackHandler.serializeNBT())
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
        return if (capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) facing == EnumFacing.WEST || facing == EnumFacing.EAST else super.hasCapability(capability, facing)
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.WEST)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast<T>(leftStackHandler)
            else if (facing == EnumFacing.EAST)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast<T>(rightStackHandler)
        }
        return super.getCapability(capability, facing)
    }

    override fun getUpdateTag(): NBTTagCompound {
        val nbtTag = super.getUpdateTag()
        nbtTag.setInteger(TAG_STATE_MEDIUM, state.ordinal)
        nbtTag.setInteger(TAG_STONE_MEDIUM, stone.ordinal)
        nbtTag.setTag(TAG_CALLING_STONE, stoneStackHandler.serializeNBT())
        return nbtTag
    }

    override fun getUpdatePacket(): SPacketUpdateTileEntity? {
        return SPacketUpdateTileEntity(pos, 1, updateTag)
    }

    override fun onDataPacket(net: NetworkManager?, packet: SPacketUpdateTileEntity?) {
        if (!world.isRemote)
            return

        val compound = packet!!.nbtCompound
        val stateIndex = compound.getInteger(TAG_STATE_MEDIUM)
        if (stateIndex != state.ordinal) {
            state = MediumState[stateIndex]
            world.markBlockRangeForRenderUpdate(pos, pos)
        }
        val stoneIndex = compound.getInteger(TAG_STONE_MEDIUM)
        if (stoneIndex != stone.ordinal) {
            stone = MediumStone[stoneIndex]
            world.markBlockRangeForRenderUpdate(pos, pos)
        }

        if (compound.hasKey(TAG_CALLING_STONE))
            stoneStackHandler.deserializeNBT(compound.getCompoundTag(TAG_CALLING_STONE))
    }

    fun getStone(): MediumStone {
        return stone
    }

    fun setStone(stone: MediumStone) {
        if (this.stone == stone)
            return

        this.stone = stone
        markDirty()
        val blockState = world.getBlockState(pos)
        world.notifyBlockUpdate(pos, blockState, blockState, 3)
    }

    fun getState(): MediumState {
        return state
    }

    fun setState(state: MediumState) {
        if (this.state == state)
            return

        this.state = state
        markDirty()
        val blockState = world.getBlockState(pos)
        world.notifyBlockUpdate(pos, blockState, blockState, 3)
    }

    override fun updateFiltered() {
        val stoneStack = stoneStack
        if (!stoneStack.isEmpty && multiBlockInstance != null) {
            val callingStone = stoneStack.item as ItemCallingStone
            val slotStacks = UtilHandler.getNotEmptyStacksWithSlotIndexes(combinedHandler)
            checkForOfferings(callingStone, slotStacks)
            processCraftingRecipes(callingStone, slotStacks)
        }

        updateState()

        if (extraActiveTime > 0)
            extraActiveTime -= TICK_RATE
    }

    private fun checkForOfferings(callingStone: ItemCallingStone, slotStacks: List<SlotStack>) {
        val offering = callingStone.spirit.offering
        val offeringCount = callingStone.spirit.offeringCount

        for ((index, stack) in slotStacks) {
            if (stack.item === offering) {
                if (stack.count >= offeringCount)
                    extraActiveTime = OFFERING_ACTIVE_TIME
                combinedHandler.setStackInSlot(index, ItemStack.EMPTY)
            }
        }
    }

    private fun processCraftingRecipes(callingStone: ItemCallingStone, slotStacks: List<SlotStack>) {
        if (!callingStone.spirit.isActive && extraActiveTime <= 0)
            return

        for ((_, stack) in slotStacks) {
            if (stack.item === ModItems.ritual_pouch) {
                val handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
                if (handler != null)
                    ModRecipes.exchangeRecipe(callingStone, handler, slotIndexesPouch)
            }
        }

        ModRecipes.exchangeRecipe(callingStone, leftStackHandler, slotIndexesAltar)
        ModRecipes.exchangeRecipe(callingStone, rightStackHandler, slotIndexesAltar)
    }

    override fun getMultiblockInstance(): MultiBlockInstance? {
        return multiBlockInstance
    }

    override fun getControllerWorld(): World {
        return world
    }

    override fun multiblockDeconstructed() {
        MultiblockWatcher.unRegisterController(this)
        multiBlockInstance = null
        updateState()
        //        AltarsData altarsData = WorldData.get(world).getAltarsData();
        //        altarsData.removeAltarLocation(pos);
    }

    override fun multiblockDamaged(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState) {
        val instance = multiBlockInstance
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
        if (multiBlockInstance != null)
            return
        val stack = stoneStackHandler.getStackInSlot(0)
        if (stack.isEmpty)
            return

        val callingStone = stack.item as ItemCallingStone
        val multiBlock = callingStone.multiBlock
        multiBlockInstance = multiBlock.makeMultiBlock(callingStone.spirit, world, pos)
        if (multiBlockInstance != null) {
            MultiblockWatcher.registerController(this)
            //            AltarsData altarsData = WorldData.get(world).getAltarsData();
            //            altarsData.addAltarLocation(callingStone.spirit, pos);
        }
    }

    private fun updateState() {
        val stack = stoneStack
        if (!stack.isEmpty) {
            val callingStone = stack.item as ItemCallingStone
            if (callingStone == ModCallingStones.calling_stone_arbow)
                setStone(MediumStone.ARBOW)
            else if (callingStone == ModCallingStones.calling_stone_blizrabi)
                setStone(MediumStone.BLIZRABI)
            else if (callingStone == ModCallingStones.calling_stone_endererer)
                setStone(MediumStone.ENDERERER)
            else if (callingStone == ModCallingStones.calling_stone_loon)
                setStone(MediumStone.LOON)
            else if (callingStone == ModCallingStones.calling_stone_neblaze)
                setStone(MediumStone.NEBLAZE)
            else if (callingStone == ModCallingStones.calling_stone_redwind)
                setStone(MediumStone.REDWIND)
            else if (callingStone == ModCallingStones.calling_stone_romol)
                setStone(MediumStone.ROMOL)
            else if (callingStone == ModCallingStones.calling_stone_squarefury)
                setStone(MediumStone.SQUAREFURY)
            else if (callingStone == ModCallingStones.calling_stone_timber)
                setStone(MediumStone.TIMBER)

            if (multiBlockInstance != null) {
                val spirit = callingStone.spirit
                if (spirit.isActive || extraActiveTime > 0)
                    setState(MediumState.ACTIVE)
                else
                    setState(MediumState.VALID)
            } else
                setState(MediumState.INVALID)
        } else {
            setStone(MediumStone.NONE)
            setState(MediumState.INVALID)
        }
    }

    companion object {
        const val SIZE = 27
        val TICK_RATE = UtilTick.secondsToTicks(2f)
        val OFFERING_ACTIVE_TIME = UtilTick.secondsToTicks(10f)
        private const val TAG_CALLING_STONE = "CallingStone"
        private const val TAG_ITEMS_LEFT = "ItemsLeft"
        private const val TAG_ITEMS_RIGHT = "ItemsRight"
        private const val TAG_STATE_MEDIUM = "StateMedium"
        private const val TAG_STONE_MEDIUM = "StoneMedium"
        private const val TAG_EXTRA_ACTIVE_TIME = "ExtraActiveTime"

        private val slotIndexesPouch = asList(3, 1, 5, 0, 2, 4, 6)
        private val slotIndexesAltar = asList(4, 1, 3, 5, 7, 0, 2, 6, 8)

    }
}