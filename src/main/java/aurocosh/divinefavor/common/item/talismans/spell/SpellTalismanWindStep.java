package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class SpellTalismanWindStep extends ItemSpellTalisman {
    private static float ADDED_VELOCITY_Y = 1.2f;
    private static float ADDED_VELOCITY_XZ = 3;

    public SpellTalismanWindStep(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        addVelocity(context.player);
    }

    @Override
    protected void performActionClient(TalismanContext context) {
        addVelocity(context.player);
    }

    private void addVelocity(EntityPlayer player){
        Vec3d lookVec = player.getLookVec();
        player.motionX += lookVec.x * ADDED_VELOCITY_XZ;
        player.motionY += lookVec.y * ADDED_VELOCITY_Y;
        player.motionZ += lookVec.z * ADDED_VELOCITY_XZ;
    }
}
