package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import org.easymock.MockControl;

/**
 * @author ukjamescook
 */
public class Tester extends TestCase {
    private List<MockControl> mockControlList = new ArrayList<MockControl>();

    protected MockControl createControl(Class theClass) {
        MockControl mockControl = MockControl.createControl(theClass);
        registerMockControl(mockControl);
        return mockControl;
    }

    protected void registerMockControl(MockControl mockControl) {
        mockControlList.add(mockControl);
    }

    protected void replay() {
        for (MockControl control : mockControlList) {
            control.replay();
        }
    }

    protected void verify() {
        for (MockControl control : mockControlList) {
            control.verify();
        }
    }

    public void testSomething() throws Exception {
        assertTrue("This is so I can run tests on the entire directory", true);
    }
}
