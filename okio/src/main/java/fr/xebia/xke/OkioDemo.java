package fr.xebia.xke;

import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OkioDemo {

    private static final ByteString PNG_HEADER = ByteString.decodeHex("89504e470d0a1a0a");

    public static void main(String[] args) throws IOException {
        decodePng(new FileInputStream("../okio/xebia.png"));
    }

    private static void decodePng(InputStream in) throws IOException {
        BufferedSource pngSource = fromInputStream(in);

        ByteString header = readHeader(pngSource);

        if (!header.equals(PNG_HEADER)) {
            throw new IOException("Not a PNG.");
        }

        while (true) {
            // LENGTH | TYPE | CHUNK | CRC
            //   4    |  4   |   X   |  4
            //  int   |String|  data | int
            // Type can be IHDR, IDAT, IEND

            // TODO
        }

        pngSource.close();
    }

    private static BufferedSource fromInputStream(InputStream in) {
        return null; // TODO;
    }

    private static ByteString readHeader(BufferedSource pngSource) throws IOException {
        return null; // TODO
    }

    private static void displayChunk(String type, Buffer chunk) {
        // Type IHDR contains width | length as int
        // Type IDAT contains raw bytes data
        // Type IEND contains nothing

        // TODO
    }
}
