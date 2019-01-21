package aurocosh.divinefavor.common.custom_data.living.crawling_mist;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CrawlingMistProvider implements ICapabilitySerializable<NBTTagCompound> {
    ICrawlingMistHandler instance = CrawlingMistDataHandler.CAPABILITY_CRAWLING_MIST.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CrawlingMistDataHandler.CAPABILITY_CRAWLING_MIST;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? CrawlingMistDataHandler.CAPABILITY_CRAWLING_MIST.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) CrawlingMistDataHandler.CAPABILITY_CRAWLING_MIST.getStorage().writeNBT(CrawlingMistDataHandler.CAPABILITY_CRAWLING_MIST, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        CrawlingMistDataHandler.CAPABILITY_CRAWLING_MIST.getStorage().readNBT(CrawlingMistDataHandler.CAPABILITY_CRAWLING_MIST, instance, null, nbt);
    }
}
