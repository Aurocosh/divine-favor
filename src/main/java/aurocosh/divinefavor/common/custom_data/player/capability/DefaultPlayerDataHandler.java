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
    private final DistortedAuraData distortedAuraData;
    private final EnergeticAuraData energeticAuraData;
    private final FrostyAuraData frostyAuraData;
    private final HuntersAuraData huntersAuraData;
    private final VisceralAuraData visceralAuraData;

    private final EnergeticPresenceData energeticPresenceData;
    private final FuriousPresenceData furiousPresenceData;
    private final PredatoryPresenceData predatoryPresenceData;
    private final ScorchingPresenceData scorchingPresenceData;
    private final ToweringPresenceData toweringPresenceData;
    private final WarpingPresenceData warpingPresenceData;

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
        distortedAuraData = new DistortedAuraData();
        energeticAuraData = new EnergeticAuraData();
        frostyAuraData = new FrostyAuraData();
        huntersAuraData = new HuntersAuraData();
        visceralAuraData = new VisceralAuraData();

        energeticPresenceData = new EnergeticPresenceData();
        furiousPresenceData = new FuriousPresenceData();
        predatoryPresenceData = new PredatoryPresenceData();
        scorchingPresenceData = new ScorchingPresenceData();
        toweringPresenceData = new ToweringPresenceData();
        warpingPresenceData = new WarpingPresenceData();
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
    public DistortedAuraData getDistortedAuraData() {
        return distortedAuraData;
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

    @Override
    public WarpingPresenceData getWarpingPresenceData() {
        return warpingPresenceData;
    }
}
