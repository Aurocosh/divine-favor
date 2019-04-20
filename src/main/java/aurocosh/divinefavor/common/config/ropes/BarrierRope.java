package aurocosh.divinefavor.common.config.ropes;

import net.minecraftforge.common.config.Config;

public class BarrierRope {
    @Config.Name("Repulsion radius")
    public int repulsionRadius = 3;
    @Config.Name("Repulsion force")
    public float repulsionForce = 0.3f;
    @Config.Name("Durability")
    public int durability = 100;
}
