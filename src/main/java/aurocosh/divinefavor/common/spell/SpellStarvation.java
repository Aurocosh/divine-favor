package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilRandom;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class SpellStarvation extends ModSpell {
    public static int DURATION = UtilTick.minutesToTicks(2.5f);

    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new PotionEffect(ModPotions.starvation,DURATION));
    }
}
