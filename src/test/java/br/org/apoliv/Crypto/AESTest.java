package br.org.apoliv.Crypto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AESTest {
    
    @Test
    public void testEncripty() {
        assertEquals("ilQrAh79vSLt5m3RG1/FVQ==", AES.encrypt("vlr"));
    }

    @Test
    public void testDecripty() {
        assertEquals("vlr", AES.dencrypt("ilQrAh79vSLt5m3RG1/FVQ=="));
    }
}