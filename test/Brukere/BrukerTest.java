package Brukere;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class BrukerTest {

    @org.junit.jupiter.api.Test
    void setBrukernavn() {
    }

    @org.junit.jupiter.api.Test
    void setPassord() {
    }

    @org.junit.jupiter.api.Test
    void setEmailTrue() {
        Bruker b = new Standardbruker();
        b.setEmail("henrik.lieng@oslomet.no");
        assertEquals("henrik.lieng@oslomet.no", b.getEmail());
    }

    @org.junit.jupiter.api.Test
    void setEmailTrue2() {
        Bruker b = new Standardbruker();
        b.setEmail("uk@domain.co.uk");
        assertEquals("uk@domain.co.uk", b.getEmail());
    }

    @org.junit.jupiter.api.Test
    void setEmailTrue3() {
        Bruker b = new Standardbruker();
        b.setEmail("example@example.com");
        assertEquals("example@example.com", b.getEmail());
    }

    @org.junit.jupiter.api.Test
    void setEmailFalse() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setEmail("");
        });
    }

    @org.junit.jupiter.api.Test
    void setEmailFalse2() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setEmail("henrik.lieng");
        });
    }

    @org.junit.jupiter.api.Test
    void setEmailFalse3() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setEmail("@oslomet.no");
        });
    }

    @org.junit.jupiter.api.Test
    void setEmailFalse4() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setEmail("henrik.lieng@invalidDomain");
        });
    }

    @org.junit.jupiter.api.Test
    void setEmailFalse5() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setEmail("test@");
        });
    }

    @org.junit.jupiter.api.Test
    void setEmailFalse6() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setEmail(";bot@evil.com");
        });
    }

    @org.junit.jupiter.api.Test
    void setTlfTrue() {
        Bruker b = new Standardbruker();
        b.setTlf("12233212");
        assertEquals("12233212", b.getTlf());
    }

    @org.junit.jupiter.api.Test
    void setTlfTrue2() {
        Bruker b = new Standardbruker();
        b.setTlf("+4712233212");
        assertEquals("+4712233212", b.getTlf());
    }

    @org.junit.jupiter.api.Test
    void setTlfTrue3() {
        Bruker b = new Standardbruker();
        b.setTlf("+47 12233212");
        assertEquals("+47 12233212", b.getTlf());
    }

    @org.junit.jupiter.api.Test
    void setTlfTrue4() {
        Bruker b = new Standardbruker();
        b.setTlf("(+47)12233212");
        assertEquals("(+47)12233212", b.getTlf());
    }

    @org.junit.jupiter.api.Test
    void setTlfTrue5() {
        Bruker b = new Standardbruker();
        b.setTlf("(+47) 12233212");
        assertEquals("(+47) 12233212", b.getTlf());
    }

    @org.junit.jupiter.api.Test
    void setTlfTrue6() {
        Bruker b = new Standardbruker();
        b.setTlf("(+47) 12 23 32 12");
        assertEquals("(+47) 12 23 32 12", b.getTlf());
    }

    @org.junit.jupiter.api.Test
    void setTlfFalse() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setTlf("");
        });
    }

    @org.junit.jupiter.api.Test
    void setTlfFalse2() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setTlf("Not a number");
        });
    }

    @org.junit.jupiter.api.Test
    void setTlfFalse3() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setTlf("!%&/");
        });
    }

    @org.junit.jupiter.api.Test
    void setTlfFalse4() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setTlf("1-541-abc-3010");
        });
    }

    @org.junit.jupiter.api.Test
    void setTlfFalse5() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setTlf("-231");
        });
    }

    @org.junit.jupiter.api.Test
    void setTlfFalse6() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setTlf("123-norway");
        });
    }

    @org.junit.jupiter.api.Test
    void setTlfFalse7() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setTlf("1-541-æøå-3010");
        });
    }

    @org.junit.jupiter.api.Test
    void setTlfFalse8() {
        Bruker b = new Standardbruker();
        Assertions.assertThrows(InvalidStringException.class, () -> {
            b.setTlf("123 123     123 12");
        });
    }


    @org.junit.jupiter.api.Test
    void setID() {
    }
}