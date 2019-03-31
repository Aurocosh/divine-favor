package aurocosh.divinefavor.common.potions.presences;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.scorching.ScorchingPresenceData;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionScorchingPresence extends ModPotion {
    public PotionScorchingPresence() {
        super("scorching_presence", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if(!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        ScorchingPresenceData presenceData = PlayerData.get(player).getScorchingPresenceData();
        presenceData.reset();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityDamaged(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (!(source == DamageSource.IN_FIRE) && !(source == DamageSource.ON_FIRE))
            return;
        Entity entity = event.getEntity();
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if(!player.isPotionActive(ModBlessings.scorching_presence))
            return;
        ScorchingPresenceData presenceData = PlayerData.get(player).getScorchingPresenceData();
        if(presenceData.tryLuck() && player.world.isRemote){
            player.removePotionEffect(ModBlessings.scorching_presence);
            player.addItemStackToInventory(new ItemStack(ModCallingStones.calling_stone_neblaze));
        }
    }
}
