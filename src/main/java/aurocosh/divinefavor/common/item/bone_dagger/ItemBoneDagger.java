package aurocosh.divinefavor.common.item.bone_dagger;

import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTab;
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
    private static final float CHANCE_INCREASE = 0.03f;

    public ItemBoneDagger() {
        super("bone_dagger", "bone_dagger");
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
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
        nbt.setFloat(TAG_AWAKENING_CHANCE, chance + CHANCE_INCREASE);
        return false;
    }


    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}
