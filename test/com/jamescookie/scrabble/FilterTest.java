package com.jamescookie.scrabble;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

public class FilterTest extends TestCase {
    private List<String> a;
    private Filter filter;

    protected void setUp() throws Exception {
        super.setUp();
        a = new ArrayList<String>();
        a.add("helllo");
        a.add("hello");
        a.add("helala");
        a.add("helao");
        a.add("lll");
        a.add("lwl");
        a.add("goodbya");
        a.add("hallo");
        a.add("lo");
        a.add("lao");
        filter = new Filter();
    }

    public void testFilter1() throws Exception {
        filter.setMustContain("l*lo");
        Collection words = filter.filter(a);
        assertEquals("incorrect number of words returned", 1, words.size());
        assertTrue("incorrect word returned", words.contains("helllo"));
    }

    public void testFilter2() throws Exception {
        filter.setLength(3);
        filter.setOperator(Operator.GREATER_THAN);
        filter.setMustContain("l*l");
        Collection words = filter.filter(a);
        assertEquals("incorrect number of words returned", 2, words.size());
        assertTrue("incorrect word returned", words.contains("helllo"));
        assertTrue("incorrect word returned", words.contains("helala"));
    }

}
