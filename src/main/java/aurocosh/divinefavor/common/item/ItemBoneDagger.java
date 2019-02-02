package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.damage_source.ModDamageSources;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.common.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBoneDagger extends ModItem {
    private static int HEALTH_MIN = 2;

    public ItemBoneDagger() {
        super("bone_dagger","bone_dagger");
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote || !player.isSneaking())
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        makeCrystal(player, player);
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    private void makeCrystal(EntityPlayer victim, EntityPlayer user) {
        ItemStack stack = new ItemStack(ModItems.blood_crystal);
        ItemBloodCrystal.setOwner(stack, victim);
        user.attackEntityFrom(ModDamageSources.divineDamage, 0.5f);
        user.setHealth(HEALTH_MIN);
        user.addItemStackToInventory(stack);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}
