package aurocosh.divinefavor.common.custom_data.living;

import aurocosh.divinefavor.common.custom_data.living.capability.ILivingDataHandler;
import net.minecraft.entity.EntityLivingBase;

import static aurocosh.divinefavor.common.custom_data.living.capability.LivingDataDataHandler.CAPABILITY_POTION_STATUS;

// The default implementation of the capability. Holds all the logic.
public class LivingData {
    public static ILivingDataHandler get(EntityLivingBase livingBase){
        return livingBase.getCapability(CAPABILITY_POTION_STATUS, null);
    }
}
