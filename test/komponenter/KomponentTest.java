package komponenter;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KomponentTest {

    @Test
    void setPris() {


        Prosessor k = new Prosessor("",0, "", "");
        assertEquals(0, k.getPris());
    }


    @Test
    void setPris_2() {


        Prosessor k = new Prosessor("",10, "", "");
        assertEquals(10, k.getPris());
    }
}