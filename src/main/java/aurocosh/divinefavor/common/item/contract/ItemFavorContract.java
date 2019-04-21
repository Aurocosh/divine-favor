package aurocosh.divinefavor.common.item.contract;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.items.Contract;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFavorContract extends ItemContract {
    private final int regen;
    private final int min;
    private final int max;
    private final boolean informActivity;

    public ItemFavorContract(String name, String texturePath, Contract contract) {
        this(name, texturePath, contract.regen, contract.minimum, contract.maximum, contract.informActivity);
    }

    public ItemFavorContract(String name, String texturePath, int regen, int min, int max, boolean informActivity) {
        super(name, texturePath);
        this.regen = regen;
        this.min = min;
        this.max = max;
        this.informActivity = informActivity;
        setMaxStackSize(1);
        setCreativeTab(DivineFavor.TAB_MAIN);
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

    public boolean isInformActivity() {
        return informActivity;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        if (regen != 0)
            tooltip.add(I18n.format("item.divinefavor:contract.regen", regen));
        if (min != 0)
            tooltip.add(I18n.format("item.divinefavor:contract.min", min));
        if (max != 0)
            tooltip.add(I18n.format("item.divinefavor:contract.max", max));
        if (informActivity)
            tooltip.add(I18n.format("item.divinefavor:contract.informActivity"));
    }
}
