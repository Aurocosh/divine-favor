package aurocosh.divinefavor.common.favor_sources.common;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import net.minecraft.entity.player.EntityPlayer;

public class FavorToGain {
    public final EntityPlayer player;
    public final FavorSource favorSource;

    public FavorToGain(EntityPlayer player, FavorSource favorSource) {
        this.player = player;
        this.favorSource = favorSource;
    }
}
