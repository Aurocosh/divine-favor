package aurocosh.divinefavor.common.muliblock.patchouli;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.MultiBlockConfiguration;
import aurocosh.divinefavor.common.muliblock.parts.AirStateValidator;
import aurocosh.divinefavor.common.muliblock.parts.MultiBlockPart;
import aurocosh.divinefavor.common.muliblock.parts.StateValidator;
import vazkii.patchouli.common.multiblock.StateMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatchouliMultiBlockData {
    public static final char[] possibleBlockMarkers = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public final String[][] pattern;
    public final Object[] matchingData;

    public PatchouliMultiBlockData(MultiBlockConfiguration configuration) {
        int nextSymbolId = 0;
        Map<StateValidator, Character> symbolMap = new HashMap<>();
        List<MultiBlockPart> parts = configuration.parts;
        for (int i = 0; i < parts.size(); i++) {
            MultiBlockPart part = parts.get(i);
            char symbol;
            if (part.positions.size() != 1 || !part.positions.get(0).equals(configuration.baseRelPosition)) {
                if (part.validator instanceof AirStateValidator)
                    symbol = ' ';
                else
                    symbol = possibleBlockMarkers[nextSymbolId++];
                symbolMap.put(part.validator, symbol);
            }
        }

        Map<Vector3i, StateValidator> validatorMap = new HashMap<>();
        for (MultiBlockPart part : configuration.parts)
            for (Vector3i position : part.positions)
                validatorMap.put(position, part.validator);

        Vector3i size = configuration.boundingBox.getSizeVector();
        String[][] layers = new String[size.y][];
        for (int y = 0; y < size.y; y++) {
            String[] layer = new String[size.z];
            for (int z = 0; z < size.z; z++) {
                StringBuilder builder = new StringBuilder(size.x);
                for (int x = 0; x < size.x; x++) {
                    Vector3i position = new Vector3i(x, size.y - 1 - y, z);

                    Character symbol;
                    if (position.equals(configuration.baseRelPosition))
                        symbol = '0';
                    else {
                        StateValidator validator = validatorMap.get(position);
                        symbol = symbolMap.get(validator);
                        if (symbol == null)
                            symbol = ' ';
                    }
                    builder.append(symbol);
                }
                layer[z] = builder.toString();
            }
            layers[y] = layer;
        }

        pattern = layers;
        List<Object> matchers = new ArrayList<>();

        for (Map.Entry<StateValidator, Character> entry : symbolMap.entrySet()) {
            Object matcher = entry.getKey().getPatchouliMatcher();
            if (matcher == null)
                continue;

            matchers.add(entry.getValue());
            matchers.add(matcher);
        }

        matchers.add(' ');
        matchers.add(StateMatcher.ANY);

        StateValidator validator = validatorMap.get(configuration.baseRelPosition);
        matchers.add('0');
        matchers.add(validator != null ? validator.getPatchouliMatcher() : StateMatcher.ANY);

        matchingData = matchers.toArray();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pattern.length; i++) {
            String[] layer = pattern[i];
            for (int j = 0; j < layer.length; j++) {
                String s = layer[j];
                builder.append(s);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
