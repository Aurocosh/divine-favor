package aurocosh.divinefavor.common.block.bath_heater

import aurocosh.divinefavor.common.area.IAreaWatcher
import aurocosh.divinefavor.common.area.WorldArea
import aurocosh.divinefavor.common.area.WorldAreaWatcher
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlend
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.isWater
import aurocosh.divinefavor.common.lib.wrapper.ConvertingPredicate
import aurocosh.divinefavor.common.util.UtilBlockPos
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.ITickable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import java.util.*

class TileBathHeater : TileEntity(), ITickable, IAreaWatcher {
    var clientProgressEffect: Int = 0
    var clientProgressBurning: Int = 0

    var progressBurning: Int = 0
        private set
    var progressEffect: Int = 0
        private set

    private var currentBurnTime: Int = 0
    private var maxBurnTime: Int = 0
    private var maxEffectTime: Int = 0
    private var currentEffectTime: Int = 0
    private var activeBlend: ItemBathingBlend? = null

    private var refresh: Boolean = false
    private var initialized: Boolean = false

    private val area: WorldArea
    private val loopedCounter: LoopedCounter
    private val waterPositions: MutableSet<BlockPos>

    private var _state = BathHeaterState.INACTIVE
    var state: BathHeaterState
        set(value) {
            if (_state == value)
                return
            _state = value
            markDirty()
            val blockState = world.getBlockState(pos)
            world.notifyBlockUpdate(pos, blockState, blockState, 3)
        }
        get() = _state

    private val fuelStackHandler = object : ItemStackHandler(1) {
        override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
            return TileEntityFurnace.getItemBurnTime(stack) > 0
        }

        override fun onContentsChanged(slot: Int) {
            markDirty()
        }
    }

    private val blendStackHandler = object : ItemStackHandler(1) {
        override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
            return stack.item is ItemBathingBlend
        }

        override fun onContentsChanged(slot: Int) {
            markDirty()
        }
    }

    val isBurning: Boolean
        get() = state == BathHeaterState.ACTIVE

    override fun getAreaWorld(): World {
        return world
    }

    init {
        progressBurning = 0
        clientProgressBurning = 0
        maxBurnTime = 0
        currentBurnTime = 0

        maxEffectTime = 0
        currentEffectTime = 0
        activeBlend = null

        initialized = false
        refresh = true
        area = WorldArea()
        waterPositions = HashSet()
        loopedCounter = LoopedCounter()
    }

    fun initialize() {
        if (initialized)
            return
        initialized = true
        area.addPositions(UtilCoordinates.getBlocksInRadius(pos, RADIUS_OF_EFFECT, 0, RADIUS_OF_EFFECT))
        WorldAreaWatcher.registerWatcher(this)
    }

    private fun refreshWaterBlocks() {
        if (!refresh)
            return
        refresh = false
        waterPositions.clear()
        val posVector = BlockPos(pos)
        val start = ArrayList<BlockPos>()
        start.add(posVector.add(BlockPosConstants.EAST))
        start.add(posVector.add(BlockPosConstants.WEST))
        start.add(posVector.add(BlockPosConstants.NORTH))
        start.add(posVector.add(BlockPosConstants.SOUTH))

        val predicate = ConvertingPredicate(world::getBlock, Block::isWater)::invoke
        val waterPosList = UtilCoordinates.floodFill(start, BlockPosConstants.HORIZONTAL_DIRECT, predicate, 50)
        waterPositions.addAll(waterPosList.S.filter(area::isApartOfArea))

        val blockState = world.getBlockState(pos)
        world.notifyBlockUpdate(pos, blockState, blockState, 3)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        maxBurnTime = compound.getInteger(TAG_MAX_BURN_TIME)
        currentBurnTime = compound.getInteger(TAG_CURRENT_BURN_TIME)
        progressBurning = if (maxBurnTime == 0) 0 else currentBurnTime / maxBurnTime
        _state = BathHeaterState[compound.getInteger(TAG_STATE_HEATER)]
        waterPositions.clear()
        waterPositions.addAll(UtilBlockPos.deserialize(compound.getIntArray(TAG_WATER_POSITIONS)))
        loopedCounter.tickRate = compound.getInteger(TAG_EFFECT_TICK_RATE)
        if (compound.hasKey(TAG_FUEL))
            fuelStackHandler.deserializeNBT(compound.getTag(TAG_FUEL) as NBTTagCompound)
        if (compound.hasKey(TAG_INGREDIENTS))
            blendStackHandler.deserializeNBT(compound.getTag(TAG_INGREDIENTS) as NBTTagCompound)
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(compound)
        compound.setInteger(TAG_MAX_BURN_TIME, maxBurnTime)
        compound.setInteger(TAG_CURRENT_BURN_TIME, currentBurnTime)
        compound.setInteger(TAG_STATE_HEATER, state.ordinal)
        compound.setIntArray(TAG_WATER_POSITIONS, UtilBlockPos.serialize(waterPositions))
        compound.setInteger(TAG_EFFECT_TICK_RATE, loopedCounter.tickRate)
        compound.setTag(TAG_FUEL, fuelStackHandler.serializeNBT())
        compound.setTag(TAG_INGREDIENTS, blendStackHandler.serializeNBT())
        return compound
    }

    fun isUsableByPlayer(playerIn: EntityPlayer): Boolean {
        // If we are too far away from this tile entity you cannot gainFavor it
        return !isInvalid && playerIn.getDistanceSq(pos.add(0.5, 0.5, 0.5)) <= 64.0
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return if (capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) facing == EnumFacing.UP || facing == EnumFacing.DOWN else super.hasCapability(capability, facing)
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast<T>(blendStackHandler)
            else if (facing == EnumFacing.DOWN)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast<T>(fuelStackHandler)
        }
        return super.getCapability(capability, facing)
    }

    override fun getUpdateTag(): NBTTagCompound {
        val nbtTag = super.getUpdateTag()
        nbtTag.setInteger(TAG_STATE_HEATER, state.ordinal)
        nbtTag.setIntArray(TAG_WATER_POSITIONS, UtilBlockPos.serialize(waterPositions))
        return nbtTag
    }

    override fun getUpdatePacket(): SPacketUpdateTileEntity? {
        return SPacketUpdateTileEntity(pos, 1, updateTag)
    }

    override fun onDataPacket(net: NetworkManager?, packet: SPacketUpdateTileEntity) {
        val stateIndex = packet.nbtCompound.getInteger(TAG_STATE_HEATER)

        if (!world.isRemote)
            return
        if (stateIndex != state.ordinal) {
            state = BathHeaterState[stateIndex]
            world.markBlockRangeForRenderUpdate(pos, pos)
        }
        waterPositions.clear()
        waterPositions.addAll(UtilBlockPos.deserialize(packet.nbtCompound.getIntArray(TAG_WATER_POSITIONS)))
    }

    override fun update() {
        initialize()
        if (!world.isRemote) {
            refreshWaterBlocks()
            burn()
            applyEffect()
        } else
            bubble()
    }

    private fun bubble() {
        if (state == BathHeaterState.INACTIVE)
            return
        for (waterPos in waterPositions) {
            val random = UtilRandom.random
            world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, waterPos.x + random.nextDouble(), waterPos.y + random.nextDouble(), waterPos.z + random.nextDouble(), 0.0, 0.0, 0.0)
        }
    }

    private fun burn() {
        if (currentBurnTime > 0) {
            currentBurnTime--
            progressBurning = (currentBurnTime / maxBurnTime.toFloat() * 100).toInt()
            state = BathHeaterState.ACTIVE
            markDirty()
            return
        } else if (currentBurnTime == 0) {
            val stack = fuelStackHandler.getStackInSlot(0)
            if (!stack.isEmpty) {
                maxBurnTime = TileEntityFurnace.getItemBurnTime(stack)
                currentBurnTime = maxBurnTime
                progressBurning = MAX_PROGRESS
                stack.shrink(1)
                state = BathHeaterState.ACTIVE
                markDirty()
                return
            }
        }
        progressBurning = 0
        state = BathHeaterState.INACTIVE
    }

    private fun applyEffect() {
        if (!isBurning)
            return
        if (!loopedCounter.tick())
            return
        if (currentEffectTime <= 0) {
            val stack = blendStackHandler.getStackInSlot(0)
            if (!stack.isEmpty) {
                val item = stack.item
                if (item is ItemBathingBlend) {
                    activeBlend = item
                    maxEffectTime = item.duration
                    currentEffectTime = maxEffectTime
                    loopedCounter.tickRate = item.rate
                    progressEffect = 100
                    stack.shrink(1)
                    markDirty()
                }
            }
        } else {
            currentEffectTime -= loopedCounter.tickRate
            progressEffect = (currentEffectTime / maxEffectTime.toFloat() * 100).toInt()
            markDirty()

            val boundingBox = UtilCoordinates.getBoundingBox(pos, RADIUS_OF_EFFECT.toDouble())
            val list = world.getEntitiesWithinAABB(EntityLivingBase::class.java, boundingBox) { e -> e != null && e.isInWater }
            for (livingBase in list)
                activeBlend!!.applyEffect(livingBase)
        }
    }

    override fun getArea(): WorldArea {
        return area
    }

    override fun blockChanged(world: World, pos: BlockPos, state: IBlockState) {
        refresh = true
    }

    companion object {
        const val FUEL_SIZE = 1
        const val INGREDIENTS_SIZE = 1

        private const val MAX_PROGRESS = 100
        private const val RADIUS_OF_EFFECT = 3

        private const val TAG_FUEL = "FUEL"
        private const val TAG_MAX_BURN_TIME = "MaxBurnTime"
        private const val TAG_CURRENT_BURN_TIME = "CurrentBurnTime"
        private const val TAG_INGREDIENTS = "Ingredients"
        private const val TAG_STATE_HEATER = "StateHeater"
        private const val TAG_WATER_POSITIONS = "WaterPositions"
        private const val TAG_EFFECT_TICK_RATE = "EffectTickRate"
    }
}