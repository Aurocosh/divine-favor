package aurocosh.divinefavor.common.muliblock.serialization;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.MultiBlockPart;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class MultiBlockData {
    @Expose
    public boolean symmetrical;
    @Expose
    public Vector3i basePosition;
    @Expose
    public Vector3i controllerPosition;
    @Expose
    public List<MultiBlockPart> parts;

    public MultiBlockData() {
        symmetrical = true;
        basePosition = Vector3i.ZERO;
        controllerPosition = Vector3i.ZERO;
        parts = new ArrayList<>();
    }

    public MultiBlockData(boolean symmetrical, Vector3i basePosition, Vector3i controllerPosition, List<MultiBlockPart> parts) {
        this.symmetrical = symmetrical;
        this.basePosition = basePosition;
        this.controllerPosition = controllerPosition;
        this.parts = parts;
    }
}
