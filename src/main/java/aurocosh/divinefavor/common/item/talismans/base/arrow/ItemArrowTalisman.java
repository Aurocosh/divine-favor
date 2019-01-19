package aurocosh.divinefavor.common.item.talismans.base.arrow;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrowCurse;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSpellUses;
import aurocosh.divinefavor.common.player_data.talisman_uses.ITalismanUsesHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import static aurocosh.divinefavor.common.player_data.talisman_uses.TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES;

public class ItemArrowTalisman extends ItemTalisman {
    private final ArrowSpell spell;
    private final int color;
    private final boolean breakOnHit;
    private final ArrowType arrowType;

    public ItemArrowTalisman(String name, int startingSpellUses, ArrowSpell spell, int color, boolean breakOnHit, ArrowType arrowType) {
        super("arrow_talisman_" + name, "arrow_talismans/" + name, startingSpellUses);
        this.spell = spell;
        this.color = color;
        this.breakOnHit = breakOnHit;
        this.arrowType = arrowType;

//        setMaxStackSize(64);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    public int getColor() {
        return color;
    }

    public boolean isBreakOnHit() {
        return breakOnHit;
    }

    public ArrowSpell getSpell() {
        return spell;
    }

    public boolean claimCost(@Nonnull World world, EntityLivingBase shooter) {
        if (!(shooter instanceof EntityPlayer))
            return false;

        EntityPlayer player = (EntityPlayer) shooter;
        ITalismanUsesHandler usesHandler = player.getCapability(CAPABILITY_TALISMAN_USES, null);
        assert usesHandler != null;

        if (!usesHandler.consumeUse(id))
            return false;
        if (world.isRemote)
            return true;

        int usesLeft = usesHandler.getUses(id);
        new MessageSyncSpellUses(id, usesLeft).sendTo(player);
        return true;
    }
// Talisman functions


    public EntityArrow createArrow(@Nonnull World world, @Nonnull ItemArrowTalisman talisman, EntityLivingBase shooter) {
        EntitySpellArrow arrow = getArrowByType(world, shooter);
        arrow.setSpell(talisman, shooter);
        return arrow;
    }

    private EntitySpellArrow getArrowByType(@Nonnull World worldIn, EntityLivingBase shooter){
        if(arrowType == ArrowType.SPELL_ARROW)
            return new EntitySpellArrow(worldIn, shooter);
        else
            return new EntitySpellArrowCurse(worldIn, shooter);
    }
}
