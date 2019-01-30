package aurocosh.divinefavor.common.spirit.activity;

import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

import java.util.Set;

public class SpiritActivityMessage extends SpiritActivity {
    private final String message;

    public SpiritActivityMessage(String message) {
        this.message = message;
    }

    @Override
    public void execute(ModSpirit spirit, Set<EntityPlayer> players) {
        for (EntityPlayer player : players)
            player.sendMessage(new TextComponentString(message));
    }
}
