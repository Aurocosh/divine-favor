package aurocosh.divinefavor.common.entity.rope

import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.config.common.ConfigRope
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase
import aurocosh.divinefavor.common.item.common.ModRopes
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.punishment.NeblazePunishment
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class EntityRopeLuminousNode(world: World) : EntityRopeNodeBase(world) {
    private var sourcesLeft: Int = ConfigRope.luminousRope.ligtSourcesPerNode
    private var counter: LoopedCounter = LoopedCounter(ConfigRope.luminousRope.lighSourceDelay)

    override val entityClass: Class<out EntityRopeNodeBase>
        get() = EntityRopeLuminousNode::class.java

    override val isEmittingLight: Boolean
        get() = true

    override fun onEntityUpdate() {
        super.onEntityUpdate()

        if (world.isRemote)
            return
        if (sourcesLeft <= 0)
            return
        if (!counter.tick())
            return

        sourcesLeft--
        placeLightSource()

        if(sourcesLeft == 0)
            setDead()
    }

    private fun placeLightSource() {
        val radius = ConfigRope.luminousRope.lighSourceRadius
        val blockPos = UtilCoordinates.getRandomBlockInRange(position, radius, BLOCK_SEARCH_LIMIT) { !world.isAirOrReplacable(it) }
                ?: return

        val blockState = ModBlocks.luminousRopeSource.defaultState
        val finalPos = BlockPosConstants.DIRECT_NEIGHBOURS.S.map(blockPos::add).firstOrNull(world::isAirOrReplacable)
                ?: return

        world.setBlockState(finalPos, blockState)
    }

    override fun getLightBlock(): Block {
        return ModBlocks.luminousRopeLight
    }

    override fun registerPickUp(player: EntityPlayer) {
        UtilPlayer.addStackToInventoryOrDrop(player, ItemStack(ModRopes.rope_luminous, 1))
    }

    override fun canDropNewNode(player: EntityPlayer): Boolean {
        val (slotIndex, stack) = UtilPlayer.findStackInInventory(player) { element -> !element.isEmpty && element.item === ModRopes.rope_luminous }
        if (slotIndex == -1)
            return false
        stack.shrink(1)
        player.inventory.setInventorySlotContents(slotIndex, if (stack.count > 0) stack else ItemStack.EMPTY)
        return true
    }

    override fun makeNewNode(world: World): EntityRopeNodeBase {
        return EntityRopeLuminousNode(world)
    }

    override fun readEntityFromNBT(nbt: NBTTagCompound) {
        super.readEntityFromNBT(nbt)
        sourcesLeft = nbt.getInteger(TAG_LIGHTS_LEFT)
    }

    override fun writeEntityToNBT(nbt: NBTTagCompound) {
        super.writeEntityToNBT(nbt)
        nbt.setInteger(TAG_LIGHTS_LEFT, sourcesLeft)
    }

    companion object {
        const val TAG_LIGHTS_LEFT = "LightsLeft"
        const val BLOCK_SEARCH_LIMIT = 60
    }
}