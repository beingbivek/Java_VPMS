package vpms.utils;

import java.io.*;
import java.nio.file.*;

public class ImageConverter {

    private final byte[] imageBytes;

    public ImageConverter(File uploaded) throws Exception {
        this.imageBytes = getImageOrDefault(uploaded, Constants.defaultImagePath());
    }
    public byte[] returnByteArray() { return imageBytes; }

    public static byte[] convertImageToByteArray(File img) throws IOException {
        return Files.readAllBytes(img.toPath());
    }

    /* tries uploaded → filesystem default → class-path default */
    public static byte[] getImageOrDefault(File uploaded, String defaultPath) throws Exception {

        /* 1) user-supplied */
        if (uploaded != null && uploaded.exists()) {
            return convertImageToByteArray(uploaded);
        }

        /* 2) default on filesystem (IDE run) */
        Path p = Paths.get(defaultPath);
        if (Files.exists(p)) {
            return Files.readAllBytes(p);
        }

        /* 3) default inside the JAR (production run) */
        try (InputStream in = ImageConverter.class.getResourceAsStream(defaultPath)) {
            if (in != null) return in.readAllBytes();
        }
        /* never return null */
        return new byte[0];
    }
}
