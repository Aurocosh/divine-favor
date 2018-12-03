package aurocosh.divinefavor.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;


public class UtilNbt {
    public static NBTTagCompound getEistingOrNewNBT(ItemStack stack) {
        if (stack.hasTagCompound())
            return stack.getTagCompound();

        NBTTagCompound nbt = new NBTTagCompound();
        stack.setTagCompound(nbt);
        return nbt;
    }

    public static boolean checkForTag(ItemStack stack, String tag) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(tag);
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
}