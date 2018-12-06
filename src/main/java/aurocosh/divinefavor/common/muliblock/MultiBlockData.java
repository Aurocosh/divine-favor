package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.parts.MultiBlockPart;
import com.google.gson.annotations.Expose;

import java.util.List;

public class MultiBlockData {
    @Expose
    public Vector3i controllerPosition;
    @Expose
    public List<MultiBlockPart> parts;

    public MultiBlockData(Vector3i controllerPosition, List<MultiBlockPart> parts) {
        this.controllerPosition = controllerPosition;
        this.parts = parts;
    }
}
