package net.anotheria.util.crypt;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class CryptToolLongKeyTest {
    @Test
    public void testCreationOfLongKey(){
        String appSecret = "3CBAACAAAAAABBBBBBCCCCCC11111148";
        String userSecret = UUID.randomUUID().toString();

        CryptTool cryptTool = new CryptTool(appSecret+userSecret);
        //if we are here, we were able to create LONG KEYS
    }
}
