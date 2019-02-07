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

// The default implementation of the capability. Holds all the logic.
public class DefaultPlayerDataHandler implements IPlayerDataHandler {
    private final ArmorCorrosionData armorCorrosionData;
    private final CrawlingMistData crawlingMistData;
    private final EscapePlanData escapePlanData;
    private final FavorData favorData;
    private final FocusedFuryData focusedFuryData;
    private final GillsData gillsData;
    private final GrudgeData grudgeData;
    private final InteractionData interactionData;
    private final MoltenSkinData moltenSkinData;
    private final PearlCrumbsData pearlCrumbsData;

    private final ArborealAuraData arborealAuraData;
    private final CharredAuraData charredAuraData;
    private final EnergeticAuraData energeticAuraData;
    private final FrostyAuraData frostyAuraData;
    private final HuntersAuraData huntersAuraData;
    private final VisceralAuraData visceralAuraData;

    private final EnergeticPresenceData energeticPresenceData;
    private final FuriousPresenceData furiousPresenceData;
    private final PredatoryPresenceData predatoryPresenceData;
    private final ScorchingPresenceData scorchingPresenceData;
    private final ToweringPresenceData toweringPresenceData;

    public DefaultPlayerDataHandler() {
        armorCorrosionData = new ArmorCorrosionData();
        crawlingMistData = new CrawlingMistData();
        escapePlanData = new EscapePlanData();
        favorData = new FavorData();
        focusedFuryData = new FocusedFuryData();
        gillsData = new GillsData();
        grudgeData = new GrudgeData();
        interactionData = new InteractionData();
        moltenSkinData = new MoltenSkinData();
        pearlCrumbsData = new PearlCrumbsData();

        arborealAuraData = new ArborealAuraData();
        charredAuraData = new CharredAuraData();
        energeticAuraData = new EnergeticAuraData();
        frostyAuraData = new FrostyAuraData();
        huntersAuraData = new HuntersAuraData();
        visceralAuraData = new VisceralAuraData();

        energeticPresenceData = new EnergeticPresenceData();
        furiousPresenceData = new FuriousPresenceData();
        predatoryPresenceData = new PredatoryPresenceData();
        scorchingPresenceData = new ScorchingPresenceData();
        toweringPresenceData = new ToweringPresenceData();
    }

    @Override
    public ArmorCorrosionData getArmorCorrosionData() {
        return armorCorrosionData;
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
    public FavorData getFavorData() {
        return favorData;
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
    public ArborealAuraData getArborealAuraData() {
        return arborealAuraData;
    }

    @Override
    public CharredAuraData getCharredAuraData() {
        return charredAuraData;
    }

    @Override
    public EnergeticAuraData getEnergeticAuraData() {
        return energeticAuraData;
    }

    @Override
    public FrostyAuraData getFrostyAuraData() {
        return frostyAuraData;
    }

    @Override
    public HuntersAuraData getHuntersAuraData() {
        return huntersAuraData;
    }

    @Override
    public VisceralAuraData getVisceralAuraData() {
        return visceralAuraData;
    }

    @Override
    public EnergeticPresenceData getEnergeticPresenceData() {
        return energeticPresenceData;
    }

    @Override
    public FuriousPresenceData getFuriousPresenceData() {
        return furiousPresenceData;
    }

    @Override
    public PredatoryPresenceData getPredatoryPresenceData() {
        return predatoryPresenceData;
    }

    @Override
    public ScorchingPresenceData getScorchingPresenceData() {
        return scorchingPresenceData;
    }

    @Override
    public ToweringPresenceData getToweringPresenceData() {
        return toweringPresenceData;
    }
}
