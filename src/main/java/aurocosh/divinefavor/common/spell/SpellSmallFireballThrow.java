package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.api.spell.Spell;
import aurocosh.divinefavor.api.spell.SpellContext;
import aurocosh.divinefavor.common.lib.LibSpellNames;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpellSmallFireballThrow extends Spell {
    public SpellSmallFireballThrow() {
        super(LibSpellNames.SMALL_FIREBALL_THROW);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        throwSmallFireball(context.worldIn,context.playerIn);
        return true;
    }

    @Override
    protected boolean claimCost( SpellContext context) {
        return true;
    }

    public boolean throwSmallFireball(World worldIn, EntityPlayer playerIn) {
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (spellRand.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote)
        {
            //TalismanHelper.setHeadingFromThrower(playerIn, entitySmallFireball, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 0.0F);

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
