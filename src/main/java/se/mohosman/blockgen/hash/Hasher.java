package se.mohosman.blockgen.hash;

import se.mohosman.blockgen.model.Block;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Hasher {

    public static byte[] createHash(Block block) throws NoSuchAlgorithmException {
        String data = String.format("%d%s%s%s", block.getIndex(), block.getTimestamp(), Arrays.toString(block.getData()), Arrays.toString(block.getPreviousHash()));
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data.getBytes(StandardCharsets.UTF_8));
    }
}
