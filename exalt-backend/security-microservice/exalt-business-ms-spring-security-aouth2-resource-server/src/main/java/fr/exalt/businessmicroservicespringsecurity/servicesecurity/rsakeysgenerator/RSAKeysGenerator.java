package fr.exalt.businessmicroservicespringsecurity.servicesecurity.rsakeysgenerator;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class RSAKeysGenerator {
    private static final String KEYS_PATH = "exalt-business-ms-spring-security-aouth2-resource-server/src/main/resources/certs";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        try {
            PemWriter pemWriterPublic = new PemWriter(
                    new OutputStreamWriter(new FileOutputStream(KEYS_PATH + "/publicKey.pem")));
            PemObject pemObjectPublic = new PemObject("PUBLIC KEY", publicKey);
            pemWriterPublic.writeObject(pemObjectPublic);
            pemWriterPublic.close();

            PemWriter pemWriterPrivate = new PemWriter(
                    new OutputStreamWriter(new FileOutputStream(KEYS_PATH + "/privateKey.pem")));
            PemObject pemObjectPrivate = new PemObject("PRIVATE KEY", privateKey);
            pemWriterPrivate.writeObject(pemObjectPrivate);
            pemWriterPrivate.close();
        } catch (Exception exception) {
            log.error("{}", exception.getMessage());
        }
    }
}
