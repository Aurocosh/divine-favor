package aurocosh.divinefavor.common.custom_data.player.capability;

import aurocosh.divinefavor.common.custom_data.player.data.aura.arboreal.ArborealAuraDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.aura.calling.CallingAuraDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.aura.charred.CharredAuraDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.aura.distorted.DistortedAuraDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.curse.corrosion.ArmorCorrosionDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.curse.crawling_mist.CrawlingMistDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.aura.energetic.EnergeticAuraDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.curse.evil_eye.EvilEyeDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.presence.energetic.EnergeticPresenceDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.presence.manipulative.ManipulativePresenceDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.spell.escape_plan.EscapePlanDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.spell.focused_fury.FocusedFuryDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.aura.frosty.FrostyAuraDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.presence.furious.FuriousPresenceDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.spell.gills.GillsDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.spell.grudge.GrudgeDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.aura.hunters.HuntersAuraDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.interaction_handler.InteractionDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.spell.molten_skin.MoltenSkinDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.spell.pearl_crumbs.PearlCrumbsDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.presence.predatory.PredatoryPresenceDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.presence.scorching.ScorchingPresenceDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.presence.towering.ToweringPresenceDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.aura.visceral.VisceralAuraDataSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class PlayerDataStorage implements Capability.IStorage<IPlayerDataHandler> {
    private static final ArmorCorrosionDataSerializer ARMOR_CORROSION_DATA_SERIALIZER = new ArmorCorrosionDataSerializer();
    private static final CrawlingMistDataSerializer CRAWLING_MIST_DATA_SERIALIZER = new CrawlingMistDataSerializer();
    private static final EscapePlanDataSerializer ESCAPE_PLAN_DATA_SERIALIZER = new EscapePlanDataSerializer();
    private static final EvilEyeDataSerializer EVIL_EYE_DATA_SERIALIZER = new EvilEyeDataSerializer();
    private static final FavorDataSerializer FAVOR_DATA_SERIALIZER = new FavorDataSerializer();
    private static final FocusedFuryDataSerializer FOCUSED_FURY_DATA_SERIALIZER = new FocusedFuryDataSerializer();
    private static final GillsDataSerializer GILLS_DATA_SERIALIZER = new GillsDataSerializer();
    private static final GrudgeDataSerializer GRUDGE_DATA_SERIALIZER = new GrudgeDataSerializer();
    private static final InteractionDataSerializer INTERACTION_DATA_SERIALIZER = new InteractionDataSerializer();
    private static final MoltenSkinDataSerializer MOLTEN_SKIN_DATA_SERIALIZER = new MoltenSkinDataSerializer();
    private static final PearlCrumbsDataSerializer PEARL_CRUMBS_DATA_SERIALIZER = new PearlCrumbsDataSerializer();

    private static final ArborealAuraDataSerializer ARBOREAL_AURA_DATA_SERIALIZER = new ArborealAuraDataSerializer();
    private static final CallingAuraDataSerializer CALLING_AURA_DATA_SERIALIZER = new CallingAuraDataSerializer();
    private static final CharredAuraDataSerializer CHARRED_AURA_DATA_SERIALIZER = new CharredAuraDataSerializer();
    private static final DistortedAuraDataSerializer DISTORTED_AURA_DATA_SERIALIZER = new DistortedAuraDataSerializer();
    private static final EnergeticAuraDataSerializer ENERGETIC_AURA_DATA_SERIALIZER = new EnergeticAuraDataSerializer();
    private static final FrostyAuraDataSerializer FROSTY_AURA_DATA_SERIALIZER = new FrostyAuraDataSerializer();
    private static final HuntersAuraDataSerializer HUNTERS_AURA_DATA_SERIALIZER = new HuntersAuraDataSerializer();
    private static final VisceralAuraDataSerializer VISCERAL_AURA_DATA_SERIALIZER = new VisceralAuraDataSerializer();

    private static final EnergeticPresenceDataSerializer ENERGETIC_PRESENCE_DATA_SERIALIZER = new EnergeticPresenceDataSerializer();
    private static final FuriousPresenceDataSerializer FURIOUS_PRESENCE_DATA_SERIALIZER = new FuriousPresenceDataSerializer();
    private static final ManipulativePresenceDataSerializer MANIPULATIVE_PRESENCE_DATA_SERIALIZER = new ManipulativePresenceDataSerializer();
    private static final PredatoryPresenceDataSerializer PREDATORY_PRESENCE_DATA_SERIALIZER = new PredatoryPresenceDataSerializer();
    private static final ScorchingPresenceDataSerializer SCORCHING_PRESENCE_DATA_SERIALIZER = new ScorchingPresenceDataSerializer();
    private static final ToweringPresenceDataSerializer TOWERING_PRESENCE_DATA_SERIALIZER = new ToweringPresenceDataSerializer();

    @Override
    public NBTBase writeNBT(Capability<IPlayerDataHandler> capability, IPlayerDataHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        ARMOR_CORROSION_DATA_SERIALIZER.serialize(tag, instance.getArmorCorrosionData());
        CRAWLING_MIST_DATA_SERIALIZER.serialize(tag, instance.getCrawlingMistData());
        ESCAPE_PLAN_DATA_SERIALIZER.serialize(tag, instance.getEscapePlanData());
        EVIL_EYE_DATA_SERIALIZER.serialize(tag, instance.getEvilEyeData());
        FAVOR_DATA_SERIALIZER.serialize(tag, instance.getFavorData());
        FOCUSED_FURY_DATA_SERIALIZER.serialize(tag, instance.getFocusedFuryData());
        GILLS_DATA_SERIALIZER.serialize(tag, instance.getGillsData());
        GRUDGE_DATA_SERIALIZER.serialize(tag, instance.getGrudgeData());
        INTERACTION_DATA_SERIALIZER.serialize(tag, instance.getInteractionData());
        MOLTEN_SKIN_DATA_SERIALIZER.serialize(tag, instance.getMoltenSkinData());
        PEARL_CRUMBS_DATA_SERIALIZER.serialize(tag, instance.getPearlCrumbsData());

        ARBOREAL_AURA_DATA_SERIALIZER.serialize(tag, instance.getArborealAuraData());
        CALLING_AURA_DATA_SERIALIZER.serialize(tag, instance.getCallingAuraData());
        CHARRED_AURA_DATA_SERIALIZER.serialize(tag, instance.getCharredAuraData());
        DISTORTED_AURA_DATA_SERIALIZER.serialize(tag, instance.getDistortedAuraData());
        ENERGETIC_AURA_DATA_SERIALIZER.serialize(tag, instance.getEnergeticAuraData());
        FROSTY_AURA_DATA_SERIALIZER.serialize(tag, instance.getFrostyAuraData());
        HUNTERS_AURA_DATA_SERIALIZER.serialize(tag, instance.getHuntersAuraData());
        VISCERAL_AURA_DATA_SERIALIZER.serialize(tag, instance.getVisceralAuraData());

        ENERGETIC_PRESENCE_DATA_SERIALIZER.serialize(tag, instance.getEnergeticPresenceData());
        FURIOUS_PRESENCE_DATA_SERIALIZER.serialize(tag, instance.getFuriousPresenceData());
        MANIPULATIVE_PRESENCE_DATA_SERIALIZER.serialize(tag, instance.getManipulativePresenceData());
        PREDATORY_PRESENCE_DATA_SERIALIZER.serialize(tag, instance.getPredatoryPresenceData());
        SCORCHING_PRESENCE_DATA_SERIALIZER.serialize(tag, instance.getScorchingPresenceData());
        TOWERING_PRESENCE_DATA_SERIALIZER.serialize(tag, instance.getToweringPresenceData());
        return tag;
    }

    @Override
    public void readNBT(Capability<IPlayerDataHandler> capability, IPlayerDataHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        ARMOR_CORROSION_DATA_SERIALIZER.deserialize(tag, instance.getArmorCorrosionData());
        CRAWLING_MIST_DATA_SERIALIZER.deserialize(tag, instance.getCrawlingMistData());
        ESCAPE_PLAN_DATA_SERIALIZER.deserialize(tag, instance.getEscapePlanData());
        EVIL_EYE_DATA_SERIALIZER.deserialize(tag, instance.getEvilEyeData());
        FAVOR_DATA_SERIALIZER.deserialize(tag, instance.getFavorData());
        FOCUSED_FURY_DATA_SERIALIZER.deserialize(tag, instance.getFocusedFuryData());
        GILLS_DATA_SERIALIZER.deserialize(tag, instance.getGillsData());
        GRUDGE_DATA_SERIALIZER.deserialize(tag, instance.getGrudgeData());
        INTERACTION_DATA_SERIALIZER.deserialize(tag, instance.getInteractionData());
        MOLTEN_SKIN_DATA_SERIALIZER.deserialize(tag, instance.getMoltenSkinData());
        PEARL_CRUMBS_DATA_SERIALIZER.deserialize(tag, instance.getPearlCrumbsData());

        ARBOREAL_AURA_DATA_SERIALIZER.deserialize(tag, instance.getArborealAuraData());
        CALLING_AURA_DATA_SERIALIZER.deserialize(tag, instance.getCallingAuraData());
        CHARRED_AURA_DATA_SERIALIZER.deserialize(tag, instance.getCharredAuraData());
        DISTORTED_AURA_DATA_SERIALIZER.deserialize(tag, instance.getDistortedAuraData());
        ENERGETIC_AURA_DATA_SERIALIZER.deserialize(tag, instance.getEnergeticAuraData());
        FROSTY_AURA_DATA_SERIALIZER.deserialize(tag, instance.getFrostyAuraData());
        HUNTERS_AURA_DATA_SERIALIZER.deserialize(tag, instance.getHuntersAuraData());
        VISCERAL_AURA_DATA_SERIALIZER.deserialize(tag, instance.getVisceralAuraData());

        ENERGETIC_PRESENCE_DATA_SERIALIZER.deserialize(tag, instance.getEnergeticPresenceData());
        FURIOUS_PRESENCE_DATA_SERIALIZER.deserialize(tag, instance.getFuriousPresenceData());
        MANIPULATIVE_PRESENCE_DATA_SERIALIZER.deserialize(tag, instance.getManipulativePresenceData());
        PREDATORY_PRESENCE_DATA_SERIALIZER.deserialize(tag, instance.getPredatoryPresenceData());
        SCORCHING_PRESENCE_DATA_SERIALIZER.deserialize(tag, instance.getScorchingPresenceData());
        TOWERING_PRESENCE_DATA_SERIALIZER.deserialize(tag, instance.getToweringPresenceData());
    }
}
