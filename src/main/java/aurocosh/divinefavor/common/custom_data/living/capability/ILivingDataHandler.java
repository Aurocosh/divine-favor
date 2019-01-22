package aurocosh.divinefavor.common.custom_data.living.capability;

import aurocosh.divinefavor.common.custom_data.living.data.curse.CurseData;
import aurocosh.divinefavor.common.custom_data.living.data.limp_leg.LimpLegData;
import aurocosh.divinefavor.common.custom_data.living.data.petrification.PetrificationData;
import aurocosh.divinefavor.common.custom_data.living.data.suffocating_fumes.SuffocatingFumesData;
import aurocosh.divinefavor.common.custom_data.living.data.wind_leash.WindLeashData;

public interface ILivingDataHandler {
    CurseData getCurseData();
    LimpLegData getLimpLegData();
    PetrificationData getPetrificationData();
    SuffocatingFumesData getSuffocatingFumesData();
    WindLeashData getWindLeashData();
}
