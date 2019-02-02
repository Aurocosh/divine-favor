package aurocosh.divinefavor.common.custom_data.player.capability;

import aurocosh.divinefavor.common.custom_data.player.data.corrosion.ArmorCorrosionDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.crawling_mist.CrawlingMistDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.escape_plan.EscapePlanDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.focused_fury.FocusedFuryDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.gills.GillsDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.grudge.GrudgeDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.interaction_handler.InteractionDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.molten_skin.MoltenSkinDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.pearl_crumbs.PearlCrumbsDataSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class PlayerDataStorage implements Capability.IStorage<IPlayerDataHandler> {
    private static final ArmorCorrosionDataSerializer ARMOR_CORROSION_DATA_SERIALIZER = new ArmorCorrosionDataSerializer();
    private static final CrawlingMistDataSerializer CRAWLING_MIST_DATA_SERIALIZER = new CrawlingMistDataSerializer();
    private static final EscapePlanDataSerializer ESCAPE_PLAN_DATA_SERIALIZER = new EscapePlanDataSerializer();
    private static final FocusedFuryDataSerializer FOCUSED_FURY_DATA_SERIALIZER = new FocusedFuryDataSerializer();
    private static final GillsDataSerializer GILLS_DATA_SERIALIZER = new GillsDataSerializer();
    private static final GrudgeDataSerializer GRUDGE_DATA_SERIALIZER = new GrudgeDataSerializer();
    private static final InteractionDataSerializer INTERACTION_DATA_SERIALIZER = new InteractionDataSerializer();
    private static final MoltenSkinDataSerializer MOLTEN_SKIN_DATA_SERIALIZER = new MoltenSkinDataSerializer();
    private static final PearlCrumbsDataSerializer PEARL_CRUMBS_DATA_SERIALIZER = new PearlCrumbsDataSerializer();
    private static final FavorDataSerializer FAVOR_DATA_SERIALIZER = new FavorDataSerializer();

    @Override
    public NBTBase writeNBT(Capability<IPlayerDataHandler> capability, IPlayerDataHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        ARMOR_CORROSION_DATA_SERIALIZER.serialize(tag, instance.getArmorCorrosionData());
        CRAWLING_MIST_DATA_SERIALIZER.serialize(tag, instance.getCrawlingMistData());
        ESCAPE_PLAN_DATA_SERIALIZER.serialize(tag, instance.getEscapePlanData());
        FOCUSED_FURY_DATA_SERIALIZER.serialize(tag, instance.getFocusedFuryData());
        GILLS_DATA_SERIALIZER.serialize(tag, instance.getGillsData());
        GRUDGE_DATA_SERIALIZER.serialize(tag, instance.getGrudgeData());
        INTERACTION_DATA_SERIALIZER.serialize(tag, instance.getInteractionData());
        MOLTEN_SKIN_DATA_SERIALIZER.serialize(tag, instance.getMoltenSkinData());
        PEARL_CRUMBS_DATA_SERIALIZER.serialize(tag, instance.getPearlCrumbsData());
        FAVOR_DATA_SERIALIZER.serialize(tag, instance.getFavorData());
        return tag;
    }

    @Override
    public void readNBT(Capability<IPlayerDataHandler> capability, IPlayerDataHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        ARMOR_CORROSION_DATA_SERIALIZER.deserialize(tag, instance.getArmorCorrosionData());
        CRAWLING_MIST_DATA_SERIALIZER.deserialize(tag, instance.getCrawlingMistData());
        ESCAPE_PLAN_DATA_SERIALIZER.deserialize(tag, instance.getEscapePlanData());
        FOCUSED_FURY_DATA_SERIALIZER.deserialize(tag, instance.getFocusedFuryData());
        GILLS_DATA_SERIALIZER.deserialize(tag, instance.getGillsData());
        GRUDGE_DATA_SERIALIZER.deserialize(tag, instance.getGrudgeData());
        INTERACTION_DATA_SERIALIZER.deserialize(tag, instance.getInteractionData());
        MOLTEN_SKIN_DATA_SERIALIZER.deserialize(tag, instance.getMoltenSkinData());
        PEARL_CRUMBS_DATA_SERIALIZER.deserialize(tag, instance.getPearlCrumbsData());
        FAVOR_DATA_SERIALIZER.deserialize(tag, instance.getFavorData());
    }
}
