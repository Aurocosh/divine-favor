package aurocosh.divinefavor.common.item.soul_shards;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstGemTabOrder;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemSoulShard extends ModItem {
    protected static final String TAG_ENTITY_ID = "Id";
    protected static final String NBT_ENTITY_NAME = "EntityName";

    protected final ModSpirit spirit;

    public ItemSoulShard(String name, ModSpirit spirit) {
        super("soul_shard_" + name, "soul_shards/" + name, ConstGemTabOrder.SOUL_SHARD);
        this.spirit = spirit;
        setCreativeTab(DivineFavor.TAB_GEMS);
    }

    public ModSpirit getSpirit() {
        return spirit;
    }

    public static boolean hasOwner(ItemStack stack) {
        return getEntityId(stack) != null;
    }

    @Nullable
    public static UUID getEntityId(ItemStack stack) {
        NBTTagCompound compound = UtilNbt.getNbt(stack);
        if (compound.hasKey(TAG_ENTITY_ID))
            return UUID.fromString(compound.getString(TAG_ENTITY_ID));
        return null;
    }

    public static void setOwner(ItemStack stack, EntityLivingBase livingBase) {
        NBTTagCompound compound = UtilNbt.getNbt(stack);
        compound.setString(TAG_ENTITY_ID, livingBase.getUniqueID().toString());
        compound.setString(NBT_ENTITY_NAME, livingBase.getName());
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        NBTTagCompound compound = UtilNbt.getNbt(stack);
        if (compound.hasKey(NBT_ENTITY_NAME))
            tooltip.add(I18n.format("item.divinefavor:soul_shard.entity", compound.getString(NBT_ENTITY_NAME)));
    }
}
