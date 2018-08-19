package domain;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {

    ItemNonAbstract item;

    // The following inner class enables us to test the abstract class "Item".
    private class ItemNonAbstract extends Item {

        public ItemNonAbstract(int id, String name) {
            super(id, name);
        }
    }

    @Before
    public void setUp() {
        item = new ItemNonAbstract(0, "");
    }

    @Test
    public void toStringWorks() {
        assertTrue(item.toString().length() > 0);
    }

    @Test
    public void givenIdIsReturnedCorrectly() {
        int id = ThreadLocalRandom.current().nextInt();
        item = new ItemNonAbstract(id, "");
        assertEquals(id, item.getId());
    }

    @Test
    public void givenNameIsReturnedCorrectly() {
        String randomName = ("n").concat(String.valueOf(ThreadLocalRandom.current().nextInt()));
        item = new ItemNonAbstract(0, randomName);
        assertEquals(randomName, item.getName());
    }

}
