package aurocosh.divinefavor.common.item.bathing_blend.base;

import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ItemBathingBlendSimple extends ItemBathingBlend {
    private final int baseDuration;
    private final float basePotency;

    public ItemBathingBlendSimple(String name, int baseDuration, float basePotency) {
        super(name);
        this.baseDuration = baseDuration;
        this.basePotency = basePotency;
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
        nbt.setFloat(TAG_POTENCY, basePotency);
        nbt.setInteger(TAG_DURATION, baseDuration);
        return stack;
    }
}