package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.exception.CanvasException;
import com.draw.factory.DrawFactory;
import com.draw.factory.DrawFactoryImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;

import static com.draw.util.ApplicationUtil.PIXEL;
import static org.junit.Assert.assertEquals;

public abstract class CommandTest {

    private static final transient Log logger = LogFactory.getLog(RectangleTest.class);
    Canvas canvas;
    int width = 20;
    int height = 4;
    DrawFactory drawFactory;

    @Before
    public void setUp() {
        drawFactory = new DrawFactoryImpl();
        try {
            canvas = new Canvas(width, height);
        } catch (CanvasException e) {
            logger.error("Exception initializing Canvas: " + e.getMessage(), e);
        }
    }

    @After
    public void drillDown() {
        canvas.printCanvas();
        canvas.clearCanvas();
    }

    /**
     * Counts the 'pixels' present in the draw and compare it with the expected value.
     */
    void assertExpected(Canvas canvas, String pixels, int expected) {
        String[][] mat = canvas.generateCanvasArray();
        int found = 0;
        for (String[] strings : mat) {
            if (strings.length > 0) {
                for (String string : strings) {
                    // count all the 'x' (or 'o') present in the matrix.
                    if (string.equals(pixels)) {
                        found++;
                    }
                }
            }
        }

        assertEquals(expected, found);
    }

    protected void assertExpected(Canvas canvas, int expected) {
        assertExpected(canvas, PIXEL, expected);
    }

}
