import Blocks.BlockFactory;
import Blocks.Block;
import Blocks.BlockType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;


public class Panel extends JPanel implements ActionListener {
    int screenWidth = 600;
    int screenHeight = 600;
    int unitSize = 25;
    int marginLeft = unitSize;
    int marginBot = unitSize;
    int refreshRate = 50;
    int dropRate = 5;
    int ticks = 0;
    int rowCount = 18;
    int columnCount = 10;
    Timer timer;
    Block block;
    boolean gameRunning = false;
    ArrayList<BlockType> blockList;
    public boolean[][] stableBlocks;
    private int score;
    Random random = new Random();
    private boolean keyPressed = false;

    public Panel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setFocusable(true);

        // KeyInput
        addKeyListener(new TetrisKeyAdapter());

        timer = new Timer(refreshRate, this);
        timer.start();

        startGame();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (gameRunning) {
            drawGridLines(graphics);
            drawBlock(graphics);
            drawStableBlocks(graphics);
        } else {
            gameOverGraphics(graphics);
        }
    }

    public void drawGridLines(Graphics graphics) {
        for (int i = 0; i < columnCount + 1; i++) {
            graphics.drawLine(i * unitSize + marginLeft, screenHeight - (marginBot),
                    i * unitSize + marginLeft, screenHeight - (marginBot + unitSize * rowCount)
            );
        }
        for (int i = 0; i < rowCount + 1; i++) {

            graphics.drawLine(marginLeft, screenHeight - (i * unitSize + marginBot),
                    marginLeft + unitSize * columnCount, screenHeight - (i * unitSize + marginBot)
            );
        }
    }

    private void drawStableBlocks(Graphics graphics, Color color) {
        graphics.setColor(color);
        for (int x = 0; x < stableBlocks.length; x++) {
            for (int y = 0; y < stableBlocks[0].length; y++) {
                if (stableBlocks[x][y])
                    graphics.fillRect(marginLeft + unitSize * x, screenHeight - (marginBot + unitSize + unitSize * y), unitSize, unitSize);
            }
        }
    }

    private void drawStableBlocks(Graphics graphics) {
        drawStableBlocks(graphics, Color.gray);
    }

    private void drawEmptyBlocks(Graphics graphics, Color color) {
        graphics.setColor(color);
        for (int x = 0; x < stableBlocks.length; x++) {
            for (int y = 0; y < stableBlocks[0].length; y++) {
                if (!stableBlocks[x][y])
                    graphics.fillRect(marginLeft + unitSize * x, screenHeight - (marginBot + unitSize + unitSize * y), unitSize, unitSize);
            }
        }
    }

    private void drawBlock(Graphics graphics) {
        if (block == null)
            return;
        graphics.setColor(block.color);
        for (int[] occupiedBlock : block.occupiedBlocks()) {
            if (occupiedBlock[1] >= rowCount)
                continue;
            graphics.fillRect(marginLeft + unitSize * occupiedBlock[0], screenHeight - (marginBot + unitSize + unitSize * occupiedBlock[1]), unitSize, unitSize);
        }
    }

    public void startGame() {
        setStableBlocks();
        fillBlockList();
        newBlock();
        gameRunning = true;
    }

    public void setStableBlocks() {

        stableBlocks = new boolean[columnCount][];
        for (var i = 0; i < stableBlocks.length; i++) {
            var row = new boolean[rowCount];
            for (int j = 0; j < rowCount; j++) {
                row[j] = false;
            }
            stableBlocks[i] = row;
        }
    }

    public void newBlock() {
        var blockType = blockList.get(random.nextInt(7 * 6 * 5 * 4 * 3 * 2 * 1) % blockList.size());
        block = BlockFactory.newBlock(blockType);
        block.position = new int[]{((columnCount - 1) / 2), rowCount};


        //block = blockList
    }

    public void fillBlockList() {
        blockList = new ArrayList(Arrays.asList(BlockType.values()));
    }

    public void drop() {
        // can successfully drop
        if (testDrop()) {
            block.position[1] -= 1;
            return;
        }
        // collides
        stablizeBlocks();

        // hits top when collides
        if (collideTop(block.occupiedBlocks())) {
            gameOver();
        }
        newBlock();
        clearRows();
    }

    public boolean testDrop() {
        return !collide(block.occupiedBlocksAfterDrop());
    }

    public boolean collide(int[][] occupiedBlocks) {
        // hit bottom
        if (Arrays.stream(occupiedBlocks).anyMatch(block -> block[1] < 0))
            return true;
        //hit sides
        if (Arrays.stream(occupiedBlocks).anyMatch(block -> block[0] < 0 || block[0] >= columnCount))
            return true;

        //hit stable
        if (Arrays.stream(occupiedBlocks).anyMatch(block -> block[0] < columnCount && block[1] < rowCount
                && stableBlocks[block[0]][block[1]]))
            return true;

        return false;
    }

    public boolean collideTop(int[][] occupiedBlocks) {
        // hit bottom
        if (Arrays.stream(occupiedBlocks).anyMatch(block -> block[1] == rowCount))
            return true;

        return false;
    }

    public void stablizeBlocks() {
        for (var occupiedBlock : block.occupiedBlocks()) {
            if (occupiedBlock[1] >= rowCount)
                return;
            stableBlocks[occupiedBlock[0]][occupiedBlock[1]] = true;
        }
    }


    public void clearRows() {

        var clearRows = new ArrayList<Integer>();
        for (int i = 0; i < rowCount; i++) {
            var clear = true;
            for (int j = 0; j < columnCount; j++) {
                if (stableBlocks[j][i])
                    continue;
                clear = false;
                break;
            }
            if (clear)
                clearRows.add(i);
        }
        dropClearRows(clearRows);
    }

    public void dropClearRows(ArrayList<Integer> rows) {
        rows.sort(Comparator.reverseOrder());
        for (var row : rows) {
            dropClearRow(row);
        }
    }

    public void dropClearRow(int row) {
        for (int i = 0; i < columnCount; i++) {
            stableBlocks[i][row] = false;
        }
        for (int i = row; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (!stableBlocks[j][i])
                    continue;
                stableBlocks[j][i] = false;
                stableBlocks[j][i - 1] = true;
            }
        }
    }


    private void dropUntilCollide() {
        while (testDrop()) {
            drop();
        }
    }

    private boolean testRotate() {
        return !collide(block.occupiedBlocksAfterRotate());
    }


    public void addClearRowScore() {
        score += clearRowScore();
    }

    public int clearRowScore() {
        return 0;
    }

    public boolean isGameOver() {
        return false;
    }

    public void gameOver() {
        gameRunning = false;
    }

    public void gameOverGraphics(Graphics graphics) {
        drawStableBlocks(graphics, new Color(200, 200, 200));
        drawEmptyBlocks(graphics, new Color(150, 150, 150));
    }

    @Override
    public void resize(Dimension d) {
        super.resize(d);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameRunning)
            return;
        ticks++;
        var tickMatch = ticks == dropRate;
        if (tickMatch) {
            drop();
            ticks = 0;
        }
        if (tickMatch || keyPressed) {
            keyPressed = false;
            repaint();
        }
    }

    public class TetrisKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            keyPressed = true;
            var key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_UP: {
                    if (!testRotate())
                        break;
                    block.rotate();
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    dropUntilCollide();
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    if (collide(block.occupiedBlocksAfterLeft()))
                        break;
                    block.position[0]--;
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    if (collide(block.occupiedBlocksAfterRight()))
                        break;
                    block.position[0]++;
                    break;
                }
                case KeyEvent.VK_ESCAPE: {
                    if (gameRunning)
                        break;
                    startGame();
                }
            }
        }
    }

}

