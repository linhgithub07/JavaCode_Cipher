import javax.swing.*;
import java.awt.event.ActionEvent;

public class RailFenceCipher extends JFrame {
    private JTextArea inputText;
    private JTextArea outputText;
    private JTextField keyField;

    public RailFenceCipher() {
        // Thiết lập tiêu đề cho JFrame
        setTitle("Rail Fence Cipher Tool");
        setSize(440, 188);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tạo các thành phần UI
        inputText = new JTextArea(5, 20);
        outputText = new JTextArea(5, 20);
        keyField = new JTextField(15);

        JButton encryptButton = new JButton("Encryption");
        JButton decryptButton = new JButton("Decryption");

        // Panel chính
        JPanel panel = new JPanel();
        panel.add(new JScrollPane(inputText));
        panel.add(new JScrollPane(outputText));
        panel.add(new JLabel("Key"));
        panel.add(keyField);
        panel.add(encryptButton);
        panel.add(decryptButton);
        add(panel);

        // Lắng nghe sự kiện nút
        encryptButton.addActionListener((ActionEvent e) -> {
            String input = inputText.getText();
            String key = keyField.getText();
            String result = encryptRailFence(input, key);
            outputText.setText(result);
        });

        decryptButton.addActionListener((ActionEvent e) -> {
            String input = inputText.getText();
            String key = keyField.getText();
            String result = decryptRailFence(input, key);
            outputText.setText(result);
        });
    }

    // Hàm mã hóa Rail Fence với chuỗi ký tự thay vì số nguyên
    private String encryptRailFence(String text, String key) {
        int rails = key.length();
        if (rails <= 1) return text; // Nếu key quá ngắn, trả về chuỗi ban đầu
        StringBuilder[] railFence = new StringBuilder[rails];
        for (int i = 0; i < rails; i++) railFence[i] = new StringBuilder();

        // Mã hóa bằng cách sử dụng chuỗi key
        int dir = 1; // Hướng di chuyển xuống (+1) hoặc lên (-1)
        int row = 0;

        for (char c : text.toCharArray()) {
            railFence[row].append(c);
            row += dir;

            // Đảo chiều khi chạm tới rail cuối hoặc rail đầu
            if (row == 0 || row == rails - 1) dir *= -1;
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder rail : railFence) {
            result.append(rail);
        }
        return result.toString();
    }

    // Hàm giải mã Rail Fence với chuỗi ký tự
    private String decryptRailFence(String text, String key) {
        int rails = key.length();
        if (rails <= 1) return text;
        StringBuilder[] railFence = new StringBuilder[rails];
        for (int i = 0; i < rails; i++) railFence[i] = new StringBuilder();

        // Tính toán cách các ký tự được sắp xếp trên rail
        int[] railLengths = new int[rails];
        int dir = 1;
        int row = 0;
        for (int i = 0; i < text.length(); i++) {
            railLengths[row]++;
            row += dir;
            if (row == 0 || row == rails - 1) dir *= -1;
        }

        // Phân bố ký tự vào từng rail
        int index = 0;
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < railLengths[i]; j++) {
                railFence[i].append(text.charAt(index++));
            }
        }

        // Đọc ra ký tự theo thứ tự chuyển động xuống và lên
        StringBuilder result = new StringBuilder();
        dir = 1;
        row = 0;
        int[] pos = new int[rails]; // Chỉ mục cho từng rail
        for (int i = 0; i < text.length(); i++) {
            result.append(railFence[row].charAt(pos[row]++));
            row += dir;
            if (row == 0 || row == rails - 1) dir *= -1;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        RailFenceCipher app = new RailFenceCipher();
        app.setVisible(true);
    }
}
