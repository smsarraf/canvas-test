package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.exception.CanvasCommandNotFoundException;
import com.draw.exception.CanvasException;
import com.draw.exception.CanvasNotYetCreatedException;
import com.draw.exception.WrongParamsException;
import org.junit.Test;

import static com.draw.util.ApplicationUtil.PIXEL;
import static org.junit.Assert.assertEquals;

public class LineTest extends CommandTest {

    @Test
    public void testHP_leftBorder() throws CanvasException {
        // left column

        Line cmd = new Line((String.format("L 1 1 1 %d", height)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);
        String[][] mat = canvas.generateCanvasArray();

        int countPoints = 0;
        for (String[] strings : mat) {
            for (int j = 0; j < strings.length; j++) {
                // check if first column contains only 'x' elements
                assert j != 0 || (strings[j].equals(PIXEL));
                // count all the 'x' present in the matrix.
                if (strings[j].equals(PIXEL)) {
                    countPoints++;
                }
            }
        }
        assertEquals(height, countPoints);
    }

    @Test
    public void testHP_upperBorder() throws CanvasException {
        Line cmd = new Line((String.format("L 1 1 %d 1", width)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);
        String[][] mat = canvas.generateCanvasArray();
        int countPoints = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                // check if first row contains only 'x' elements
                assert i != 0 || (mat[i][j].equals(PIXEL));
                // count all the 'x' present in the matrix.
                if (mat[i][j].equals(PIXEL)) {
                    countPoints++;
                }
            }
        }
        assertEquals(width, countPoints);
    }

    @Test
    public void testHP_rightBorder() throws CanvasException {
        // right column
        Line cmd = new Line((String.format("L %d 1 %d %d", width, width, height)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);
        String[][] mat = canvas.generateCanvasArray();

        int countPoints = 0;
        for (String[] strings : mat) {
            for (int j = 0; j < strings.length; j++) {
                // check if last column contains only 'x' elements
                assert j != strings.length - 1 || (strings[j].equals(PIXEL));
                // count all the 'x' present in the matrix.
                if (strings[j].equals(PIXEL)) {
                    countPoints++;
                }
            }
        }
        assertEquals(height, countPoints);
    }

    @Test
    public void testHP_bottomBorder() throws CanvasException {
        // bottom row
        Line cmd = new Line((String.format("L 1 %d %d %d", height, width, height)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);
        String[][] mat = canvas.generateCanvasArray();
        int countPoints = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                // check if last row contains only 'x' elements
                assert i != mat.length - 1 || (mat[i][j].equals(PIXEL));
                // count all the 'x' present in the matrix.
                if (mat[i][j].equals(PIXEL)) {
                    countPoints++;
                }
            }
        }
        assertEquals(width, countPoints);
    }

    /**
     * Inserts 4 lines which covers the borders of the draw. First row, last row, first column and the last column.
     * Tests the presence of the 'x' all around the borders and counts the total number of 'x'.
     */
    @Test
    public void testHP_CoverBordersWithLines() throws CanvasException {

        // left column
        Line cmd = new Line((String.format("L 1 1 1 %d", height)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);

        // upper row
        cmd = new Line((String.format("L 1 1 %d 1 ", width)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);

        // right column
        cmd = new Line((String.format("L %d 1 %d %d", width, width, height)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);

        // bottom row
        cmd = new Line((String.format("L 1 %d %d %d", height, width, height)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);

        String[][] mat = canvas.generateCanvasArray();
        // Test the created draw
        int countPoints = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                // check if first and last row contains only 'x' elements
                assert i != 0 && i != mat.length - 1 || (mat[i][j].equals(PIXEL));

                // check if first and last column contains only 'x' elements
                assert j != 0 && j != mat[i].length - 1 || (mat[i][j].equals(PIXEL));

                // count all the 'x' present in the matrix.
                if (mat[i][j].equals(PIXEL)) {
                    countPoints++;
                }
            }
        }

        int mustBe = (width * 2) + (height * 2) - 4; // 2 rows + 2 columns - 4 corners
        assertEquals(mustBe, countPoints);
    }

    @Test(expected = CanvasNotYetCreatedException.class)
    public void testEX_NotVerticalLine() throws CanvasException {
        String str = "L 6 1 7 4";
        Canvas canvas1 = new Canvas(0, 0);
        new Line(str.split("\\s"), canvas1);
        // drawFactory.buildCommand(str, canvas1);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_X1GreaterThanX2() throws CanvasException {
        new Line(("L 4 2 1 2").split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_X1LessThanZero() throws CanvasException {
        String str = "L -2 2 6 2";
        new Line(str.split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_X1EqualZero() throws CanvasException {
        String str = "L 0 2 6 2";
        new Line(str.split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_X2GreaterThanWidth() throws CanvasException {
        String str = String.format("L 1 2 %d 2", width + 1);
        new Line(str.split("\\s"), canvas);
    }

    @Test
    public void testCL_X2EqualWidth() throws CanvasException {
        String str = "L 6 2 " + width + " 2";
        Line line = new Line(str.split("\\s"), canvas);
        canvas.getCommands().add(line);
        assertExpected(canvas, (width - 6) + 1);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_YLessThanZero() throws CanvasException {
        String str = "L 1 -2 6 -2";
        new Line(str.split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_YEqualZero() throws CanvasException {
        String str = "L 1 0 6 0";
        new Line(str.split("\\s"), canvas);
    }

    @Test(expected = CanvasCommandNotFoundException.class)
    public void testEX_YGreaterThanHeight() throws CanvasException {
        String str = "L 1 " + (height + 1) + "  6 " + (height + 1);
        new Line(str.split("\\s"), canvas);
    }

    // vertical lines

    @Test(expected = WrongParamsException.class)
    public void testEX_Y1GreaterThanY2() throws CanvasException {
        String str = "L 6 4 6 3";
        new Line(str.split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_Y1LessThanZero() throws CanvasException {
        String str = "L 6 -1 6 4";
        new Line(str.split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_Y1EqualZero() throws CanvasException {
        String str = "L 6 0 6 4";
        new Line(str.split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_Y2GreaterThanHeight() throws CanvasException {
        String str = String.format("L 6 2 6 %d", height + 1);
        new Line(str.split("\\s"), canvas);
    }

    @Test
    public void testCL_Y2EqualHeight() throws CanvasException {
        String str = String.format("L 6 2 6 %d", height);
        Command command = drawFactory.buildCommand(str, canvas);
        command.execute();
        assertExpected(canvas, (height - 2) + 1);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_XLessThanZero() throws CanvasException {
        String str = "L -1 3 -1 4";
        new Line(str.split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_XEqualZero() throws CanvasException {
        String str = "L 0 3 0 4";
        new Line(str.split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_XGreaterThanWidth() throws CanvasException {
        String str = String.format("L %d 3 %d 4", width + 1, width + 1);
        new Line(str.split("\\s"), canvas);
    }

    @Test
    public void testCL_XEqualWidth() throws CanvasException {
        String str = String.format("L %d 3 %d 4", width, width);
        Command command = drawFactory.buildCommand(str, canvas);
        command.execute();
        assertExpected(canvas, (4 - 3) + 1);
    }

}
