package aurocosh.divinefavor.common.item.bone_dagger;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigItem;
import aurocosh.divinefavor.common.constants.ConstMainTabOrder;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.util.UtilNbt;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemBoneDagger extends ModItem {
    private static final String TAG_AWAKENING_CHANCE = "AWAKENING_CHANCE";

    public ItemBoneDagger() {
        super("bone_dagger", "bone_dagger", ConstMainTabOrder.DAGGERS);
        setMaxStackSize(1);
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (player.world.isRemote)
            return false;
        if (!(entity instanceof EntityLivingBase))
            return false;
        NBTTagCompound nbt = UtilNbt.getNbt(stack);
        float chance = nbt.getFloat(TAG_AWAKENING_CHANCE);
        if (UtilRandom.rollDiceFloat(chance)) {
            stack.shrink(1);
            player.addItemStackToInventory(new ItemStack(ModItems.bone_dagger_awakened, 1));
        }
        nbt.setFloat(TAG_AWAKENING_CHANCE, chance + ConfigItem.boneDagger.awakeningSpeed);
        return false;
    }


    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}
