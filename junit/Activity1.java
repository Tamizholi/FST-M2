package junit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {
    static ArrayList list;

    @BeforeAll
     static void setUp() {
        // Initialize a new ArrayList
        list = new ArrayList<String>();
        // Add values to the list
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1
    }

    @Test()
    void insertTest() {// Assert size of list
        assertEquals(2, list.size(), "Wrong size");
// Assert individual elements
        list.add("gamma");
        assertEquals(3, list.size(), "Wrong size");
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("changedbeta", list.get(1), "Wrong element");
        assertEquals("gamma", list.get(2), "Wrong element");
    }

    @Test()
    void replaceTest() {// Assert size of list
        assertEquals(2, list.size(), "Wrong size");
// Assert individual elements
        list.add("gamma");
        assertEquals(3, list.size(), "Wrong size");
        list.set(1, "changedbeta");
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("changedbeta", list.get(1), "Wrong element");
        assertEquals("gamma", list.get(2), "Wrong element");
        list.remove(2);
    }
}
