package aurocosh.divinefavor.common.item.bathing_blend.base;

import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ItemBathingBlendSimple extends ItemBathingBlend {
    private final int duration;
    private final int rate;

    public ItemBathingBlendSimple(String name, int duration, int rate) {
        super(name);
        this.duration = duration;
        this.rate = rate;
    }

    public abstract void applyEffect(EntityLivingBase livingBase);

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public ItemStack makeStack(int count) {
        ItemStack stack = new ItemStack(this, count);
        NBTTagCompound nbt = UtilNbt.getNbt(stack);
        nbt.setInteger(TAG_RATE, rate);
        nbt.setInteger(TAG_DURATION, duration);
        return stack;
    }
}