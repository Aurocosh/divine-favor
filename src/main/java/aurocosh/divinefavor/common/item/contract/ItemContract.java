package aurocosh.divinefavor.common.item.contract;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemContract extends ModItem {
    private final ModFavor favor;
    private final int regen;
    private final int min;
    private final int max;

    public ItemContract(String name, String texturePath, int orderIndex, ModFavor favor, int regen, int min, int max) {
        super("contract_" + name, "contracts/" + texturePath, orderIndex);
        this.favor = favor;
        this.regen = regen;
        this.min = min;
        this.max = max;
        setMaxStackSize(1);
        setCreativeTab(DivineFavor.tabContracts);
    }

    public ModFavor getFavor() {
        return favor;
    }

    public int getRegen() {
        return regen;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add(I18n.format("item.divinefavor:contract.favor", favor.getName()));
        if (regen != 0)
            tooltip.add(I18n.format("item.divinefavor:contract.regen", regen));
        if (min != 0)
            tooltip.add(I18n.format("item.divinefavor:contract.min", min));
        if (max != 0)
            tooltip.add(I18n.format("item.divinefavor:contract.max", max));
    }
}
