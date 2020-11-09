package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.exception.CanvasException;
import com.draw.exception.WrongParamsException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test the BucketFiller Command. 
 * All the tests start with a Canvas already initialized to the following state: 
 	----------------------
	|xxxxxxxx            |
	|x      x      2     |
	|x  1   x            |
	|x      xxxxxxxx     |
	|xxxxxxxx  3   x     |
	----------------------
	Ignore numbers, it express the area to be filled with 'o'
 */
public class BucketFillTest extends CommandTest {

	private static final transient Log logger = LogFactory.getLog(BucketFillTest.class);
	private final static String COLOR = "o";
	private Canvas canvas;
	private int width = 20;
	private int height = 5;
	private String[][] chars;
	
	/**
	 * Initialize the draw used for all tests.
	 */
	@Before
	public void setUp(){
		try {
			canvas = new Canvas(width, height);
			chars = new String[height][width];
		} catch (CanvasException e) {
			logger.error("Exception initializing Canvas: " + e.getMessage(), e);
		}
		
		try {
			Rectangle cmd = new Rectangle(("R 1 1 8 "+height).split("\\s"), canvas);
			canvas.getCommands().add(cmd);
			canvas.insertRectangle(cmd, chars);

			cmd = new Rectangle(("R 8 "+(height-1) + " 15 "+(height -1)).split("\\s"), canvas);
			canvas.getCommands().add(cmd);
			canvas.insertRectangle(cmd, chars);

			cmd = new Rectangle(("R 15 "+(height-1) + " 15 "+height).split("\\s"), canvas);
			canvas.getCommands().add(cmd);
			canvas.insertRectangle(cmd, chars);
		} catch (Exception e) {
			logger.error("Error initializing the Canvas for testing FillBucket Command");
		}
	}
	
	@After
	public void drillDown() {
		canvas.printCanvas();
		canvas.clearCanvas();
	}
	
	/**
	 * Check correctness of the filler when starting point is as follow: 
	 	----------------------
		|xxxxxxxxO           |
		|x      x            |
		|x      x            |
		|x      xxxxxxxx     |
		|xxxxxxxx      x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea2LeftUpperStartingPoint() {
		BucketFill cmd2 = new BucketFill(9, 1, COLOR, canvas);
		canvas.getCommands().add(cmd2);
		canvas.fillBucket(cmd2, chars);
		//c.printStatusCanvas();
		
		int expected = (12*3)+(5*2);
		assertExpected(canvas, COLOR, expected);
	}
	
	/**
	 * Check correctness of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx           O|
		|x      x            |
		|x      x            |
		|x      xxxxxxxx     |
		|xxxxxxxx      x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea2RightUpperStartingPoint() {
		BucketFill bucketFill = new BucketFill(20, 1, COLOR, canvas);
		canvas.getCommands().add(bucketFill);
		int expected = (12*3)+(5*2);
		assertExpected(canvas, COLOR, expected);
	}
	
	/**
	 * Check correctness of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx            |
		|x      x            |
		|x      xO           |
		|x      xxxxxxxx     |
		|xxxxxxxx      x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea2LeftDownStartingPoint() {
		BucketFill bucketFill = new BucketFill(9, 3, COLOR, canvas);
		canvas.getCommands().add(bucketFill);
		
		int expected = (12*3)+(5*2);
		assertExpected(canvas, COLOR, expected);
	}
	
	/**
	 * Check correctness of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx            |
		|x      x            |
		|x      x            |
		|x      xxxxxxxxO    |
		|xxxxxxxx      x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea2EdgeStartingPoint() {
		BucketFill bucketFill = new BucketFill(16, 4, COLOR, canvas);
		canvas.getCommands().add(bucketFill);
		int expected = (12*3)+(5*2);
		assertExpected(canvas, COLOR, expected);
	}
	
	/**
	 * Check correctness of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx            |
		|x      x            |
		|x      x            |
		|x      xxxxxxxx     |
		|xxxxxxxx      x    O|
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea2RightDownStartingPoint() {
		BucketFill cmd2 = new BucketFill(20, 5, COLOR, canvas);
		canvas.getCommands().add(cmd2);
		//c.printStatusCanvas();
		
		int expected = (12*3)+(5*2);
		assertExpected(canvas, COLOR, expected);
	}

	/**
	 * Check correctness (Area 3) of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx            |
		|x      x            |
		|x      x            |
		|x      xxxxxxxx     |
		|xxxxxxxx  O   x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea3() {
		BucketFill cmd2 = new BucketFill(11, 5, COLOR, canvas);
		canvas.getCommands().add(cmd2);
		int expected = 6;
		assertExpected(canvas, COLOR, expected);
	}
	
	/**
	 * Check correctness (Area 1) of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx            |
		|x      x            |
		|x   O  x            |
		|x      xxxxxxxx     |
		|xxxxxxxx      x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea1() {
		BucketFill cmd2 = new BucketFill(5, 3, COLOR, canvas);
		canvas.getCommands().add(cmd2);
		
		int expected = 6*3;
		assertExpected(canvas, COLOR, expected);
	}	
	
	//Check exceptions
	@Test(expected= WrongParamsException.class)
	public void testEX_XZero() throws CanvasException {
		String str= "B 0 2 a";//one or more space
		BucketFill bucketFill = new BucketFill(str.split("\\s"), canvas);
		canvas.getCommands().add(bucketFill);
	}

	@Test(expected= WrongParamsException.class)
	public void testEX_XLessThanZero() throws CanvasException {
		String str= "B -1 3 a";//one or more space
		BucketFill bucketFill = new BucketFill(str.split("\\s"), canvas);
		canvas.getCommands().add(bucketFill);
	}
	@Test(expected= WrongParamsException.class)
	public void testEX_YZero() throws CanvasException {
		String str= "B 7 0 a";//one or more space
		BucketFill bucketFill = new BucketFill(str.split("\\s"), canvas);
		canvas.getCommands().add(bucketFill);
	}
	@Test(expected= WrongParamsException.class)
	public void testEX_YLessThanZero() throws CanvasException {
		String str= "B 7 -1 a";//one or more space
		BucketFill bucketFill = new BucketFill(str.split("\\s"), canvas);
		canvas.getCommands().add(bucketFill);
	}
}
