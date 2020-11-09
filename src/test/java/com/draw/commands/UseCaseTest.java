package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.exception.CanvasException;
import com.draw.exception.WrongParamsException;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * In this class are traced use cases which introduced errors in the test phase.
 */
public class UseCaseTest extends CommandTest {

	@After
	public void drillDown() {
		canvas.clearCanvas();
	}

	@Test
	public void parseBucketFillCommand() throws  CanvasException {
		String cmdLine = "B 2 3 a";
		Command cmd = drawFactory.buildCommand(cmdLine, canvas);

		assertNotNull(cmd);
		assertTrue(cmd instanceof BucketFill);
	}

	@Test(expected = WrongParamsException.class)
	public void testEX_ParseBucketFilldWrongColor() throws CanvasException {
		String cmdLine = "B 2 3 add";
		drawFactory.buildCommand(cmdLine, canvas);
	}

	@Test
	public void testHP_1() throws CanvasException {
		Line l = new Line("L 1 1 18 1".split("\\s"), canvas);
		int stars = (18 - 1) + 1;
		canvas.getCommands().add(l);
		assertExpected(canvas, stars);


		l = new Line("L 3 2 3 3".split("\\s"), canvas);
		stars = stars + 2;
		canvas.getCommands().add(l);
		assertExpected(canvas,stars);

		Rectangle r = new Rectangle("R 3 2 8 3".split("\\s"), canvas);
		stars = stars + 10;
		canvas.getCommands().add(r);
		assertExpected(canvas,stars);
		canvas.getCommands().remove(r);
	}
	
	@Test(expected = WrongParamsException.class)
	public void testEX_newCanvasExceedWidthDimension() throws CanvasException {
		canvas = new Canvas(201, 10);
	}
	@Test(expected = WrongParamsException.class)
	public void testEX_newCanvasExceedHeightDimension() throws CanvasException {
		canvas = new Canvas(10, 51);
	}
	@Test(expected = WrongParamsException.class)
	public void testEX_newCanvasExceedWidthDimension_secondConstructor() throws CanvasException {
		canvas = new Canvas(201, 10);
	}
	@Test(expected = WrongParamsException.class)
	public void testEX_newCanvasExceedHeightDimension_secondConstructor() throws CanvasException {
		canvas = new Canvas(10, 51);
		canvas = new Canvas(10, 51, null);
	}
	
}