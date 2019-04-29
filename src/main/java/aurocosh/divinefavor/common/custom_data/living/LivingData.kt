package aurocosh.divinefavor.common.custom_data.living

import aurocosh.divinefavor.common.custom_data.living.capability.ILivingDataHandler
import aurocosh.divinefavor.common.custom_data.living.capability.LivingDataDataHandler
import net.minecraft.entity.EntityLivingBase

// The default implementation of the capability. Holds all the logic.
object LivingData {
    operator fun get(livingBase: EntityLivingBase): ILivingDataHandler {
        return livingBase.getCapability(LivingDataDataHandler.CAPABILITY_LIVING_DATA!!, null)!!
    }
}
