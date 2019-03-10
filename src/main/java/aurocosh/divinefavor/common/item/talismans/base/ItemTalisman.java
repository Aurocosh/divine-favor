package aurocosh.divinefavor.common.item.talismans.base;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public abstract class ItemTalisman extends ModItem {
    protected final String name;
    protected final int favorCost;
    protected final ModFavor favor;

    public ItemTalisman(String name, String texturePath, ModFavor favor, int favorCost) {
        super(name, texturePath);

        this.name = name;
        this.favor = favor;
        this.favorCost = favorCost;
        setMaxStackSize(1);
    }

    public String getName() {
        return name;
    }
    public int getFavorCost() {
        return favorCost;
    }

    public ModFavor getFavor() {
        return favor;
    }

    public int getFavorId() {
        return favor.getId();
    }

    public String getUseInfo(EntityPlayer player) {
        FavorData favorData = PlayerData.get(player).getFavorData();
        int favorValue = favorData.getFavor(favor.getId());

        int useCount = favorCost == 0 ? -1 : favorValue / favorCost;
        String description;
        if(useCount < 0)
            description = "Infinite use";
        else if(useCount == 0)
            description = "Unusable";
        else
            description = "Cost: " + favorCost + " Uses: " + useCount;
        return description;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}