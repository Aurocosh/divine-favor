package aurocosh.divinefavor.common.custom_data.player.capability;

import aurocosh.divinefavor.common.custom_data.player.data.burnt_smell.BurntSmellData;
import aurocosh.divinefavor.common.custom_data.player.data.corrosion.ArmorCorrosionData;
import aurocosh.divinefavor.common.custom_data.player.data.crawling_mist.CrawlingMistData;
import aurocosh.divinefavor.common.custom_data.player.data.escape_plan.EscapePlanData;
import aurocosh.divinefavor.common.custom_data.player.data.focused_fury.FocusedFuryData;
import aurocosh.divinefavor.common.custom_data.player.data.gills.GillsData;
import aurocosh.divinefavor.common.custom_data.player.data.grudge.GrudgeData;
import aurocosh.divinefavor.common.custom_data.player.data.interaction_handler.InteractionData;
import aurocosh.divinefavor.common.custom_data.player.data.molten_skin.MoltenSkinData;
import aurocosh.divinefavor.common.custom_data.player.data.pearl_crumbs.PearlCrumbsData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.custom_data.player.data.scorching_presence.ScorchingPresenceData;

public interface IPlayerDataHandler {
    ArmorCorrosionData getArmorCorrosionData();
    BurntSmellData getBurntSmellData();
    CrawlingMistData getCrawlingMistData();
    EscapePlanData getEscapePlanData();
    FavorData getFavorData();
    FocusedFuryData getFocusedFuryData();
    GillsData getGillsData();
    GrudgeData getGrudgeData();
    InteractionData getInteractionData();
    MoltenSkinData getMoltenSkinData();
    PearlCrumbsData getPearlCrumbsData();
    ScorchingPresenceData getScorchingPresenceData();
}
