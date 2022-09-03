package Blocks;

import java.awt.*;

public class IBlock extends Block {
    public IBlock() {
        blockRotations = new int[][][]{
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                {{-1, 0}, {0, 0}, {1, 0}, {2, 0}}
        };
        blockType = BlockType.I_Block;
        color = new Color(100, 200, 250);
    }

}
