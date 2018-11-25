package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.Spell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.lib.LibSpellNames;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class SpellSnowballThrow extends Spell {
    public SpellSnowballThrow() {
        super(LibSpellNames.SNOWBALL_THROW);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        throwSnowball(context.worldIn,context.playerIn);
        return true;
    }

    @Override
    protected boolean claimCost( SpellContext context) {
        return true;
    }

    public boolean throwSnowball(World worldIn, EntityPlayer playerIn) {
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (spellRand.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote)
        {
            EntitySnowball entitysnowball = new EntitySnowball(worldIn, playerIn);
            entitysnowball.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntity(entitysnowball);
        }
        return true;
    }
}