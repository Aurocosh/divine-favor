package aurocosh.divinefavor.common.network.base.serialization.class_serializers.registering;

import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.generic.array_list.CollectionSerializerProvider;
import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.generic.hash_set.MapSerializerProvider;

import java.util.*;

import static aurocosh.divinefavor.common.network.base.serialization.class_serializers.TypeBufSerializerProvider.registerSerializerProvider;

public class GenericSerializerRegistration {
    static {
        registerSerializerProvider(List.class, new CollectionSerializerProvider<>(ArrayList::new));
        registerSerializerProvider(ArrayList.class, new CollectionSerializerProvider<>(ArrayList::new));
        registerSerializerProvider(Stack.class, new CollectionSerializerProvider<>(capacity -> new Stack()));
        registerSerializerProvider(Vector.class, new CollectionSerializerProvider<>(capacity -> new Vector()));
        registerSerializerProvider(LinkedList.class, new CollectionSerializerProvider<>(capacity -> new LinkedList()));

        registerSerializerProvider(Set.class, new CollectionSerializerProvider<>(HashSet::new));
        registerSerializerProvider(HashSet.class, new CollectionSerializerProvider<>(HashSet::new));

        registerSerializerProvider(Map.class, new MapSerializerProvider<>(HashMap::new));
        registerSerializerProvider(HashMap.class, new MapSerializerProvider<>(HashMap::new));
    }
}
