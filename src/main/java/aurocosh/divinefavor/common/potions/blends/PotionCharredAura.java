package aurocosh.divinefavor.common.potions.blends;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.charred_aura.CharredAuraData;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionCharredAura extends ModPotion {
    private static final float EXPLOSION_POWER = 6;

    public PotionCharredAura() {
        super("charred_aura", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if(!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        CharredAuraData auraData = PlayerData.get(player).getCharredAuraData();
        auraData.reset();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockPlaced(BlockEvent.PlaceEvent event) {
        EntityPlayer player = event.getPlayer();
        if(!player.isPotionActive(ModBlendEffects.charred_aura))
            return;
        IBlockState state = event.getState();
        if(!(state.getBlock() == Blocks.FIRE))
            return;
        if(!ModSpirits.allfire.isActive())
            return;
        CharredAuraData auraData = PlayerData.get(player).getCharredAuraData();
        if(auraData.count()){
            player.removePotionEffect(ModBlendEffects.charred_aura);
            player.addPotionEffect(new ModEffect(ModBlessings.scorching_presence, UtilTick.minutesToTicks(2)));
            BlockPos playerPosition = player.getPosition();
            event.getWorld().newExplosion(player, playerPosition.getX(), playerPosition.getY(), playerPosition.getZ(), EXPLOSION_POWER, true, false);
        }
    }
}
