import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest {
    @Test
    public void addTest(){
        Character tester = new Character();
        assertEquals(tester.getMag(), 0);
        assertEquals(tester.getAtk(), 2);
    }
}