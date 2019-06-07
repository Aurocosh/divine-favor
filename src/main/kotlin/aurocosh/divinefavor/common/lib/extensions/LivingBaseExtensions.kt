package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.custom_data.living.capability.ILivingDataHandler
import aurocosh.divinefavor.common.custom_data.living.capability.LivingDataDataHandler
import net.minecraft.entity.EntityLivingBase

val EntityLivingBase.divineLivingData: ILivingDataHandler
    get() = this.getCapability(LivingDataDataHandler.CAPABILITY_LIVING_DATA, null) as ILivingDataHandler

