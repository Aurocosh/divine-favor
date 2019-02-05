package aurocosh.divinefavor.common.potions.blessings;

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

@Mod.EventBusSubscriber
public class PotionChillingPresence extends ModPotion {
    private static final int SPAWN_RADIUS = 7;
    private static final int MIN_WOLFS_TO_SPAWN = 3;
    private static final int MAX_WOLFS_TO_SPAWN = 5;

    public PotionChillingPresence() {
        super("chilling_presence", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        int wolfsToSpawn = UtilRandom.nextInt(MIN_WOLFS_TO_SPAWN, MAX_WOLFS_TO_SPAWN);
        for (int i = 0; i < wolfsToSpawn; i++) {
            BlockPos position = livingBase.getPosition();
            BlockPos spawnPos = UtilCoordinates.getRandomNeighbour(position, SPAWN_RADIUS, 0, SPAWN_RADIUS);
            World world = livingBase.world;
            spawnPos = UtilCoordinates.findPlaceToSpawn(spawnPos, world, SPAWN_RADIUS);
            EntityDirewolf entityDirewolf = new EntityDirewolf(world);
            entityDirewolf.setLocationAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0.0F);
            world.spawnEntity(entityDirewolf);
        }
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        EntityPlayer player = (EntityPlayer) livingBase;
        player.addItemStackToInventory(new ItemStack(ModCallingStones.calling_stone_blizrabi));
    }
}
