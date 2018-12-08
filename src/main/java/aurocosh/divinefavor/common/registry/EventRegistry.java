package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.effect.EventPotionTick;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public class EventRegistry {

  private ArrayList<Object> events = new ArrayList<>();

  public void registerCoreEvents() {
    this.register(new EventPotionTick());
  }

  public void register(Object e) {
    events.add(e);
  }

  public void registerAll() {
    for (Object e : events) {
      MinecraftForge.EVENT_BUS.register(e);
    }
  }
}
