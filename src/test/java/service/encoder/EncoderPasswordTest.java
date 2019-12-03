package service.encoder;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class EncoderPasswordTest {
    private static EncoderPassword encoder = new EncoderPassword();

    @Test
    public void shouldEncodePassword() {
        assertThat("21232f297a57a5a743894ae4a801fc3", is(encoder.encode("admin")));
        assertThat("bb534b6741a863fd98b8ea0a79d680", is(encoder.encode("user41")));
        assertThat("9207d4febc6860f6ea3423d65dea0", is(encoder.encode("user48")));
    }

    @Test
    public void shouldFailedIfDontEnoodedPassword() {
        assertThat("user48", not(encoder.encode("user48")));
        assertThat("user41", not(encoder.encode("user41")));
        assertThat("admin", not(encoder.encode("admin")));
    }
}
