import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayFairCipher extends JFrame {
    private JTextArea inputText;
    private JTextArea outputText;
    private JTextField keyField;

    public PlayFairCipher() {
        // Thiết lập tiêu đề của JFrame
        setTitle("PlayFair Cipher");
        setSize(290, 294);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tạo các thành phần UI
        inputText = new JTextArea(5, 25);
        outputText = new JTextArea(5, 25);
        keyField = new JTextField(22);
        JButton encryptButton = new JButton("Encryption");
        JButton decryptButton = new JButton("Decryption");

        // Panel chính
        JPanel panel = new JPanel();
        panel.add(new JScrollPane(inputText));
        panel.add(new JScrollPane(outputText));
        panel.add(new JLabel("Key:"));
        panel.add(keyField);
        panel.add(encryptButton);
        panel.add(decryptButton);
        add(panel);

        // Lắng nghe sự kiện nút
        encryptButton.addActionListener((ActionEvent e) -> {
            String input = inputText.getText();
            String key = keyField.getText();
            String result = encrypt(input, key);
            outputText.setText(result);
        });

        decryptButton.addActionListener((ActionEvent e) -> {
            String input = inputText.getText();
            String key = keyField.getText();
            String result = decrypt(input, key);
            outputText.setText(result);
        });
    }

    // Hàm mã hóa Playfair
    private String encrypt(String text, String key) {
        char[][] playfairMatrix = createPlayfairMatrix(key);
        String preparedText = prepareText(text);
        StringBuilder encryptedText = new StringBuilder();
        
        for (int i = 0; i < preparedText.length(); i += 2) {
            char a = preparedText.charAt(i);
            char b = (i + 1 < preparedText.length()) ? preparedText.charAt(i + 1) : 'X';
            if (a == b) b = 'X'; // Thay thế nếu cả hai ký tự giống nhau
            encryptedText.append(encryptPair(a, b, playfairMatrix));
        }
        return encryptedText.toString();
    }

    // Hàm giải mã Playfair
    private String decrypt(String text, String key) {
        char[][] playfairMatrix = createPlayfairMatrix(key);
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';
            decryptedText.append(decryptPair(a, b, playfairMatrix));
        }
        return decryptedText.toString();
    }

    // Tạo ma trận Playfair
    private char[][] createPlayfairMatrix(String key) {
        StringBuilder matrixString = new StringBuilder();
        boolean[] used = new boolean[26];

        // Thêm ký tự từ khóa vào ma trận
        for (char c : key.toUpperCase().toCharArray()) {
            if (c >= 'A' && c <= 'Z' && !used[c - 'A']) {
                used[c - 'A'] = true;
                matrixString.append(c);
            }
        }

        // Thêm các ký tự còn lại
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue; // Bỏ ký tự J
            if (!used[c - 'A']) {
                matrixString.append(c);
            }
        }

        char[][] matrix = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = matrixString.charAt(i * 5 + j);
            }
        }
        return matrix;
    }

    // Chuẩn bị văn bản để mã hóa
    private String prepareText(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                result.append(c);
            }
        }
        return result.toString();
    }

    // Mã hóa cặp ký tự
    private String encryptPair(char a, char b, char[][] matrix) {
        int[] pos1 = findPosition(a, matrix);
        int[] pos2 = findPosition(b, matrix);

        if (pos1[0] == pos2[0]) { // Cùng hàng
            return "" + matrix[pos1[0]][(pos1[1] + 1) % 5] + matrix[pos2[0]][(pos2[1] + 1) % 5];
        } else if (pos1[1] == pos2[1]) { // Cùng cột
            return "" + matrix[(pos1[0] + 1) % 5][pos1[1]] + matrix[(pos2[0] + 1) % 5][pos2[1]];
        } else { // Hình chữ nhật
            return "" + matrix[pos1[0]][pos2[1]] + matrix[pos2[0]][pos1[1]];
        }
    }

    // Giải mã cặp ký tự
    private String decryptPair(char a, char b, char[][] matrix) {
        int[] pos1 = findPosition(a, matrix);
        int[] pos2 = findPosition(b, matrix);

        if (pos1[0] == pos2[0]) { // Cùng hàng
            return "" + matrix[pos1[0]][(pos1[1] + 4) % 5] + matrix[pos2[0]][(pos2[1] + 4) % 5];
        } else if (pos1[1] == pos2[1]) { // Cùng cột
            return "" + matrix[(pos1[0] + 4) % 5][pos1[1]] + matrix[(pos2[0] + 4) % 5][pos2[1]];
        } else { // Hình chữ nhật
            return "" + matrix[pos1[0]][pos2[1]] + matrix[pos2[0]][pos1[1]];
        }
    }

    // Tìm vị trí của ký tự trong ma trận
    private int[] findPosition(char c, char[][] matrix) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Ký tự không tìm thấy
    }

    public static void main(String[] args) {
        PlayFairCipher app = new PlayFairCipher();
        app.setVisible(true);
    }
}
