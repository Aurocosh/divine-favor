package aurocosh.divinefavor.common.custom_data.player.capability;

import aurocosh.divinefavor.common.custom_data.player.data.corrosion.ArmorCorrosionData;
import aurocosh.divinefavor.common.custom_data.player.data.crawling_mist.CrawlingMistData;
import aurocosh.divinefavor.common.custom_data.player.data.escape_plan.EscapePlanData;
import aurocosh.divinefavor.common.custom_data.player.data.focused_fury.FocusedFuryData;
import aurocosh.divinefavor.common.custom_data.player.data.gills.GillsData;
import aurocosh.divinefavor.common.custom_data.player.data.grudge.GrudgeData;
import aurocosh.divinefavor.common.custom_data.player.data.interaction_handler.InteractionData;
import aurocosh.divinefavor.common.custom_data.player.data.molten_skin.MoltenSkinData;
import aurocosh.divinefavor.common.custom_data.player.data.pearl_crumbs.PearlCrumbsData;
import aurocosh.divinefavor.common.custom_data.player.data.talisman_uses.TalismanUsesData;

// The default implementation of the capability. Holds all the logic.
public class DefaultPlayerDataHandler implements IPlayerDataHandler {
    private final ArmorCorrosionData armorCorrosionData;
    private final CrawlingMistData crawlingMistData;
    private final EscapePlanData escapePlanData;
    private final FocusedFuryData focusedFuryData;
    private final GillsData gillsData;
    private final GrudgeData grudgeData;
    private final InteractionData interactionData;
    private final MoltenSkinData moltenSkinData;
    private final PearlCrumbsData pearlCrumbsData;
    private final TalismanUsesData talismanUsesData;

    public DefaultPlayerDataHandler() {
        armorCorrosionData = new ArmorCorrosionData();
        crawlingMistData = new CrawlingMistData();
        escapePlanData = new EscapePlanData();
        focusedFuryData = new FocusedFuryData();
        gillsData = new GillsData();
        grudgeData = new GrudgeData();
        interactionData = new InteractionData();
        moltenSkinData = new MoltenSkinData();
        pearlCrumbsData = new PearlCrumbsData();
        talismanUsesData = new TalismanUsesData();
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
    public TalismanUsesData getTalismanUsesData() {
        return talismanUsesData;
    }
}
