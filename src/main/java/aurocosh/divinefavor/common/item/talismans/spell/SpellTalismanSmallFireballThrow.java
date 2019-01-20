package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpellTalismanSmallFireballThrow extends ItemSpellTalisman {
    private static final int USES = 10;

    public SpellTalismanSmallFireballThrow() {
        super("small_fireball_throw", USES, true, false);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        throwSmallFireball(context.world, context.player);
    }

    public boolean throwSmallFireball(World worldIn, EntityPlayer playerIn) {
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (UtilRandom.random.nextFloat() * 0.4F + 0.8F));
        if (!playerIn.world.isRemote) {
            //UtilBlock.setHeadingFromThrower(player, entitySmallFireball, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 0.0F);

            Vec3d looking = playerIn.getLookVec();

            EntitySmallFireball fireball = new EntitySmallFireball(worldIn, playerIn, 0, 0, 0);
            fireball.posY += playerIn.eyeHeight;
            fireball.motionX = looking.x;
            fireball.motionY = looking.y;
            fireball.motionZ = looking.z;
            fireball.accelerationX = fireball.motionX * 0.1D;
            fireball.accelerationY = fireball.motionY * 0.1D;
            fireball.accelerationZ = fireball.motionZ * 0.1D;

            worldIn.spawnEntity(fireball);
        }
        return true;
    }
}
