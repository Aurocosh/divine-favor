package aurocosh.divinefavor.common.item.talismans.base;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

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

    public String getUseInfo(EntityPlayer player) {
        SpiritData spiritData = PlayerData.get(player).getSpiritData();
        int favorValue = spiritData.getFavor(spirit.getId());

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