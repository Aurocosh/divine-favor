package aurocosh.divinefavor.common.custom_data.player.capability;

import aurocosh.divinefavor.common.custom_data.player.data.arboreal_aura.ArborealAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.charred_aura.CharredAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.corrosion.ArmorCorrosionData;
import aurocosh.divinefavor.common.custom_data.player.data.crawling_mist.CrawlingMistData;
import aurocosh.divinefavor.common.custom_data.player.data.energetic_aura.EnergeticAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.energetic_presence.EnergeticPresenceData;
import aurocosh.divinefavor.common.custom_data.player.data.escape_plan.EscapePlanData;
import aurocosh.divinefavor.common.custom_data.player.data.focused_fury.FocusedFuryData;
import aurocosh.divinefavor.common.custom_data.player.data.frosty_aura.FrostyAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.furious_presence.FuriousPresenceData;
import aurocosh.divinefavor.common.custom_data.player.data.gills.GillsData;
import aurocosh.divinefavor.common.custom_data.player.data.grudge.GrudgeData;
import aurocosh.divinefavor.common.custom_data.player.data.hunters_aura.HuntersAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.interaction_handler.InteractionData;
import aurocosh.divinefavor.common.custom_data.player.data.molten_skin.MoltenSkinData;
import aurocosh.divinefavor.common.custom_data.player.data.pearl_crumbs.PearlCrumbsData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.custom_data.player.data.predatory_presence.PredatoryPresenceData;
import aurocosh.divinefavor.common.custom_data.player.data.scorching_presence.ScorchingPresenceData;
import aurocosh.divinefavor.common.custom_data.player.data.towering_presence.ToweringPresenceData;
import aurocosh.divinefavor.common.custom_data.player.data.visceral_aura.VisceralAuraData;

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
    EnergeticAuraData getEnergeticAuraData();
    FrostyAuraData getFrostyAuraData();
    HuntersAuraData getHuntersAuraData();
    VisceralAuraData getVisceralAuraData();

    EnergeticPresenceData getEnergeticPresenceData();
    FuriousPresenceData getFuriousPresenceData();
    PredatoryPresenceData getPredatoryPresenceData();
    ScorchingPresenceData getScorchingPresenceData();
    ToweringPresenceData getToweringPresenceData();
}
