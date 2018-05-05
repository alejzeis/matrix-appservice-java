import io.github.jython234.matrix.appservice.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilTest {
    @Test
    void testLocalpart() {
        assertEquals("fakeuser", Util.getLocalpart("@fakeuser:fakedomain.net"));
        assertEquals("fakeroom", Util.getLocalpart("#fakeroom:fakedomain.net"));
    }
}
