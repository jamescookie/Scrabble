package com.jamescookie.scrabble;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilterTest {
    private List<String> a;
    private Filter filter;

    @BeforeEach
    public void setUp() {
        a = new ArrayList<>();
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

    @Test
    public void testFilter1() {
        filter.setMustContain("l*lo");
        Collection<String> words = filter.filter(a);
        assertEquals(1, words.size(), "incorrect number of words returned");
        assertTrue(words.contains("helllo"), "incorrect word returned");
    }

    @Test
    public void testFilter2() {
        filter.setLength(3);
        filter.setOperator(Operator.GREATER_THAN);
        filter.setMustContain("l*l");
        Collection<String> words = filter.filter(a);
        assertEquals(2, words.size(), "incorrect number of words returned");
        assertTrue(words.contains("helllo"), "incorrect word returned");
        assertTrue(words.contains("helala"), "incorrect word returned");
    }

}
