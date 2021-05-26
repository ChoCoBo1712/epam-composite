package com.chocobo.composite.reader;

import com.chocobo.composite.exception.CompositeException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class TextComponentReaderTest {

    private final TextComponentReader reader = new TextComponentReader();

    @Test
    public void readLinesToListTest() throws CompositeException {
        String actual = reader.readTextFromFile("data/test_text.txt");

        String expected = "    Lorem ipsum dolor sit enim, consectetur malesuada elit. Mauris et molestie felis.\r\n"
                + "    Integer molestie dolor, consectetur enim malesuada et.";
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = CompositeException.class)
    public void readTextFromFileExceptionTest() throws CompositeException {
        reader.readTextFromFile("data/testtext.txt");
    }
}
