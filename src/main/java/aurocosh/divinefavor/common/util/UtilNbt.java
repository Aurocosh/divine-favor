package aurocosh.divinefavor.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class UtilNbt {
    public static NBTTagCompound getNbt(ItemStack stack) {
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

//    {
//        Integer valuerOrDefault = getValuerOrDefault(new NBTTagCompound(), "", NBTTagCompound::getInteger, 1);
//    }

    public static <T> T getValuerOrDefault(NBTTagCompound compound, String tag, ValueExtractor<T> extractor, T fallback) {
        if (compound.hasKey(tag))
            return extractor.next(compound, tag);
        else
            return fallback;
    }

    public static void setBlockPos(NBTTagCompound compound, String tag, BlockPos value) {
        compound.setLong(tag, value.toLong());
    }

    public static BlockPos getBlockPos(NBTTagCompound compound, String tag) {
        return BlockPos.fromLong(compound.getLong(tag));
    }

    public static void setVec3d(NBTTagCompound compound, String tagPrefix, Vec3d vector) {
        compound.setDouble(tagPrefix + "X", vector.x);
        compound.setDouble(tagPrefix + "Y", vector.y);
        compound.setDouble(tagPrefix + "Z", vector.z);
    }

    public static Vec3d getVec3d(NBTTagCompound compound, String tagPrefix) {
        double x = compound.getDouble(tagPrefix + "X");
        double y = compound.getDouble(tagPrefix + "Y");
        double z = compound.getDouble(tagPrefix + "Z");
        return new Vec3d(x, y, z);
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