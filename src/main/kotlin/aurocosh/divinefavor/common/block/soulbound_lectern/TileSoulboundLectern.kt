package aurocosh.divinefavor.common.block.soulbound_lectern

import aurocosh.divinefavor.common.item.gems.soul_shards.ItemSoulShard
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.mapPairs
import aurocosh.divinefavor.common.muliblock.IMultiblockController
import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks
import aurocosh.divinefavor.common.muliblock.common.MultiblockWatcher
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockSpiritInstance
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
    private val syncHandler = TilePropertySyncHandler()

    private var isRejecting: Boolean = false
    private var multiBlockInstance: MultiBlockSpiritInstance? = null

    private val spiritIdDelegate = TileEntityPropertyDelegate(spiritProperty, syncHandler::sync)
    private val gemDelegate = TileEntityPropertyDelegate(gemProperty, syncHandler::sync)
    private val stateDelegate = TileEntityPropertyDelegate(stateProperty, syncHandler::sync)

    var spiritId: Int by spiritIdDelegate
    var gem: SoulboundLecternGem by gemDelegate
    var state: SoulboundLecternState by stateDelegate

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
                    world.notifyBlockUpdate(pos, blockState, blockState, 3)

                    tryToFormMultiBlock()
                }
            }
            markDirty()
        }
    }

    val isMultiblockValid: Boolean get() = multiBlockInstance != null
    val shardStack: ItemStack get() = shardStackHandler.getStackInSlot(0)

    init {
        isRejecting = false
    }

    override fun onLoad() {
        tryToFormMultiBlockInternal()
        syncHandler.autoSync = !world.isRemote
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        stateDelegate.readFromNbt(compound)
        gemDelegate.readFromNbt(compound)
        spiritIdDelegate.readFromNbt(compound)
        if (compound.hasKey(TAG_SHARD))
            shardStackHandler.deserializeNBT(compound.getTag(TAG_SHARD) as NBTTagCompound)
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(compound)
        stateDelegate.writeToNbt(compound)
        gemDelegate.writeToNbt(compound)
        spiritIdDelegate.writeToNbt(compound)
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
        stateDelegate.writeToNbt(nbtTag)
        gemDelegate.writeToNbt(nbtTag)
        spiritIdDelegate.writeToNbt(nbtTag)
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

        val oldGem = gem
        val oldState = state

        stateDelegate.readFromNbt(compound)
        gemDelegate.readFromNbt(compound)
        spiritIdDelegate.readFromNbt(compound)

        if (state != oldState || gem != oldGem)
            world.markBlockRangeForRenderUpdate(pos, pos)
    }

    override fun getMultiblockInstance(): MultiBlockInstance? {
        return multiBlockInstance
    }

    override fun multiblockDeconstructed() {
        MultiblockWatcher.unRegisterController(this)
        state = SoulboundLecternState.INACTIVE
        spiritId = -1
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
        val possibleMultiblocks = soulShard.spirits.S.mapPairs { multiblocks[it.id] }.filterNotNull().toList()
        multiBlockInstance = possibleMultiblocks.map { it.second.makeMultiBlock(it.first, world, pos) }.firstOrNull { it != null }
        val instance = multiBlockInstance
        if (instance != null) {
            spiritId = instance.spirit.id
            MultiblockWatcher.registerController(this)
        }
    }

    override fun getControllerWorld(): World {
        return world
    }

    private fun updateState() {
        val stack = shardStack
        if (stack.isEmpty)
            return
        val soulShard = stack.item as ItemSoulShard
        gem = when (soulShard.name) {
            "end" -> SoulboundLecternGem.END
            "mind" -> SoulboundLecternGem.MIND
            "nether" -> SoulboundLecternGem.NETHER
            "peace" -> SoulboundLecternGem.PEACE
            "will" -> SoulboundLecternGem.WILL
            "undeath" -> SoulboundLecternGem.UNDEATH
            "water" -> SoulboundLecternGem.WATER
            "wild" -> SoulboundLecternGem.WILD
            "wither" -> SoulboundLecternGem.WITHER
            else -> SoulboundLecternGem.NONE
        }

        if (multiBlockInstance == null)
            state = SoulboundLecternState.INACTIVE
        else
            state = SoulboundLecternState.ACTIVE
    }

    companion object {
        private val gemProperty = NbtPropertyEnum("GemLectern", SoulboundLecternGem.NONE, SoulboundLecternGem, listOf("tag_GemLectern"))
        private val stateProperty = NbtPropertyEnum("StateLectern", SoulboundLecternState.INACTIVE, SoulboundLecternState, listOf("tag_StateLectern"))
        private val spiritProperty = NbtPropertyInt("SpiritId", -1, listOf("tag_SpiritId"))

        private const val TAG_SHARD = "Shard"

        private val multiblocks = HashMap<Int, ModMultiBlock>()

        init {
            multiblocks[ModSpirits.arbow.id] = ModMultiBlocks.soulbound_lectern_arbow
            multiblocks[ModSpirits.blizrabi.id] = ModMultiBlocks.soulbound_lectern_blizrabi
            multiblocks[ModSpirits.endererer.id] = ModMultiBlocks.soulbound_lectern_endererer
            multiblocks[ModSpirits.loon.id] = ModMultiBlocks.soulbound_lectern_loon
            multiblocks[ModSpirits.materia.id] = ModMultiBlocks.soulbound_lectern_materia
            multiblocks[ModSpirits.neblaze.id] = ModMultiBlocks.soulbound_lectern_neblaze
            multiblocks[ModSpirits.redwind.id] = ModMultiBlocks.soulbound_lectern_redwind
            multiblocks[ModSpirits.romol.id] = ModMultiBlocks.soulbound_lectern_romol
            multiblocks[ModSpirits.squarefury.id] = ModMultiBlocks.soulbound_lectern_squarefury
            multiblocks[ModSpirits.timber.id] = ModMultiBlocks.soulbound_lectern_timber
        }
    }
}