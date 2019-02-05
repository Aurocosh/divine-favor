package aurocosh.divinefavor.common.custom_data.player.capability;

import aurocosh.divinefavor.common.custom_data.player.data.arboreal_aura.ArborealAuraData;
import aurocosh.divinefavor.common.custom_data.player.data.charred_aura.CharredAuraData;
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
import aurocosh.divinefavor.common.custom_data.player.data.towering_presence.ToweringPresenceData;

// The default implementation of the capability. Holds all the logic.
public class DefaultPlayerDataHandler implements IPlayerDataHandler {
    private final ArborealAuraData arborealAuraData;
    private final ArmorCorrosionData armorCorrosionData;
    private final CharredAuraData charredAuraData;
    private final CrawlingMistData crawlingMistData;
    private final EscapePlanData escapePlanData;
    private final FavorData favorData;
    private final FocusedFuryData focusedFuryData;
    private final GillsData gillsData;
    private final GrudgeData grudgeData;
    private final InteractionData interactionData;
    private final MoltenSkinData moltenSkinData;
    private final PearlCrumbsData pearlCrumbsData;
    private final ScorchingPresenceData scorchingPresenceData;
    private final ToweringPresenceData toweringPresenceData;


    public DefaultPlayerDataHandler() {
        arborealAuraData = new ArborealAuraData();
        armorCorrosionData = new ArmorCorrosionData();
        charredAuraData = new CharredAuraData();
        crawlingMistData = new CrawlingMistData();
        escapePlanData = new EscapePlanData();
        favorData = new FavorData();
        focusedFuryData = new FocusedFuryData();
        gillsData = new GillsData();
        grudgeData = new GrudgeData();
        interactionData = new InteractionData();
        moltenSkinData = new MoltenSkinData();
        pearlCrumbsData = new PearlCrumbsData();
        scorchingPresenceData = new ScorchingPresenceData();
        toweringPresenceData = new ToweringPresenceData();
    }

    @Override
    public ArborealAuraData getArborealAuraData() {
        return arborealAuraData;
    }

    @Override
    public ArmorCorrosionData getArmorCorrosionData() {
        return armorCorrosionData;
    }

    @Override
    public CharredAuraData getCharredAuraData() {
        return charredAuraData;
    }

    @Override
    public CrawlingMistData getCrawlingMistData() {
        return crawlingMistData;
    }

    @Override
    public EscapePlanData getEscapePlanData() {
        return escapePlanData;
    }

    @Override
    public FocusedFuryData getFocusedFuryData() {
        return focusedFuryData;
    }

    @Override
    public GillsData getGillsData() {
        return gillsData;
    }

    @Override
    public GrudgeData getGrudgeData() {
        return grudgeData;
    }

    @Override
    public InteractionData getInteractionData() {
        return interactionData;
    }

    @Override
    public MoltenSkinData getMoltenSkinData() {
        return moltenSkinData;
    }

    @Override
    public PearlCrumbsData getPearlCrumbsData() {
        return pearlCrumbsData;
    }

    @Override
    public ScorchingPresenceData getScorchingPresenceData() {
        return scorchingPresenceData;
    }

    @Override
    public ToweringPresenceData getToweringPresenceData() {
        return toweringPresenceData;
    }

    @Override
    public FavorData getFavorData() {
        return favorData;
    }
}
