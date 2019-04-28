package aurocosh.divinefavor.common.muliblock.patchouli;

import aurocosh.divinefavor.common.muliblock.MultiBlockConfiguration;
import aurocosh.divinefavor.common.muliblock.MultiBlockPart;
import aurocosh.divinefavor.common.muliblock.validators.StateValidator;
import net.minecraft.util.math.BlockPos;
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
        List<MultiBlockPart> parts = configuration.getParts();
        for (int i = 0; i < parts.size(); i++) {
            MultiBlockPart part = parts.get(i);
            char symbol;
            if (part.positions.size() != 1 || !part.positions.get(0).equals(configuration.getBaseRelPosition())) {
                symbol = possibleBlockMarkers[nextSymbolId++];
                symbolMap.put(part.validator, symbol);
            }
        }

        Map<BlockPos, StateValidator> validatorMap = new HashMap<>();
        for (MultiBlockPart part : configuration.getParts())
            for (BlockPos position : part.positions)
                validatorMap.put(position, part.validator);

        BlockPos size = configuration.getBoundingBox().getSizeVector();
        String[][] layers = new String[size.getY()][];
        for (int y = 0; y < size.getY(); y++) {
            String[] layer = new String[size.getZ()];
            for (int z = 0; z < size.getZ(); z++) {
                StringBuilder builder = new StringBuilder(size.getX());
                for (int x = 0; x < size.getX(); x++) {
                    BlockPos position = new BlockPos(x, size.getY() - 1 - y, z);

                    Character symbol;
                    if (position.equals(configuration.getBaseRelPosition()))
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

        StateValidator validator = validatorMap.get(configuration.getBaseRelPosition());
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
                builder.append("|");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
