package aurocosh.divinefavor.common.block.bath_heater

import aurocosh.divinefavor.common.item.base.GenericContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerBathHeater(player: EntityPlayer, private val bathHeater: TileBathHeater) : GenericContainer(TileBathHeater.FUEL_SIZE + TileBathHeater.INGREDIENTS_SIZE) {

    init {
        val blendHandler = this.bathHeater.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)
        this.addSlotToContainer(SlotItemHandler(blendHandler, 0, 80, 23))

        val fuelHandler = this.bathHeater.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)
        this.addSlotToContainer(SlotItemHandler(fuelHandler, 0, 80, 58))

        generateInventorySlots(player.inventory, 8, 84)
        generateHotbarSlots(player.inventory, 8, 142)
    }

    override fun canInteractWith(player: EntityPlayer): Boolean {
        return bathHeater.isUsableByPlayer(player)
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()
        if (bathHeater.progressBurning != bathHeater.clientProgressBurning) {
            bathHeater.clientProgressBurning = bathHeater.progressBurning
            for (listener in listeners)
                listener.sendWindowProperty(this, PROGRESS_BURNING_ID, bathHeater.progressBurning)
        }
        if (bathHeater.progressEffect != bathHeater.clientProgressEffect) {
            bathHeater.clientProgressEffect = bathHeater.progressEffect
            for (listener in listeners)
                listener.sendWindowProperty(this, PROGRESS_EFFECT_ID, bathHeater.progressEffect)
        }
    }

    override fun updateProgressBar(id: Int, data: Int) {
        if (id == PROGRESS_BURNING_ID)
            bathHeater.clientProgressBurning = data
        else if (id == PROGRESS_EFFECT_ID)
            bathHeater.clientProgressEffect = data
    }

    companion object {
        private const val PROGRESS_BURNING_ID = 0
        private const val PROGRESS_EFFECT_ID = 1
    }
}