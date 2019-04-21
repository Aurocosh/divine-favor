package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.entity.EntityPing;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class SpellTalismanPing extends ItemSpellTalisman {
    public SpellTalismanPing(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        Vec3d pos = context.posVec;

        EntityPing ropeNode = new EntityPing(context.world);
        ropeNode.setLocationAndAngles(pos.x, pos.y, pos.z, 0, 0);
        context.world.spawnEntity(ropeNode);
        context.world.playSound(null, pos.x, pos.y, pos.z, SoundEvents.BLOCK_NOTE_BELL, SoundCategory.PLAYERS, 1, 1.5F);
    }
}
