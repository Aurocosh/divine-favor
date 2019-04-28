package aurocosh.divinefavor.common.potions.presences;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.entity.mob.EntityDirewolf;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionChillingPresence extends ModPotion {
    public PotionChillingPresence() {
        super("chilling_presence", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        int spawnRadius = ConfigPresence.chillingPresence.spawnRadius;
        int wolfsToSpawn = UtilRandom.nextInt(ConfigPresence.chillingPresence.minWolfsToSpawn, ConfigPresence.chillingPresence.maxWolfsToSpawn);
        for (int i = 0; i < wolfsToSpawn; i++) {
            BlockPos position = livingBase.getPosition();
            BlockPos spawnPos = UtilCoordinates.INSTANCE.getRandomNeighbour(position, spawnRadius, 0, spawnRadius);
            World world = livingBase.world;
            spawnPos = UtilCoordinates.INSTANCE.findPlaceToStand(spawnPos, world, spawnRadius);
            EntityDirewolf entityDirewolf = new EntityDirewolf(world);
            entityDirewolf.setLocationAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0.0F);
            world.spawnEntity(entityDirewolf);
        }
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        EntityPlayer player = (EntityPlayer) livingBase;
        player.addItemStackToInventory(new ItemStack(ModCallingStones.INSTANCE.getCalling_stone_blizrabi()));
    }
}
