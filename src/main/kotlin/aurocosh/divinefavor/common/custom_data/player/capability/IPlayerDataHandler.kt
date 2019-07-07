package aurocosh.divinefavor.common.custom_data.player.capability

import aurocosh.divinefavor.common.custom_data.player.data.aura.arboreal.ArborealAuraData
import aurocosh.divinefavor.common.custom_data.player.data.aura.calling.CallingAuraData
import aurocosh.divinefavor.common.custom_data.player.data.aura.charred.CharredAuraData
import aurocosh.divinefavor.common.custom_data.player.data.aura.distorted.DistortedAuraData
import aurocosh.divinefavor.common.custom_data.player.data.aura.energetic.EnergeticAuraData
import aurocosh.divinefavor.common.custom_data.player.data.aura.frosty.FrostyAuraData
import aurocosh.divinefavor.common.custom_data.player.data.aura.hunters.HuntersAuraData
import aurocosh.divinefavor.common.custom_data.player.data.aura.mineral.MineralAuraData
import aurocosh.divinefavor.common.custom_data.player.data.aura.visceral.VisceralAuraData
import aurocosh.divinefavor.common.custom_data.player.data.curse.corrosion.ArmorCorrosionData
import aurocosh.divinefavor.common.custom_data.player.data.curse.crawling_mist.CrawlingMistData
import aurocosh.divinefavor.common.custom_data.player.data.curse.evil_eye.EvilEyeData
import aurocosh.divinefavor.common.custom_data.player.data.curse.red_fury.RedFuryData
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData
import aurocosh.divinefavor.common.custom_data.player.data.interaction_handler.InteractionData
import aurocosh.divinefavor.common.custom_data.player.data.materia.MaterialPresenceData
import aurocosh.divinefavor.common.custom_data.player.data.presence.energetic.EnergeticPresenceData
import aurocosh.divinefavor.common.custom_data.player.data.presence.furious.FuriousPresenceData
import aurocosh.divinefavor.common.custom_data.player.data.presence.industrious.IndustriousPresenceData
import aurocosh.divinefavor.common.custom_data.player.data.presence.manipulative.ManipulativePresenceData
import aurocosh.divinefavor.common.custom_data.player.data.presence.predatory.PredatoryPresenceData
import aurocosh.divinefavor.common.custom_data.player.data.presence.scorching.ScorchingPresenceData
import aurocosh.divinefavor.common.custom_data.player.data.presence.towering.ToweringPresenceData
import aurocosh.divinefavor.common.custom_data.player.data.presence.warping.WarpingPresenceData
import aurocosh.divinefavor.common.custom_data.player.data.spell.escape_plan.EscapePlanData
import aurocosh.divinefavor.common.custom_data.player.data.spell.focused_fury.FocusedFuryData
import aurocosh.divinefavor.common.custom_data.player.data.spell.gills.GillsData
import aurocosh.divinefavor.common.custom_data.player.data.spell.grudge.GrudgeData
import aurocosh.divinefavor.common.custom_data.player.data.spell.molten_skin.MoltenSkinData
import aurocosh.divinefavor.common.custom_data.player.data.spell.pearl_crumbs.PearlCrumbsData
import aurocosh.divinefavor.common.custom_data.player.data.spell.vengeful_blade.VengefulBladeData
import aurocosh.divinefavor.common.custom_data.player.data.templates.TemplateData

interface IPlayerDataHandler {
    val spiritData: SpiritData

    val armorCorrosionData: ArmorCorrosionData
    val crawlingMistData: CrawlingMistData
    val evilEyeData: EvilEyeData
    val redFuryData: RedFuryData

    val escapePlanData: EscapePlanData
    val focusedFuryData: FocusedFuryData
    val gillsData: GillsData
    val grudgeData: GrudgeData
    val interactionData: InteractionData
    val moltenSkinData: MoltenSkinData
    val pearlCrumbsData: PearlCrumbsData
    val templateData: TemplateData
    val vengefulBladeData: VengefulBladeData

    val arborealAuraData: ArborealAuraData
    val callingAuraData: CallingAuraData
    val charredAuraData: CharredAuraData
    val distortedAuraData: DistortedAuraData
    val energeticAuraData: EnergeticAuraData
    val frostyAuraData: FrostyAuraData
    val huntersAuraData: HuntersAuraData
    val mineralAuraData: MineralAuraData
    val visceralAuraData: VisceralAuraData

    val energeticPresenceData: EnergeticPresenceData
    val furiousPresenceData: FuriousPresenceData
    val industriousPresenceData: IndustriousPresenceData
    val manipulativePresenceData: ManipulativePresenceData
    val materialPresenceData: MaterialPresenceData
    val predatoryPresenceData: PredatoryPresenceData
    val scorchingPresenceData: ScorchingPresenceData
    val toweringPresenceData: ToweringPresenceData
    val warpingPresenceData: WarpingPresenceData
}
