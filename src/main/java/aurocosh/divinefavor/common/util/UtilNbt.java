package aurocosh.divinefavor.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class UtilNbt {
    public static NBTTagCompound getTag(ItemStack stack) {
        if (stack.hasTagCompound())
            return stack.getTagCompound();

        NBTTagCompound nbt = new NBTTagCompound();
        stack.setTagCompound(nbt);
        return nbt;
    }

    public static boolean checkForTag(ItemStack stack, String tag) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(tag);
    }

    public static boolean checkForTags(NBTTagCompound nbt, String... tags) {
       for (String tag : tags)
            if (!nbt.hasKey(tag))
                return false;
        return true;
    }

    public static boolean checkForTags(ItemStack stack, String... tags) {
        if (!stack.hasTagCompound())
            return false;
        NBTTagCompound nbt = stack.getTagCompound();
        for (String tag : tags)
            if (!nbt.hasKey(tag))
                return false;
        return true;
    }

    public static void setBlockPos(NBTTagCompound compound, String tag, BlockPos value) {
        compound.setLong(tag, value.toLong());
    }

    public static BlockPos getBlockPos(NBTTagCompound compound, String tag){
        return BlockPos.fromLong(compound.getLong(tag));
    }

    public static <T> T getFirstExistingValue(NBTTagCompound nbt, ValueExtractor<T> extractor, T fallback, String... tags) {
        for (String tag : tags)
            if (nbt.hasKey(tag))
                return extractor.next(nbt, tag);
        return fallback;
    }


    @FunctionalInterface
    public interface ValueExtractor<T> {
        T next(NBTTagCompound compound, String tag);
    }
}