package aurocosh.divinefavor.common.block.soulbound_lectern

import aurocosh.divinefavor.common.item.soul_shards.ItemSoulShard
import aurocosh.divinefavor.common.muliblock.IMultiblockController
import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks
import aurocosh.divinefavor.common.muliblock.common.MultiblockWatcher
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.spirit.ModSpirits
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import java.util.*

class TileSoulboundLectern : TileEntity(), IMultiblockController {

    private var isRejecting: Boolean = false
    private var multiBlockInstance: MultiBlockInstance? = null

    private var _state = SoulboundLecternState.INACTIVE
    var state: SoulboundLecternState
        set(value) {
            if (_state === value)
                return
            _state = value
            markDirty()
            val blockState = world.getBlockState(pos)
            getWorld().notifyBlockUpdate(pos, blockState, blockState, 3)
        }
        get() = _state

    private var _gem = SoulboundLecternGem.NONE
    var gem: SoulboundLecternGem
        set(value) {
            if (_gem === value)
                return
            _gem = value
            markDirty()
            val blockState = world.getBlockState(pos)
            getWorld().notifyBlockUpdate(pos, blockState, blockState, 3)
        }
        get() = _gem

    val shardStackHandler: ItemStackHandler = object : ItemStackHandler(1) {
        override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
            return stack.item is ItemSoulShard && stack.count == 1
        }

        override fun onContentsChanged(slot: Int) {
            if (!world.isRemote) {
                val stack = this.getStackInSlot(slot)
                if (!stack.isEmpty) {
                    isRejecting = true

                    val blockState = world.getBlockState(pos)
                    getWorld().notifyBlockUpdate(pos, blockState, blockState, 3)

                    tryToFormMultiBlock()
                }
            }
            markDirty()
        }
    }

    val isMultiblockValid: Boolean
        get() = multiBlockInstance != null

    val shardStack: ItemStack
        get() = shardStackHandler.getStackInSlot(0)

    val favorId: Int
        get() {
            val stack = shardStack
            if (stack.isEmpty)
                return -1
            val soulShard = stack.item as ItemSoulShard
            return soulShard.spirit.id
        }

    init {
        isRejecting = false
    }

    override fun onLoad() {
        tryToFormMultiBlockInternal()
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        _state = SoulboundLecternState.VALUES[compound.getInteger(TAG_STATE_LECTERN)]
        _gem = SoulboundLecternGem.INDEXER[compound.getInteger(TAG_GEM_LECTERN)]
        if (compound.hasKey(TAG_SHARD))
            shardStackHandler.deserializeNBT(compound.getTag(TAG_SHARD) as NBTTagCompound)
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(compound)
        compound.setInteger(TAG_STATE_LECTERN, state.ordinal)
        compound.setInteger(TAG_GEM_LECTERN, gem.ordinal)
        compound.setTag(TAG_SHARD, shardStackHandler.serializeNBT())
        return compound
    }

    fun isUsableByPlayer(playerIn: EntityPlayer): Boolean {
        // If we are too far away from this tile entity you cannot gainFavor it
        if (isRejecting) {
            isRejecting = false
            return false
        }
        return !isInvalid && playerIn.getDistanceSq(pos.add(0.5, 0.5, 0.5)) <= 64.0
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return if (capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) facing == null else super.hasCapability(capability, facing)
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast<T>(shardStackHandler)
        }
        return super.getCapability(capability, facing)
    }

    override fun getUpdateTag(): NBTTagCompound {
        val nbtTag = super.getUpdateTag()
        nbtTag.setTag(TAG_SHARD, shardStackHandler.serializeNBT())
        nbtTag.setInteger(TAG_STATE_LECTERN, state.ordinal)
        nbtTag.setInteger(TAG_GEM_LECTERN, gem.ordinal)
        return nbtTag
    }

    override fun getUpdatePacket(): SPacketUpdateTileEntity? {
        return SPacketUpdateTileEntity(pos, 1, updateTag)
    }

    override fun onDataPacket(net: NetworkManager?, packet: SPacketUpdateTileEntity?) {
        if (!world.isRemote)
            return
        val compound = packet!!.nbtCompound
        shardStackHandler.deserializeNBT(compound.getCompoundTag(TAG_SHARD))

        val stateIndex = compound.getInteger(TAG_STATE_LECTERN)
        val gemIndex = compound.getInteger(TAG_GEM_LECTERN)
        if (stateIndex != state.ordinal || gemIndex != gem.ordinal) {
            gem = SoulboundLecternGem.INDEXER[gemIndex]
            state = SoulboundLecternState.VALUES[stateIndex]
            world.markBlockRangeForRenderUpdate(pos, pos)
        }
    }

    override fun getMultiblockInstance(): MultiBlockInstance? {
        return multiBlockInstance
    }

    override fun multiblockDeconstructed() {
        MultiblockWatcher.unRegisterController(this)
        state = SoulboundLecternState.INACTIVE
        multiBlockInstance = null
    }

    override fun multiblockDamaged(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState) {
        multiblockDeconstructed()
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
        val stack = shardStack
        if (stack.isEmpty)
            return
        val soulShard = stack.item as ItemSoulShard
        val multiBlock = multiblocks[soulShard.spirit.id]
        if (multiBlock != null) {
            multiBlockInstance = multiBlock.makeMultiBlock(world, pos)
            if (multiBlockInstance != null)
                MultiblockWatcher.registerController(this)
        }
    }

    private fun updateState() {
        val stack = shardStack
        if (stack.isEmpty)
            return
        val soulShard = stack.item as ItemSoulShard
        val spirit = soulShard.spirit
        if (spirit == ModSpirits.endererer)
            gem = SoulboundLecternGem.END
        else if (spirit == ModSpirits.romol)
            gem = SoulboundLecternGem.MIND
        else if (spirit == ModSpirits.neblaze)
            gem = SoulboundLecternGem.NETHER
        else if (spirit == ModSpirits.arbow)
            gem = SoulboundLecternGem.PEACE
        else if (spirit == ModSpirits.redwind)
            gem = SoulboundLecternGem.WILL
        else if (spirit == ModSpirits.loon)
            gem = SoulboundLecternGem.UNDEATH
        else if (spirit == ModSpirits.blizrabi)
            gem = SoulboundLecternGem.WATER
        else if (spirit == ModSpirits.squarefury)
            gem = SoulboundLecternGem.WILD
        else if (spirit == ModSpirits.timber)
            gem = SoulboundLecternGem.WITHER
        else
            gem = SoulboundLecternGem.NONE

        if (multiBlockInstance == null)
            state = SoulboundLecternState.INACTIVE
        else
            state = SoulboundLecternState.ACTIVE
    }

    companion object {
        private val TAG_SHARD = "Shard"
        private val TAG_STATE_LECTERN = "StateLectern"
        private val TAG_GEM_LECTERN = "GemLectern"
        private val multiblocks = HashMap<Int, ModMultiBlock>()

        init {
            multiblocks[ModSpirits.arbow.id] = ModMultiBlocks.soulbound_lectern_arbow
            multiblocks[ModSpirits.blizrabi.id] = ModMultiBlocks.soulbound_lectern_blizrabi
            multiblocks[ModSpirits.endererer.id] = ModMultiBlocks.soulbound_lectern_endererer
            multiblocks[ModSpirits.loon.id] = ModMultiBlocks.soulbound_lectern_loon
            multiblocks[ModSpirits.neblaze.id] = ModMultiBlocks.soulbound_lectern_neblaze
            multiblocks[ModSpirits.redwind.id] = ModMultiBlocks.soulbound_lectern_redwind
            multiblocks[ModSpirits.romol.id] = ModMultiBlocks.soulbound_lectern_romol
            multiblocks[ModSpirits.squarefury.id] = ModMultiBlocks.soulbound_lectern_squarefury
            multiblocks[ModSpirits.timber.id] = ModMultiBlocks.soulbound_lectern_timber
        }
    }
}