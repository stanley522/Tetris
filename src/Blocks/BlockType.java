package Blocks;

public enum BlockType {

    I_Block(1),
    S_Block(2),
    Z_Block(3),
    L_Block(4),
    J_Block(5),
    O_Block(6),
    T_Block(7);
    int blockNum;

    BlockType(int blockNum) {
        this.blockNum = blockNum;
    }


}