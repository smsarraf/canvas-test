package com.draw.commands;

import com.draw.exception.CanvasException;
import com.draw.exception.WrongParamsException;
import org.junit.Test;

public class RectangleTest extends CommandTest {

    /**
     * Inserts a rectangle which covers the borders of the draw.
     * Tests the presence of the starts all around the borders and counts the total number of stars.
     */
    @Test
    public void testHP_CoverBordersWithStars() throws CanvasException {
        int x1 = 1, y1 = 1, x2 = width, y2 = height;
        Rectangle cmd = new Rectangle((String.format("R %d %d %d %d", x1, y1, width, height)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);

        int expected = (x2 - x1) * 2 + (y2 - y1) * 2;
        assertExpected(canvas, expected);
    }

    // Will be a line
    @Test
    public void testCL_X1EqualX2() throws CanvasException {
        int x1 = 3, y1 = 1, x2 = 3, y2 = 4;
		Rectangle cmd = new Rectangle((String.format("R %d %d %d %d", x1, y1, x2, y2)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);

        int expected = (y2 - y1) + 1;
        assertExpected(canvas, expected);
    }

    // Will be a line
    @Test
    public void testCL_Y1EqualY2() throws CanvasException {
        int x1 = 1, y1 = 1, x2 = 14, y2 = 1;
		Rectangle cmd = new Rectangle((String.format("R %d %d %d %d", x1, y1, x2, y2)).split("\\s"), canvas);
		canvas.getCommands().add(cmd);

        int expected = (x2 - x1) + 1;
        assertExpected(canvas, expected);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_X1GreaterThanX2() throws CanvasException {
		new Rectangle(("R 16 1 14 3").split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_X1LessThanZero() throws CanvasException {
		new Rectangle(("R -1 1 20 3").split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_X1EqualZero() throws CanvasException {
		new Rectangle(("R 0 1 20 3").split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_X2GreaterThanWidth() throws CanvasException {
		new Rectangle((String.format("R 16 1 %d 3", width + 1)).split("\\s"), canvas);
    }

    @Test
    public void testCL_X2EqualWidth() throws CanvasException {
        int x1 = 16, y1 = 1, x2 = width, y2 = 4;
		Rectangle cmd = new Rectangle((String.format("R %d %d %d %d", x1, y1, x2, y2)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);
        int expected = (x2 - x1) * 2 + (y2 - y1) * 2;
        assertExpected(canvas, expected);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_YLessThanZero() throws CanvasException {
		new Rectangle(("R 16 -1 20 3").split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_YEqualZero() throws CanvasException {
		new Rectangle(("R 16 0 20 3").split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_YGreaterThanHeight() throws CanvasException {
		new Rectangle((String.format("R 1 1 6 %d", height + 1)).split("\\s"), canvas);
    }

    // vertical lines Test cases

    @Test(expected = WrongParamsException.class)
    public void testEX_Y1GreaterThanY2() throws CanvasException {
		new Rectangle(("R 16 3 20 1").split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_Y1LessThanZero() throws CanvasException {
		new Rectangle(("R 16 -1 20 3").split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_Y1EqualZero() throws CanvasException {
		new Rectangle(("R 16 0 20 3").split("\\s"), canvas);
    }

    @Test(expected = WrongParamsException.class)
    public void testEX_Y2GreaterThanHeight() throws CanvasException {
		new Rectangle(("R 16 1 20 "+(height + 1)).split("\\s"), canvas);
    }

    @Test
    public void testCL_Y2EqualHeight() throws CanvasException {
        int x1 = 16, y1 = 1, x2 = 20, y2 = height;
		Rectangle cmd = new Rectangle((String.format("R %d %d %d %d", x1, y1, x2, y2)).split("\\s"), canvas);
        canvas.getCommands().add(cmd);

        int expected = (x2 - x1) * 2 + (y2 - y1) * 2;
        assertExpected(canvas, expected);
    }
}
