package Blocks;

import java.awt.*;

public class SBlock extends Block {
    public SBlock() {
        blockRotations = new int[][][]{
                {{1, 1}, {0, 1}, {0, 0}, {-1, 0}},
                {{0, 1}, {0, 0}, {1, 0}, {1, -1}}
        };
        blockType = BlockType.I_Block;
        color = new Color(200, 50, 50);
    }
}
