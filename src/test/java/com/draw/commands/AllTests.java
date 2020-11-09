package com.draw.commands;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
		LineTest.class,
		RectangleTest.class,
		BucketFillTest.class,
		UseCaseTest.class
})


public class AllTests {

}
