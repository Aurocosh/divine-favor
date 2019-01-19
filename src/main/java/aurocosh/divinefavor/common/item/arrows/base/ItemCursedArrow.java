package aurocosh.divinefavor.common.item.arrows.base;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.entity.projectile.EntityCursedArrow;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.util.UtilNbt;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCursedArrow extends ModItemArrow {
    public static final String TAG_DURATION = "Duration";
    public static final int DURATION_DEFAULT = UtilTick.secondsToTicks(15);

    private final ModPotion potion;

    private final int color;

    public ItemCursedArrow(String name, ModPotion potion, int color) {
        super("cursed_arrow_" + name, "cursed_arrows/" + name);
        this.potion = potion;
        this.color = color;

//        setMaxStackSize(64);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    public int getColor() {
        return color;
    }

    public ModPotion getPotion() {
        return potion;
    }

    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        EntityCursedArrow arrow = new EntityCursedArrow(worldIn, shooter);
        arrow.setPotionEffect(stack);
        return arrow;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (!UtilNbt.checkForTag(stack, TAG_DURATION))
            return;
        NBTTagCompound nbt = stack.getTagCompound();
        int duration = nbt.getInteger(TAG_DURATION);

        String info = I18n.translateToLocal("cursed_arrow.duration").trim() + duration;
        tooltip.add(TextFormatting.GRAY + info);
    }

    public static void setDuration(ItemStack stack, int duration) {
        NBTTagCompound nbt = UtilNbt.getTag(stack);
        nbt.setInteger(TAG_DURATION, duration);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!this.isInCreativeTab(tab))
            return;
        ItemStack stack = new ItemStack(this, 1);
        setDuration(stack, DURATION_DEFAULT);
        items.add(stack);
    }
}
