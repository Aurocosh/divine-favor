package aurocosh.divinefavor.common.custom_data.living.capability;

import aurocosh.divinefavor.common.custom_data.living.data.curse.CurseData;
import aurocosh.divinefavor.common.custom_data.living.data.limp_leg.LimpLegData;
import aurocosh.divinefavor.common.custom_data.living.data.petrification.PetrificationData;
import aurocosh.divinefavor.common.custom_data.living.data.suffocating_fumes.SuffocatingFumesData;
import aurocosh.divinefavor.common.custom_data.living.data.wind_leash.WindLeashData;

// The default implementation of the capability. Holds all the logic.
public class DefaultLivingDataHandler implements ILivingDataHandler {
    private final CurseData curse;
    private final LimpLegData limpLegData;
    private final PetrificationData petrificationData;
    private final SuffocatingFumesData suffocatingFumesData;
    private final WindLeashData windLeashData;

    public DefaultLivingDataHandler() {
        curse = new CurseData();
        limpLegData = new LimpLegData();
        petrificationData = new PetrificationData();
        suffocatingFumesData = new SuffocatingFumesData();
        windLeashData = new WindLeashData();
    }

    @Override
    public CurseData getCurseData() {
        return curse;
    }

    @Override
    public LimpLegData getLimpLegData() {
        return limpLegData;
    }

    @Override
    public PetrificationData getPetrificationData() {
        return petrificationData;
    }

    @Override
    public SuffocatingFumesData getSuffocatingFumesData() {
        return suffocatingFumesData;
    }

    @Override
    public WindLeashData getWindLeashData() {
        return windLeashData;
    }
}
