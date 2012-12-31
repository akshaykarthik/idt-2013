package edu.mhs.compsys.testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	BinaryImageTest.class,
	BinaryImageProcessorTest.class
})
public class AllTests {

}
