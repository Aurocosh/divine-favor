package aurocosh.divinefavor.common.custom_data.living.capability

import aurocosh.divinefavor.common.custom_data.living.data.cripple.CrippleData
import aurocosh.divinefavor.common.custom_data.living.data.curse.CurseData
import aurocosh.divinefavor.common.custom_data.living.data.limp_leg.LimpLegData
import aurocosh.divinefavor.common.custom_data.living.data.petrification.PetrificationData
import aurocosh.divinefavor.common.custom_data.living.data.soul_theft.SoulTheftData
import aurocosh.divinefavor.common.custom_data.living.data.suffocating_fumes.SuffocatingFumesData
import aurocosh.divinefavor.common.custom_data.living.data.wind_leash.WindLeashData

// The default implementation of the capability. Holds all the logic.
class DefaultLivingDataHandler : ILivingDataHandler {
    private val crippleData: CrippleData = CrippleData()
    private val curse: CurseData = CurseData()
    private val limpLegData: LimpLegData = LimpLegData()
    private val petrificationData: PetrificationData = PetrificationData()
    private val soulTheftData: SoulTheftData = SoulTheftData()
    private val suffocatingFumesData: SuffocatingFumesData = SuffocatingFumesData()
    private val windLeashData: WindLeashData = WindLeashData()

    override fun getCrippleData(): CrippleData {
        return crippleData
    }

    override fun getCurseData(): CurseData {
        return curse
    }

    override fun getLimpLegData(): LimpLegData {
        return limpLegData
    }

    override fun getPetrificationData(): PetrificationData {
        return petrificationData
    }

    override fun getSoulTheftData(): SoulTheftData {
        return soulTheftData
    }

    override fun getSuffocatingFumesData(): SuffocatingFumesData {
        return suffocatingFumesData
    }

    override fun getWindLeashData(): WindLeashData {
        return windLeashData
    }
}
