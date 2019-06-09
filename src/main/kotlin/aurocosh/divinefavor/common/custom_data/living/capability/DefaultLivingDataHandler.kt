package aurocosh.divinefavor.common.custom_data.living.capability

import aurocosh.divinefavor.common.custom_data.living.data.cripple.CrippleData
import aurocosh.divinefavor.common.custom_data.living.data.curse.CurseData
import aurocosh.divinefavor.common.custom_data.living.data.extra_looting.ExtraLootingData
import aurocosh.divinefavor.common.custom_data.living.data.limp_leg.LimpLegData
import aurocosh.divinefavor.common.custom_data.living.data.petrification.PetrificationData
import aurocosh.divinefavor.common.custom_data.living.data.soul_theft.SoulTheftData
import aurocosh.divinefavor.common.custom_data.living.data.suffocating_fumes.SuffocatingFumesData
import aurocosh.divinefavor.common.custom_data.living.data.wind_leash.WindLeashData

// The default implementation of the capability. Holds all the logic.
class DefaultLivingDataHandler : ILivingDataHandler {
    override val crippleData: CrippleData = CrippleData()
    override val curseData: CurseData = CurseData()
    override val extraLootingData: ExtraLootingData = ExtraLootingData()
    override val limpLegData: LimpLegData = LimpLegData()
    override val petrificationData: PetrificationData = PetrificationData()
    override val soulTheftData: SoulTheftData = SoulTheftData()
    override val suffocatingFumesData: SuffocatingFumesData = SuffocatingFumesData()
    override val windLeashData: WindLeashData = WindLeashData()
}
