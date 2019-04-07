package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class SpellTalismanWindStep extends ItemSpellTalisman {
    public SpellTalismanWindStep(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        addVelocity(context.player);
    }

    @Override
    protected void performActionClient(TalismanContext context) {
        addVelocity(context.player);
    }

    private void addVelocity(EntityPlayer player) {
        Vec3d lookVec = player.getLookVec();
        player.motionX += lookVec.x * ConfigSpells.windStep.addedVelocityXZ;
        player.motionY += lookVec.y * ConfigSpells.windStep.addedVelocityY;
        player.motionZ += lookVec.z * ConfigSpells.windStep.addedVelocityXZ;
    }
}
