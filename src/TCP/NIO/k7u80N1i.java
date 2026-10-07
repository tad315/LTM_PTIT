package TCP.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class k7u80N1i {

    public static void main(String[] args) {

        String serverAddress = "36.50.135.242";
        int serverPort = 2211;

        String studentCode = "B23DCCN230";
        String qCode = "k7u80N1i";

        try {
            SocketChannel channel = SocketChannel.open();
            channel.connect(
                    new InetSocketAddress(serverAddress, serverPort)
            );

            // a. Gửi studentCode;qCode
            String request = studentCode + ";" + qCode;
            writeFrame(channel, request);

            // b. Nhận đúng 3 frame
            StringBuilder httpRequest = new StringBuilder();

            for (int i = 0; i < 3; i++) {
                String payload = readFrame(channel);

                System.out.println(
                        "Frame " + (i + 1) + ": " + payload
                );

                httpRequest.append(payload);
            }

            String http = httpRequest.toString();

            System.out.println("HTTP Request:");
            System.out.println(http);

            // c. METHOD;PATH;HOST
            String result = parseHttpRequest(http);

            System.out.println("Result: " + result);

            writeFrame(channel, result);

            // d. Đóng kết nối
            channel.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // =========================================
    // ĐỌC ĐỦ DỮ LIỆU
    // =========================================
    public static void readFully(
            SocketChannel channel,
            ByteBuffer buffer
    ) throws IOException {

        while (buffer.hasRemaining()) {

            int n = channel.read(buffer);

            if (n == -1) {
                throw new IOException(
                        "Server đóng kết nối trước khi đọc đủ dữ liệu"
                );
            }
        }
    }


    // =========================================
    // ĐỌC 1 FRAME
    // =========================================
    public static String readFrame(
            SocketChannel channel
    ) throws IOException {

        // Đọc 4 byte length
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);

        readFully(channel, lengthBuffer);

        lengthBuffer.flip();

        int length = lengthBuffer.getInt();

        // Đọc payload
        ByteBuffer payloadBuffer =
                ByteBuffer.allocate(length);

        readFully(channel, payloadBuffer);

        payloadBuffer.flip();

        byte[] data = new byte[length];
        payloadBuffer.get(data);

        return new String(
                data,
                StandardCharsets.UTF_8
        );
    }


    // =========================================
    // GỬI 1 FRAME
    // =========================================
    public static void writeFrame(
            SocketChannel channel,
            String message
    ) throws IOException {

        byte[] data =
                message.getBytes(StandardCharsets.UTF_8);

        ByteBuffer buffer =
                ByteBuffer.allocate(4 + data.length);

        // 4 byte độ dài
        buffer.putInt(data.length);

        // Payload
        buffer.put(data);

        buffer.flip();

        // Gửi đủ dữ liệu
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }


    // =========================================
    // TÁCH METHOD;PATH;HOST
    // =========================================
    public static String parseHttpRequest(String http) {

        String[] lines = http.split("\\r\\n");

        // Ví dụ:
        // GET /search?q=java HTTP/1.1
        String[] firstLine = lines[0].split(" ");

        String method = firstLine[0];
        String path = firstLine[1];

        String host = "";

        // Tìm dòng Host:
        for (String line : lines) {

            if (line.toLowerCase().startsWith("host:")) {

                host = line.substring(5).trim();

                break;
            }
        }

        return method + ";" + path + ";" + host;
    }
}