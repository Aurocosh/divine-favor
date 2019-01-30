package aurocosh.divinefavor.common.spirit.activity;

import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Set;

public abstract class SpiritActivity {
    public abstract void execute(ModSpirit spirit, Set<EntityPlayer> players);
}
