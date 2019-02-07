package aurocosh.divinefavor.common.custom_data.player.capability;

import aurocosh.divinefavor.common.custom_data.player.data.aura.arboreal.ArborealAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.charred.CharredAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.curse.corrosion.ArmorCorrosionData;
import aurocosh.divinefavor.common.custom_data.player.data.curse.crawling_mist.CrawlingMistData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.distorted.DistortedAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.energetic.EnergeticAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.energetic.EnergeticPresenceData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.warping.WarpingPresenceData;
import aurocosh.divinefavor.common.custom_data.player.data.spell.escape_plan.EscapePlanData;
import aurocosh.divinefavor.common.custom_data.player.data.spell.focused_fury.FocusedFuryData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.frosty.FrostyAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.furious.FuriousPresenceData;
import aurocosh.divinefavor.common.custom_data.player.data.spell.gills.GillsData;
import aurocosh.divinefavor.common.custom_data.player.data.spell.grudge.GrudgeData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.hunters.HuntersAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.interaction_handler.InteractionData;
import aurocosh.divinefavor.common.custom_data.player.data.spell.molten_skin.MoltenSkinData;
import aurocosh.divinefavor.common.custom_data.player.data.spell.pearl_crumbs.PearlCrumbsData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.predatory.PredatoryPresenceData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.scorching.ScorchingPresenceData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.towering.ToweringPresenceData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.visceral.VisceralAuraData;

public interface IPlayerDataHandler {
    ArmorCorrosionData getArmorCorrosionData();
    CrawlingMistData getCrawlingMistData();
    EscapePlanData getEscapePlanData();
    FavorData getFavorData();
    FocusedFuryData getFocusedFuryData();
    GillsData getGillsData();
    GrudgeData getGrudgeData();
    InteractionData getInteractionData();
    MoltenSkinData getMoltenSkinData();
    PearlCrumbsData getPearlCrumbsData();

    ArborealAuraData getArborealAuraData();
    CharredAuraData getCharredAuraData();
    DistortedAuraData getDistortedAuraData();
    EnergeticAuraData getEnergeticAuraData();
    FrostyAuraData getFrostyAuraData();
    HuntersAuraData getHuntersAuraData();
    VisceralAuraData getVisceralAuraData();

    EnergeticPresenceData getEnergeticPresenceData();
    FuriousPresenceData getFuriousPresenceData();
    PredatoryPresenceData getPredatoryPresenceData();
    ScorchingPresenceData getScorchingPresenceData();
    ToweringPresenceData getToweringPresenceData();
    WarpingPresenceData getWarpingPresenceData();
}
