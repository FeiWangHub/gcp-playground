package com.feiwanghub.subcontroller;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SubControllerApplicationTests extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SubControllerApplicationTests(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(SubControllerApplicationTests.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }

}
