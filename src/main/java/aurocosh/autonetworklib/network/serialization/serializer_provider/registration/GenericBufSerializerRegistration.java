package aurocosh.autonetworklib.network.serialization.serializer_provider.registration;

import aurocosh.autonetworklib.network.serialization.buf_serializers.generic.array_list.CollectionSerializerProvider;
import aurocosh.autonetworklib.network.serialization.buf_serializers.generic.hash_set.MapSerializerProvider;
import aurocosh.autonetworklib.network.serialization.serializer_provider.BufSerializerProvider;
import aurocosh.divinefavor.DivineFavor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
public class GenericBufSerializerRegistration {
    @SubscribeEvent
    public static void onRegisterSerializers(BufSerializerRegistryEvent event) {
        BufSerializerProvider.registerSerializerProvider(List.class, new CollectionSerializerProvider<>(ArrayList::new));
        BufSerializerProvider.registerSerializerProvider(ArrayList.class, new CollectionSerializerProvider<>(ArrayList::new));
        BufSerializerProvider.registerSerializerProvider(Stack.class, new CollectionSerializerProvider<>(capacity -> new Stack()));
        BufSerializerProvider.registerSerializerProvider(Vector.class, new CollectionSerializerProvider<>(capacity -> new Vector()));
        BufSerializerProvider.registerSerializerProvider(LinkedList.class, new CollectionSerializerProvider<>(capacity -> new LinkedList()));

        BufSerializerProvider.registerSerializerProvider(Set.class, new CollectionSerializerProvider<>(HashSet::new));
        BufSerializerProvider.registerSerializerProvider(HashSet.class, new CollectionSerializerProvider<>(HashSet::new));

        BufSerializerProvider.registerSerializerProvider(Map.class, new MapSerializerProvider<>(HashMap::new));
        BufSerializerProvider.registerSerializerProvider(HashMap.class, new MapSerializerProvider<>(HashMap::new));
    }
}
