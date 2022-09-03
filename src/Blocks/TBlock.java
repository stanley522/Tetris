package Blocks;

import java.awt.*;

public class TBlock extends Block {
    public TBlock() {
        blockRotations = new int[][][]{
                {{0, 1}, {-1, 0}, {0, 0}, {1, 0}},
                {{1, 0}, {0, 1}, {0, 0}, {0, -1}},
                {{0, -1}, {-1, 0}, {0, 0}, {1, 0}},
                {{-1, 0}, {0, 1}, {0, 0}, {0, -1}}
        };
        blockType = BlockType.I_Block;
        color = new Color(200, 50, 200);
    }
}
