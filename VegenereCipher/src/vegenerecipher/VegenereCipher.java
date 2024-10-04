import javax.swing.*;
import java.awt.event.ActionEvent;

public class VegenereCipher extends JFrame {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public VegenereCipher() {
        // Tạo giao diện Swing
        setTitle("Vegenere Cipher");
        setSize(475, 166);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo các thành phần
        JTextArea inputText = new JTextArea(5, 22);
        JLabel label2 = new JLabel("Key:");
        JTextField keyText = new JTextField(20);
        JButton encryptButton = new JButton("Encryption");
        JButton decryptButton = new JButton("Decryption");
        JTextArea resultText = new JTextArea(5, 22);
        JPanel panel = new JPanel();

        panel.add(inputText);
        panel.add(new JScrollPane(resultText));
        panel.add(label2);
        panel.add(keyText);
        panel.add(encryptButton);
        panel.add(decryptButton);
        add(panel);

        // Xử lý khi nhấn nút mã hóa
        encryptButton.addActionListener((ActionEvent e) -> {
            String plainText = inputText.getText().toUpperCase();
            String key = keyText.getText().toUpperCase();
            resultText.setText(encrypt(plainText, key));
        });

        // Xử lý khi nhấn nút giải mã
        decryptButton.addActionListener((ActionEvent e) -> {
            String cipherText = inputText.getText().toUpperCase();
            String key = keyText.getText().toUpperCase();
            resultText.setText(decrypt(cipherText, key));
        });
    }

    // Hàm mã hóa Vigenère
    private static String encrypt(String text, String key) {
        StringBuilder encryptedText = new StringBuilder();
        key = key.repeat(text.length() / key.length() + 1);
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (ALPHABET.indexOf(currentChar) != -1) {
                int charIndex = (ALPHABET.indexOf(currentChar) + ALPHABET.indexOf(key.charAt(i))) % ALPHABET.length();
                encryptedText.append(ALPHABET.charAt(charIndex));
            } else {
                encryptedText.append(currentChar);
            }
        }
        return encryptedText.toString();
    }
    // Hàm giải mã Vigenère
    private static String decrypt(String text, String key) {
        StringBuilder decryptedText = new StringBuilder();
        key = key.repeat(text.length() / key.length() + 1);  // Mở rộng khóa

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (ALPHABET.indexOf(currentChar) != -1) {
                int charIndex = (ALPHABET.indexOf(currentChar) - ALPHABET.indexOf(key.charAt(i)) + ALPHABET.length()) % ALPHABET.length();
                decryptedText.append(ALPHABET.charAt(charIndex));
            } else {
                decryptedText.append(currentChar);
            }
        }
        return decryptedText.toString();
    }
// ======================= MAIN ==================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VegenereCipher cipher = new VegenereCipher();
            cipher.setVisible(true);
        });
    }
}
