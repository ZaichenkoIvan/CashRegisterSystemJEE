package project.service.encoder;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import project.model.service.encoder.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PasswordEncoderTest {
    private static PasswordEncoder encoder;
    private String actual;
    private String expected;

    public PasswordEncoderTest(String actual, String expected) {
        this.actual = actual;
        this.expected = expected;
    }

    @BeforeClass
    public static void initEncoder() {
        encoder = new PasswordEncoder();
    }

    @Parameters
    public static Collection<Object[]> passwordEncodingTable() {
        return Arrays.asList(new Object[][] {
                { "Test123", "68EACB97D86FC4621FA2BE17CABD8C".toLowerCase() },
        });
    }

    @Test
    public void shouldEncodePassword() {
        String expected = this.expected;
        String actual = encoder.encode(this.actual).get();

        assertEquals(expected, actual);
    }
}
