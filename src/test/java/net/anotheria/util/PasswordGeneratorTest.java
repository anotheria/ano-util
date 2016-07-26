package net.anotheria.util;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

public class PasswordGeneratorTest {
    @Test
    public void generate() throws Exception {
        String generatedPassword = PasswordGenerator.generate(10);
        assertThat(generatedPassword.length(), is(10));
        assertThat(generatedPassword, not(isEmptyOrNullString()));
    }
}