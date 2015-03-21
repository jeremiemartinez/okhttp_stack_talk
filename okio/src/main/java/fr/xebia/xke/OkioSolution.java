package fr.xebia.xke;

import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OkioSolution {

    private static final ByteString PNG_HEADER = ByteString.decodeHex("89504e470d0a1a0a");

    public static void main(String[] args) throws IOException {
        decodePng(new FileInputStream("./xebia.png"));
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
            // Type can be IHDR, IDAT, IEND

            Buffer chunk = new Buffer();

            int length = pngSource.readInt();
            String type = pngSource.readUtf8(4);
            pngSource.readFully(chunk, length);
            int crc = pngSource.readInt();

            displayChunk(type, chunk);
            if (type.equals("IEND")) break;
        }

        pngSource.close();
    }

    private static BufferedSource fromInputStream(InputStream in) {
        return Okio.buffer(Okio.source(in));
    }
    
    private static ByteString readHeader(BufferedSource pngSource) throws IOException {
        return pngSource.readByteString(PNG_HEADER.size());
    }

    private static void displayChunk(String type, Buffer chunk) {
        if (type.equals("IHDR")) {
            int width = chunk.readInt();
            int height = chunk.readInt();
            System.out.printf("%d: %s %d x %d%n", chunk.size(), type, width, height);
        } else {
            System.out.printf("%d: %s%n", chunk.size(), type);
        }
    }
}
