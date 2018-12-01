package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

public class SpellSmallFireballThrow extends ModSpell {
    public SpellSmallFireballThrow() {
        super(SpellType.SMALL_FIREBALL_THROW, true);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        throwSmallFireball(context.worldIn,context.playerIn);
        return true;
    }

    public boolean throwSmallFireball(World worldIn, EntityPlayer playerIn) {
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (spellRand.nextFloat() * 0.4F + 0.8F));
        if (!playerIn.world.isRemote)
        {
            //UtilBlock.setHeadingFromThrower(playerIn, entitySmallFireball, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 0.0F);

            Vec3d looking = playerIn.getLookVec();
            if (looking == null)
                return false;

            EntitySmallFireball fireball = new EntitySmallFireball(worldIn,playerIn,0,0,0);
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
