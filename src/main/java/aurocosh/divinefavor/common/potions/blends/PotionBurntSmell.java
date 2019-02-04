package aurocosh.divinefavor.common.potions.blends;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.burnt_smell.BurntSmellData;
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
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionBurntSmell extends ModPotion {

    public PotionBurntSmell() {
        super("burnt_smell", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if(!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        BurntSmellData smellData = PlayerData.get(player).getBurntSmellData();
        smellData.reset();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockPlaced(BlockEvent.PlaceEvent event) {
        EntityPlayer player = event.getPlayer();
        if(!player.isPotionActive(ModBlendEffects.burnt_smell))
            return;
        IBlockState state = event.getState();
        if(!(state.getBlock() == Blocks.FIRE))
            return;
        if(!ModSpirits.allfire.isActive())
            return;
        BurntSmellData smellData = PlayerData.get(player).getBurntSmellData();
        if(smellData.count()){
            player.removePotionEffect(ModBlendEffects.burnt_smell);
            player.addPotionEffect(new ModEffect(ModBlessings.scorching_presence, UtilTick.minutesToTicks(2)));
        }
    }
}
