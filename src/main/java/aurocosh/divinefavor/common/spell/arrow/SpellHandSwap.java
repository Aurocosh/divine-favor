package aurocosh.divinefavor.common.spell.arrow;

import aurocosh.divinefavor.common.spell.arrow.base.ArrowSpell;
import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;
import aurocosh.divinefavor.common.util.InventoryIndexes;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class SpellHandSwap extends ArrowSpell {
    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        if(target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            ItemStack stackMain = player.getHeldItemMainhand();
            ItemStack stackOff = player.getHeldItemOffhand();
            player.inventory.setInventorySlotContents(player.inventory.currentItem,stackOff);
            player.inventory.setInventorySlotContents(InventoryIndexes.Offhand.getValue(),stackMain);
        }
    }
}
