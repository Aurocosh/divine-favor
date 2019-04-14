package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemWishingStone extends ModItem {
    private final ModSpirit spirit;
    private final int favorCount;

    public ItemWishingStone(ModSpirit spirit, int favorCount, String typeName, int orderIndex) {
        super("wishing_stone_" + typeName + "_" + spirit.getName(), "wishing_stones/" + typeName + "/" + spirit.getName(), orderIndex);
        this.spirit = spirit;
        this.favorCount = favorCount;

        setMaxStackSize(64);
        setCreativeTab(DivineFavor.TAB_GEMS);
    }

    public ModSpirit getSpirit() {
        return spirit;
    }

    public int getFavorCount() {
        return favorCount;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        EnumActionResult result = addFavor(player, stack) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
        return new ActionResult<>(result, stack);
    }

    private boolean addFavor(EntityPlayer player, ItemStack stack) {
        if (player.world.isRemote)
            return false;
        if (!(stack.getItem() instanceof ItemWishingStone))
            return false;
        SpiritData spiritData = PlayerData.get(player).getSpiritData();
        spiritData.addFavor(spirit.getId(), favorCount);
        new MessageSyncFavor(spirit, spiritData).sendTo(player);
        stack.shrink(1);
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        ItemWishingStone wishingStone = (ItemWishingStone) stack.getItem();
        int favorCount = wishingStone.getFavorCount();
        ModSpirit spirit = wishingStone.getSpirit();
        String name = I18n.format(spirit.getNameTranslationKey());

        String message = I18n.format("tooltip.divinefavor:wishingStone.favor_count", favorCount, name);
        tooltip.add(message);
    }
}