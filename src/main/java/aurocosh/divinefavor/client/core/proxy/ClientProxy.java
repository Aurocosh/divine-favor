package aurocosh.divinefavor.client.core.proxy;

import aurocosh.divinefavor.client.core.handler.KeyBindings;
import aurocosh.divinefavor.client.render.common.ModRendering;
import aurocosh.divinefavor.common.core.proxy.CommonProxy;
import aurocosh.divinefavor.common.particles.ParticleHandler;
import aurocosh.divinefavor.common.particles.types.ModParticleTypes;
import aurocosh.divinefavor.common.particles.types.ParticleType;
import aurocosh.divinefavor.common.util.UtilVec3d;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ModParticleTypes.preInit();
        ModRendering.preInit();

//        MinecraftForge.EVENT_BUS.register(new HUDHandler());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileProgrammer.class, new RenderTileProgrammer());

        Vec3d n_1 = new Vec3d(10, 0, 0);
        Vec3d n_1_1 = UtilVec3d.normalize(n_1);
        Vec3d n_1_2 = n_1.normalize();

        Vec3d n_2 = new Vec3d(1, 1, 1);
        Vec3d n_2_1 = UtilVec3d.normalize(n_2);
        Vec3d n_2_2 = n_2.normalize();

        Vec3d n_3 = new Vec3d(1, 0, 0);
        Vec3d n_3_1 = UtilVec3d.normalize(n_3);
        Vec3d n_3_2 = n_3.normalize();

        Vec3d n_4 = new Vec3d(-10, 5, 0);
        Vec3d n_4_1 = UtilVec3d.normalize(n_4);
        Vec3d n_4_2 = n_4.normalize();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        ModRendering.init();
        KeyBindings.init();
    }

    @Override
    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    public void createParticle(ParticleType particleType, World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... vars) {
        ParticleHandler.createParticle(particleType, world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, vars);
    }
}