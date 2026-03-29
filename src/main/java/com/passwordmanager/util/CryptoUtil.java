package com.passwordmanager.util;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.security.SecureRandom;

public class CryptoUtil {
    // Esta es tu "Llave Maestra". Debe tener 32 caracteres para AES-256.
    // ¡No la pierdas o no podrás recuperar las contraseñas!
    private static final String MASTER_KEY = "12345678901234567890123456789012"; 
    private static final String ALGORITHM = "AES/GCM/NoPadding";

    public static String encrypt(String strToEncrypt) {
        try {
            byte[] iv = new byte[12];
            new SecureRandom().nextBytes(iv);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(MASTER_KEY.getBytes(), "AES"), spec);
            
            byte[] cipherText = cipher.doFinal(strToEncrypt.getBytes());
            byte[] message = new byte[12 + cipherText.length];
            System.arraycopy(iv, 0, message, 0, 12);
            System.arraycopy(cipherText, 0, message, 12, cipherText.length);
            
            return Base64.getEncoder().encodeToString(message);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String strToDecrypt) {
        try {
            byte[] decoded = Base64.getDecoder().decode(strToDecrypt);
            byte[] iv = new byte[12];
            System.arraycopy(decoded, 0, iv, 0, 12);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(MASTER_KEY.getBytes(), "AES"), spec);
            
            byte[] plainText = cipher.doFinal(decoded, 12, decoded.length - 12);
            return new String(plainText);
        } catch (Exception e) {
            return "Error al descifrar";
        }
    }
}