package flowershop;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Test class.
 *
 * @author nmenego 2016/11/28.
 */
public class ShopRunnerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void normal() throws Exception {

        ShopRunner.main(new String[]{"files/flowershop.data"});
        Assert.assertEquals("10 R12 $12.99\n" +
                "\t10 x $12.99\n" +
                "15 L09 $41.90\n" +
                "\t6 x $16.95\n" +
                "\t9 x $24.95\n" +
                "13 T58 $25.85\n" +
                "\t3 x $5.95\n" +
                "\t5 x $19.90\n", outContent.toString());
    }

    @Test(expected = IOException.class)
    public void wrongDataFormat() throws Exception {
        ShopRunner.main(new String[]{"files/flowershop2.data"});
    }

    @Test
    public void noSuitableBundle() throws Exception {
        ShopRunner.main(new String[]{"files/flowershop3.data"});
        Assert.assertEquals("1 R12 : unable to find suitable bundle combination.\n" +
                "1 L09 : unable to find suitable bundle combination.\n" +
                "1 T58 : unable to find suitable bundle combination.\n", outContent.toString());
    }
}