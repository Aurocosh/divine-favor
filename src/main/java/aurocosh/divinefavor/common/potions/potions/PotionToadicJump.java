package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.network.message.client.MessageSyncPotionCharge;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectCharge;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionCharge;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionToadicJump extends ModPotionCharge {
    private static float JUMP_BOOST = 0.3f;

    public PotionToadicJump() {
        super("toadic_jump", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(LivingEvent.LivingJumpEvent event) {
        Entity entity = event.getEntityLiving();
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if (!player.isPotionActive(ModPotions.toadic_jump))
            return;

        entity.motionY += JUMP_BOOST;
        if(entity.world.isRemote)
            return;

        ModEffectCharge effectCharge = (ModEffectCharge) player.getActivePotionEffect(ModPotions.toadic_jump);
        assert effectCharge != null;
        int charges = effectCharge.consumeCharge();
        new MessageSyncPotionCharge(ModPotions.toadic_jump, charges).sendTo(player);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
