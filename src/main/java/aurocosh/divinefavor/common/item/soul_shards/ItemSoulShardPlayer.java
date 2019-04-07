package aurocosh.divinefavor.common.item.soul_shards;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSoulShardPlayer extends ItemSoulShard {
    public ItemSoulShardPlayer(String name, ModSpirit spirit) {
        super(name, spirit);
        setCreativeTab(DivineFavor.TAB_GEMS);
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
            tooltip.add(I18n.format("item.divinefavor:soul_shard.player", compound.getString(NBT_ENTITY_NAME)));
    }
}
