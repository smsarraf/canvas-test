package com.draw.core;

import com.draw.commands.BucketFill;
import com.draw.commands.Command;
import com.draw.commands.Line;
import com.draw.commands.Rectangle;
import com.draw.exception.CanvasException;
import com.draw.exception.WrongParamsException;
import com.draw.util.ApplicationUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.draw.util.ApplicationUtil.CANVAS_MAX_HEIGHT;
import static com.draw.util.ApplicationUtil.CANVAS_MAX_WIDTH;

@Setter
@Getter
public class Canvas {

    private int width;
    private int height;
    private List<Command> commands;

    public Canvas(int w, int h) throws CanvasException {
        validateInputNewCanvas(w, h);
        width = w;
        height = h;
        commands = new ArrayList<>();
        resetMatrix();
    }

    public Canvas(int width, int height, List<Command> commands) throws CanvasException {
        this(width, height);
        this.commands = commands;
    }

    private void validateInputNewCanvas(int width, int height) throws CanvasException {
        if (width > CANVAS_MAX_WIDTH || height > CANVAS_MAX_HEIGHT) {
            System.out.printf("New Canvas - Wrong dimension: (%d, %d). Max dimension: %d x %d %n", width, height, CANVAS_MAX_WIDTH, CANVAS_MAX_HEIGHT);
            throw new WrongParamsException(String.format("Dimension for new draw must be maximum: %d x %d", CANVAS_MAX_WIDTH, CANVAS_MAX_HEIGHT));
        }
    }

    public void clearCanvas() {
        resetMatrix();
    }

    private void resetMatrix() {
        commands = new ArrayList<>();
    }

    /**
     *  Generate Canvas String 2d array
     * @return String 2d array
     */
    public String[][] generateCanvasArray() {
        String[][] chars = new String[height][width];

        //Default Value
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                chars[i][j] =" ";
            }
        }

        commands.forEach((command) -> {
            switch (command.getName()) {
                case ApplicationUtil.CMD_LINE:
                    insertLine(command, chars);
                    break;
                case ApplicationUtil.CMD_RECTANGLE:
                    insertRectangle(command, chars);
                    break;
                case ApplicationUtil.CMD_FILL:
                    fillBucket(command, chars);
                    break;
                default:
            }
        });
        return chars;
    }

    /**
     * Print the draw state surrounded by the borders:
     */
    public void printCanvas() {
        String[][] chars = generateCanvasArray();
        // print Header
        printHeaderAndFooter();

        // the body of the draw | **** |
        for (int i = 0; i < height; i++) {
            System.out.print("\t|");
            for (int j = 0; j < width; j++) {
                System.out.print(chars[i][j] == null ? "\t " : "\t" + chars[i][j]);
            }
            System.out.println("\t|");
        }

        printHeaderAndFooter();
    }

    private void printHeaderAndFooter() {
        for (int j = 0; j < width + 2; j++) {
            System.out.print("\t-");
        }
        System.out.println();
    }

    /**
     * Insert a Line in the draw. </br>
     * 1. Insert the line </br>
     *
     * @param command Command containing x1, y1, x2, y2 for inserting new line.
     * @param chars   Array of Characters
     */
    public String[][] insertLine(Command command, String[][] chars) {
        Line cmd = (Line) command;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // horizontal line from X1 to X2 (shift +1 to match user input)
                if ((j + 1) >= cmd.getX1() && (j + 1) <= cmd.getX2() && (i + 1) == cmd.getY1()) {
                    chars[i][j] = ApplicationUtil.PIXEL;
                }
                // vertical line from Y1 to Y2 (shift +1 to match user input)
                if ((i + 1) >= cmd.getY1() && (i + 1) <= cmd.getY2() && (j + 1) == cmd.getX1()) {
                    chars[i][j] = ApplicationUtil.PIXEL;
                }
            }
        }
        return chars;
    }

    /**
     * Insert a Rectangle in the draw. </br>
     * 1. Insert the Rectangle </br>
     *
	 * @param command Command containing x1, y1, x2, y2 for inserting a rectangle, whose upper left corner is (x1,y1)
	 *                and lower right corner is (x2,y2)
	 */
    public void insertRectangle(Command command, String[][] chars) {
        Rectangle cmd = (Rectangle) command;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // horizontal line from X1 to X2 (shift +1 to match user input)
                // in case we are in the first row(Y1) or in the second row(Y2)
                if ((j + 1) >= cmd.getX1() && (j + 1) <= cmd.getX2() && ((i + 1) == cmd.getY1() || (i + 1) == cmd.getY2())) {
                    chars[i][j] = ApplicationUtil.PIXEL;
                }

                // vertical line from Y1 to Y2 (shift +1 to match user input)
                // where we are in the first column(X1) or second column(X2)
                if ((i + 1) >= cmd.getY1() && (i + 1) <= cmd.getY2() && ((j + 1) == cmd.getX1() || (j + 1) == cmd.getX2())) {
                    chars[i][j] = ApplicationUtil.PIXEL;
                }
            }
        }
	}

    /**
     * Fill the area connected to the coordinate passed as parameter.
     *
     * @param command Command containing the "pixel" (x, y) where to start for "filling the bucket"
     * @param chars   character array
     */
    public String[][] fillBucket(Command command, String[][] chars) {
        BucketFill bucketFill = (BucketFill) command;
        fillSinglePixelAndExpand(bucketFill.getX() - 1, bucketFill.getY() - 1, bucketFill.getColor(), chars);
        return chars;
    }

    /**
     * The strategy to fill the area is the following: 1. Insert the "pixel" in the current coordinate. 2. Do the same with 4
     * coordinates adjacent to the actual; call recursively the method. 3. Stop when finding borders or a coordinate already
     * "painted" with 'x' or 'color'
     *
     * @param x     coordinate x
     * @param y     coordinate y
     * @param color color to use for painting the area. Can be any single character as String
     * @param chars Characters Array
     */
    private void fillSinglePixelAndExpand(int x, int y, String color, String[][] chars) {
        // Stops when finds borders
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }
        // stops where find other pixel inserted
        if (ApplicationUtil.PIXEL.equals(chars[y][x]) || color.equals(chars[y][x])) {
            return;
        }

        chars[y][x] = color;

        fillSinglePixelAndExpand(x + 1, y, color, chars);
        fillSinglePixelAndExpand(x, y + 1, color, chars);
        fillSinglePixelAndExpand(x - 1, y, color, chars);
        fillSinglePixelAndExpand(x, y - 1, color, chars);
    }

}