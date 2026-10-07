package TCP.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q53yCsF1Q {

    public static void main(String[] args) {

        String serverAddress = "36.50.135.242";
        int serverPort = 2211;

        String studentCode = "B23DCCN230";
        String qCode = "53yCsF1Q";

        try {
            SocketChannel channel = SocketChannel.open();
            channel.connect(
                    new InetSocketAddress(serverAddress, serverPort)
            );

            // =====================================
            // a. Gửi studentCode;qCode
            // =====================================
            String request = studentCode + ";" + qCode;

            writeFrame(channel, request);


            // =====================================
            // b. Nhận ĐÚNG 2 frame
            // =====================================
            StringBuilder jsonBuilder = new StringBuilder();

            for (int i = 0; i < 2; i++) {

                String payload = readFrame(channel);

                System.out.println(
                        "Frame " + (i + 1) + ": " + payload
                );

                jsonBuilder.append(payload);
            }

            String json = jsonBuilder.toString();

            System.out.println("JSON: " + json);


            // =====================================
            // c. Lấy event, user, ok
            // =====================================
            String event = getStringValue(json, "event");
            String user = getStringValue(json, "user");
            boolean ok = getBooleanValue(json, "ok");

            String result =
                    "event=" + event
                            + ";user=" + user
                            + ";ok=" + (ok ? "1" : "0");

            System.out.println("Result: " + result);

            // Gửi kết quả dưới dạng frame
            writeFrame(channel, result);


            // =====================================
            // d. Đóng kết nối
            // =====================================
            channel.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ============================================
    // ĐỌC ĐỦ DỮ LIỆU
    // ============================================
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


    // ============================================
    // ĐỌC 1 FRAME
    // 4 byte length + payload
    // ============================================
    public static String readFrame(
            SocketChannel channel
    ) throws IOException {

        // Đọc 4 byte độ dài
        ByteBuffer lengthBuffer =
                ByteBuffer.allocate(4);

        readFully(channel, lengthBuffer);

        lengthBuffer.flip();

        int length = lengthBuffer.getInt();

        // Đọc đúng length byte payload
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


    // ============================================
    // GỬI 1 FRAME
    // ============================================
    public static void writeFrame(
            SocketChannel channel,
            String message
    ) throws IOException {

        byte[] data =
                message.getBytes(StandardCharsets.UTF_8);

        ByteBuffer buffer =
                ByteBuffer.allocate(4 + data.length);

        // 4 byte length
        buffer.putInt(data.length);

        // payload
        buffer.put(data);

        buffer.flip();

        // Gửi đầy đủ dữ liệu
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }


    // ============================================
    // LẤY STRING TỪ JSON
    // Ví dụ "event":"login"
    // ============================================
    public static String getStringValue(
            String json,
            String key
    ) {

        Pattern pattern = Pattern.compile(
                "\"" + key + "\"\\s*:\\s*\"([^\"]*)\""
        );

        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }


    // ============================================
    // LẤY BOOLEAN TỪ JSON
    // Ví dụ "ok":true
    // ============================================
    public static boolean getBooleanValue(
            String json,
            String key
    ) {

        Pattern pattern = Pattern.compile(
                "\"" + key + "\"\\s*:\\s*(true|false)",
                Pattern.CASE_INSENSITIVE
        );

        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return Boolean.parseBoolean(matcher.group(1));
        }

        return false;
    }
}