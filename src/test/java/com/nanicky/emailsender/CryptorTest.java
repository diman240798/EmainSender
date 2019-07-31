package com.nanicky.emailsender;


import com.nanicky.emailsender.crypt.Cryptor;
import org.junit.Assert;
import org.junit.Test;

public class CryptorTest {
    @Test
    public void testCrypting() {
        String initialStr = "abc";
        String res1 = Cryptor.crypt(initialStr);
        Assert.assertEquals(initialStr.length(), res1.length());
        Assert.assertEquals(initialStr, Cryptor.crypt(res1));
    }


}