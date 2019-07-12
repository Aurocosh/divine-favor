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
import aurocosh.divinefavor.common.custom_data.player.data.undo_data.BlockOperationsData

// The default implementation of the capability. Holds all the logic.
class DefaultPlayerDataHandler : IPlayerDataHandler {
    override val spiritData: SpiritData = SpiritData()

    override val armorCorrosionData: ArmorCorrosionData = ArmorCorrosionData()
    override val crawlingMistData: CrawlingMistData = CrawlingMistData()
    override val evilEyeData: EvilEyeData = EvilEyeData()
    override val redFuryData: RedFuryData = RedFuryData()

    override val blockOperationsData: BlockOperationsData = BlockOperationsData()
    override val escapePlanData: EscapePlanData = EscapePlanData()
    override val focusedFuryData: FocusedFuryData = FocusedFuryData()
    override val gillsData: GillsData = GillsData()
    override val grudgeData: GrudgeData = GrudgeData()
    override val interactionData: InteractionData = InteractionData()
    override val moltenSkinData: MoltenSkinData = MoltenSkinData()
    override val pearlCrumbsData: PearlCrumbsData = PearlCrumbsData()
    override val templateData: TemplateData = TemplateData()
    override val vengefulBladeData: VengefulBladeData = VengefulBladeData()

    override val arborealAuraData: ArborealAuraData = ArborealAuraData()
    override val callingAuraData: CallingAuraData = CallingAuraData()
    override val charredAuraData: CharredAuraData = CharredAuraData()
    override val distortedAuraData: DistortedAuraData = DistortedAuraData()
    override val energeticAuraData: EnergeticAuraData = EnergeticAuraData()
    override val frostyAuraData: FrostyAuraData = FrostyAuraData()
    override val huntersAuraData: HuntersAuraData = HuntersAuraData()
    override val mineralAuraData: MineralAuraData = MineralAuraData()
    override val visceralAuraData: VisceralAuraData = VisceralAuraData()

    override val energeticPresenceData: EnergeticPresenceData = EnergeticPresenceData()
    override val furiousPresenceData: FuriousPresenceData = FuriousPresenceData()
    override val industriousPresenceData: IndustriousPresenceData = IndustriousPresenceData()
    override val manipulativePresenceData: ManipulativePresenceData = ManipulativePresenceData()
    override val materialPresenceData: MaterialPresenceData = MaterialPresenceData()
    override val predatoryPresenceData: PredatoryPresenceData = PredatoryPresenceData()
    override val scorchingPresenceData: ScorchingPresenceData = ScorchingPresenceData()
    override val toweringPresenceData: ToweringPresenceData = ToweringPresenceData()
    override val warpingPresenceData: WarpingPresenceData = WarpingPresenceData()
}
