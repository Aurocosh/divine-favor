package aurocosh.divinefavor.common.custom_data.living.capability

import aurocosh.divinefavor.common.custom_data.living.data.cripple.CrippleData
import aurocosh.divinefavor.common.custom_data.living.data.curse.CurseData
import aurocosh.divinefavor.common.custom_data.living.data.limp_leg.LimpLegData
import aurocosh.divinefavor.common.custom_data.living.data.petrification.PetrificationData
import aurocosh.divinefavor.common.custom_data.living.data.soul_theft.SoulTheftData
import aurocosh.divinefavor.common.custom_data.living.data.suffocating_fumes.SuffocatingFumesData
import aurocosh.divinefavor.common.custom_data.living.data.wind_leash.WindLeashData

interface ILivingDataHandler {
    val crippleData: CrippleData
    val curseData: CurseData
    val limpLegData: LimpLegData
    val petrificationData: PetrificationData
    val soulTheftData: SoulTheftData
    val suffocatingFumesData: SuffocatingFumesData
    val windLeashData: WindLeashData
}
