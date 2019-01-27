package aurocosh.divinefavor.common.custom_data.player.capability;

import aurocosh.divinefavor.common.custom_data.player.data.corrosion.ArmorCorrosionDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.crawling_mist.CrawlingMistDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.escape_plan.EscapePlanDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.focused_fury.FocusedFuryDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.gills.GillsDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.grudge.GrudgeDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.interaction_handler.InteractionDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.molten_skin.MoltenSkinDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.pearl_crumbs.PearlCrumbsDataSerializer;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorDataSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class PlayerDataStorage implements Capability.IStorage<IPlayerDataHandler> {
    private static final ArmorCorrosionDataSerializer armorCorrosionDataSerializer = new ArmorCorrosionDataSerializer();
    private static final CrawlingMistDataSerializer crawlingMistDataSerializer = new CrawlingMistDataSerializer();
    private static final EscapePlanDataSerializer escapePlanDataSerializer = new EscapePlanDataSerializer();
    private static final FocusedFuryDataSerializer focusedFuryDataSerializer = new FocusedFuryDataSerializer();
    private static final GillsDataSerializer gillsDataSerializer = new GillsDataSerializer();
    private static final GrudgeDataSerializer grudgeDataSerializer = new GrudgeDataSerializer();
    private static final InteractionDataSerializer interactionDataSerializer = new InteractionDataSerializer();
    private static final MoltenSkinDataSerializer moltenSkinDataSerializer = new MoltenSkinDataSerializer();
    private static final PearlCrumbsDataSerializer pearlCrumbsDataSerializer = new PearlCrumbsDataSerializer();
    private static final FavorDataSerializer FAVOR_DATA_SERIALIZER = new FavorDataSerializer();

    @Override
    public NBTBase writeNBT(Capability<IPlayerDataHandler> capability, IPlayerDataHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        armorCorrosionDataSerializer.serialize(tag, instance.getArmorCorrosionData());
        crawlingMistDataSerializer.serialize(tag, instance.getCrawlingMistData());
        escapePlanDataSerializer.serialize(tag, instance.getEscapePlanData());
        focusedFuryDataSerializer.serialize(tag, instance.getFocusedFuryData());
        gillsDataSerializer.serialize(tag, instance.getGillsData());
        grudgeDataSerializer.serialize(tag, instance.getGrudgeData());
        interactionDataSerializer.serialize(tag, instance.getInteractionData());
        moltenSkinDataSerializer.serialize(tag, instance.getMoltenSkinData());
        pearlCrumbsDataSerializer.serialize(tag, instance.getPearlCrumbsData());
        FAVOR_DATA_SERIALIZER.serialize(tag, instance.getFavorData());
        return tag;
    }

    @Override
    public void readNBT(Capability<IPlayerDataHandler> capability, IPlayerDataHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        armorCorrosionDataSerializer.deserialize(tag, instance.getArmorCorrosionData());
        crawlingMistDataSerializer.deserialize(tag, instance.getCrawlingMistData());
        escapePlanDataSerializer.deserialize(tag, instance.getEscapePlanData());
        focusedFuryDataSerializer.deserialize(tag, instance.getFocusedFuryData());
        gillsDataSerializer.deserialize(tag, instance.getGillsData());
        grudgeDataSerializer.deserialize(tag, instance.getGrudgeData());
        interactionDataSerializer.deserialize(tag, instance.getInteractionData());
        moltenSkinDataSerializer.deserialize(tag, instance.getMoltenSkinData());
        pearlCrumbsDataSerializer.deserialize(tag, instance.getPearlCrumbsData());
        FAVOR_DATA_SERIALIZER.deserialize(tag, instance.getFavorData());
    }
}
