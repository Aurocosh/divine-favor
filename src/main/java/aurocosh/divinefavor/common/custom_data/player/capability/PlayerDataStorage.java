package aurocosh.divinefavor.common.custom_data.player.capability;

import aurocosh.divinefavor.common.custom_data.player.data.arboreal_aura.ArborealAuraDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.charred_aura.CharredAuraDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.corrosion.ArmorCorrosionDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.crawling_mist.CrawlingMistDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.escape_plan.EscapePlanDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.focused_fury.FocusedFuryDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.frosty_aura.FrostyAuraDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.gills.GillsDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.grudge.GrudgeDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.interaction_handler.InteractionDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.molten_skin.MoltenSkinDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.pearl_crumbs.PearlCrumbsDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.scorching_presence.ScorchingPresenceDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.towering_presence.ToweringPresenceDataSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class PlayerDataStorage implements Capability.IStorage<IPlayerDataHandler> {
    private static final ArborealAuraDataSerializer ARBOREAL_AURA_DATA_SERIALIZER = new ArborealAuraDataSerializer();
    private static final ArmorCorrosionDataSerializer ARMOR_CORROSION_DATA_SERIALIZER = new ArmorCorrosionDataSerializer();
    private static final CharredAuraDataSerializer CHARRED_AURA_DATA_SERIALIZER = new CharredAuraDataSerializer();
    private static final CrawlingMistDataSerializer CRAWLING_MIST_DATA_SERIALIZER = new CrawlingMistDataSerializer();
    private static final EscapePlanDataSerializer ESCAPE_PLAN_DATA_SERIALIZER = new EscapePlanDataSerializer();
    private static final FavorDataSerializer FAVOR_DATA_SERIALIZER = new FavorDataSerializer();
    private static final FocusedFuryDataSerializer FOCUSED_FURY_DATA_SERIALIZER = new FocusedFuryDataSerializer();
    private static final FrostyAuraDataSerializer FROSTY_AURA_DATA_SERIALIZER = new FrostyAuraDataSerializer();
    private static final GillsDataSerializer GILLS_DATA_SERIALIZER = new GillsDataSerializer();
    private static final GrudgeDataSerializer GRUDGE_DATA_SERIALIZER = new GrudgeDataSerializer();
    private static final InteractionDataSerializer INTERACTION_DATA_SERIALIZER = new InteractionDataSerializer();
    private static final MoltenSkinDataSerializer MOLTEN_SKIN_DATA_SERIALIZER = new MoltenSkinDataSerializer();
    private static final PearlCrumbsDataSerializer PEARL_CRUMBS_DATA_SERIALIZER = new PearlCrumbsDataSerializer();
    private static final ScorchingPresenceDataSerializer SCORCHING_PRESENCE_DATA_SERIALIZER = new ScorchingPresenceDataSerializer();
    private static final ToweringPresenceDataSerializer TOWERING_PRESENCE_DATA_SERIALIZER = new ToweringPresenceDataSerializer();

    @Override
    public NBTBase writeNBT(Capability<IPlayerDataHandler> capability, IPlayerDataHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        ARBOREAL_AURA_DATA_SERIALIZER.serialize(tag, instance.getArborealAuraData());
        ARMOR_CORROSION_DATA_SERIALIZER.serialize(tag, instance.getArmorCorrosionData());
        CHARRED_AURA_DATA_SERIALIZER.serialize(tag, instance.getCharredAuraData());
        CRAWLING_MIST_DATA_SERIALIZER.serialize(tag, instance.getCrawlingMistData());
        ESCAPE_PLAN_DATA_SERIALIZER.serialize(tag, instance.getEscapePlanData());
        FAVOR_DATA_SERIALIZER.serialize(tag, instance.getFavorData());
        FOCUSED_FURY_DATA_SERIALIZER.serialize(tag, instance.getFocusedFuryData());
        FROSTY_AURA_DATA_SERIALIZER.serialize(tag, instance.getFrostyAuraData());
        GILLS_DATA_SERIALIZER.serialize(tag, instance.getGillsData());
        GRUDGE_DATA_SERIALIZER.serialize(tag, instance.getGrudgeData());
        INTERACTION_DATA_SERIALIZER.serialize(tag, instance.getInteractionData());
        MOLTEN_SKIN_DATA_SERIALIZER.serialize(tag, instance.getMoltenSkinData());
        PEARL_CRUMBS_DATA_SERIALIZER.serialize(tag, instance.getPearlCrumbsData());
        SCORCHING_PRESENCE_DATA_SERIALIZER.serialize(tag, instance.getScorchingPresenceData());
        TOWERING_PRESENCE_DATA_SERIALIZER.serialize(tag, instance.getToweringPresenceData());
        return tag;
    }

    @Override
    public void readNBT(Capability<IPlayerDataHandler> capability, IPlayerDataHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        ARBOREAL_AURA_DATA_SERIALIZER.deserialize(tag, instance.getArborealAuraData());
        ARMOR_CORROSION_DATA_SERIALIZER.deserialize(tag, instance.getArmorCorrosionData());
        CHARRED_AURA_DATA_SERIALIZER.deserialize(tag, instance.getCharredAuraData());
        CRAWLING_MIST_DATA_SERIALIZER.deserialize(tag, instance.getCrawlingMistData());
        ESCAPE_PLAN_DATA_SERIALIZER.deserialize(tag, instance.getEscapePlanData());
        FAVOR_DATA_SERIALIZER.deserialize(tag, instance.getFavorData());
        FOCUSED_FURY_DATA_SERIALIZER.deserialize(tag, instance.getFocusedFuryData());
        FROSTY_AURA_DATA_SERIALIZER.deserialize(tag, instance.getFrostyAuraData());
        GILLS_DATA_SERIALIZER.deserialize(tag, instance.getGillsData());
        GRUDGE_DATA_SERIALIZER.deserialize(tag, instance.getGrudgeData());
        INTERACTION_DATA_SERIALIZER.deserialize(tag, instance.getInteractionData());
        MOLTEN_SKIN_DATA_SERIALIZER.deserialize(tag, instance.getMoltenSkinData());
        PEARL_CRUMBS_DATA_SERIALIZER.deserialize(tag, instance.getPearlCrumbsData());
        SCORCHING_PRESENCE_DATA_SERIALIZER.deserialize(tag, instance.getScorchingPresenceData());
        TOWERING_PRESENCE_DATA_SERIALIZER.deserialize(tag, instance.getToweringPresenceData());
    }
}
