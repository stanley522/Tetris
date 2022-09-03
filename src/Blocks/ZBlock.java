package Blocks;

import java.awt.*;

public class ZBlock extends Block {
    public ZBlock() {
        blockRotations = new int[][][]{
                {{-1, 1}, {0, 1}, {0, 0}, {1, 0}},
                {{1, 1}, {1, 0}, {0, 0}, {0, -1}}
        };
        blockType = BlockType.I_Block;
        color = new Color(50, 200, 100);
    }
}
