package aurocosh.divinefavor.common.item.talismans.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ItemTalisman extends ModItem {
    protected final String name;
    protected final int favorCost;
    protected final ModSpirit spirit;

    public ItemTalisman(String name, String texturePath, ModSpirit spirit, int favorCost) {
        super(name, texturePath);

        this.name = name;
        this.spirit = spirit;
        this.favorCost = favorCost;
        setMaxStackSize(1);
    }

    public String getName() {
        return name;
    }

    public int getFavorCost() {
        return favorCost;
    }

    public ModSpirit getSpirit() {
        return spirit;
    }

    public int getFavorId() {
        return spirit.getId();
    }

    @SideOnly(Side.CLIENT)
    public String getUseInfo(EntityPlayer player) {
        SpiritData spiritData = PlayerData.get(player).getSpiritData();
        int favorValue = spiritData.getFavor(spirit.getId());

        int useCount = favorCost == 0 ? -1 : favorValue / favorCost;
        String description;
        if (useCount < 0)
            description = I18n.format("tooltip.divinefavor:talisman.infinite_use");
        else if (useCount == 0)
            description = I18n.format("tooltip.divinefavor:talisman.unusable");
        else
            description = I18n.format("tooltip.divinefavor:talisman.cost", favorCost, useCount);
        return description;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        if (player == null)
            return;

        ItemTalisman talisman = (ItemTalisman) stack.getItem();
        String favorCost = talisman.getUseInfo(player);
        tooltip.add(favorCost);

        ModSpirit spirit = talisman.getSpirit();
        String name = I18n.format(spirit.getNameTranslationKey());
        String message = I18n.format("tooltip.divinefavor:talisman.spirit", name);
        tooltip.add(message);
    }
}