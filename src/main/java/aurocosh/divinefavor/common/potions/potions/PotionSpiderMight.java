package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionSpiderMight extends ModPotionToggle {
    public static final double CLIMB_SPEED = 0.288D;

    public PotionSpiderMight() {
        super("spider_might", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        EntityLivingBase livingBase = event.getEntityLiving();
        if (!livingBase.isPotionActive(ModPotions.spider_might))
            return;

        DamageSource source = event.getSource();
        if (source.isFireDamage())
            event.setAmount(event.getAmount() + ConfigSpells.spider_might.fireDamage);

        Entity entity = source.getTrueSource();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            ItemStack stack = player.getHeldItemMainhand();
            if (!stack.isEmpty()) {
                int baneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack);
                event.setAmount(event.getAmount() + baneLevel * ConfigSpells.spider_might.baneDamage);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity == null)
            return;
        if (!entity.isPotionActive(ModPotions.spider_might))
            return;
        if (!entity.collidedHorizontally)
            return;

        if (entity.isSneaking())
            entity.motionY = 0.0D;
        else if (entity.moveForward > 0.0F && entity.motionY < CLIMB_SPEED)
            entity.motionY = CLIMB_SPEED;
        if (!entity.world.isRemote)
            entity.fallDistance = 0;
    }
}
