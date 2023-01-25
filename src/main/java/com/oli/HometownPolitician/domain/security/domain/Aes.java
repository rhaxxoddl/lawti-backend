package com.oli.HometownPolitician.domain.security.domain;

import com.oli.HometownPolitician.global.property.AesProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class Aes {

    private final AesProperty property;

    public String encrypt(String str) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance(property.getTransformation());
        SecretKeySpec secretKeySpec = new SecretKeySpec(property.getSecret().getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(property.getInitialVector().getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        return Base64.getEncoder()
                .encodeToString(
                        cipher.doFinal(str.getBytes(StandardCharsets.UTF_8))
                );
    }

    public String decrypt(String str) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(property.getTransformation());
        SecretKeySpec secretKeySpec = new SecretKeySpec(property.getSecret().getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(property.getInitialVector().getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return new String(
                cipher.doFinal(Base64.getDecoder().decode(str)), "UTF-8"
        );
    }

}
