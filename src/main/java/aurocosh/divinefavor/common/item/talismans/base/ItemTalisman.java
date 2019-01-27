package aurocosh.divinefavor.common.item.talismans.base;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ItemTalisman extends ModItem {
    protected final String name;
    protected final int favorCost;
    protected final ModFavor favor;

    // Talisman functions

    public ItemTalisman(String name, String texturePath, ModFavor favor, int favorCost) {
        super(name, texturePath);

        this.name = name;
        this.favor = favor;
        this.favorCost = favorCost;

        ModRegistries.items.register(this);
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
        int favorValue = favorData.get(favor).getValue();

        int useCount = favorCost == 0 ? -1 : favorValue / favorCost;
        String description;
        if(useCount < 0)
            description = "Infinite use";
        else if(useCount == 0)
            description = "Unusable";
        else
            description = "Uses left: " + useCount;
        return description;
    }

// Talisman functions
    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        return stack.getItem() instanceof ItemTalisman ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}