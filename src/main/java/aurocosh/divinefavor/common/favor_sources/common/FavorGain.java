package aurocosh.divinefavor.common.favor_sources.common;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import net.minecraft.entity.player.EntityPlayer;

public class FavorGain {
    public final EntityPlayer player;
    public final FavorSource favorSource;

    public FavorGain(EntityPlayer player, FavorSource favorSource) {
        this.player = player;
        this.favorSource = favorSource;
    }
}
