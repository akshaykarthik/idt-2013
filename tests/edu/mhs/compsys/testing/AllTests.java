package edu.mhs.compsys.testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.mhs.compsys.testing.unit.BinaryImageProcessorTest;
import edu.mhs.compsys.testing.unit.BinaryImageTest;

@RunWith(Suite.class)
@SuiteClasses({
	BinaryImageTest.class,
	BinaryImageProcessorTest.class
})
public class AllTests {

}
