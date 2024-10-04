import javax.swing.*;
import java.awt.event.*;


public class CeasarCipher extends JFrame {
    private JTextArea plainText;
    private JTextArea cipherText;
    private JTextField keyField;
    private JComboBox<String> cipherSelector;

    public CeasarCipher() {
        // Phần tiêu đề.
        setTitle("Ceasar Cipher");
        setSize(600, 164);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tạo các thành phần xử lý chính.
        plainText = new JTextArea(5, 20);
        cipherText = new JTextArea(5, 20);
        keyField = new JTextField(5);
        
        // 
        String[] ciphers = {"Caesar"};
        cipherSelector = new JComboBox<>(ciphers); 

        JButton encryptButton = new JButton("Encryption");
        JButton decryptButton = new JButton("Decryption");

        // Panel chính
        JPanel panel = new JPanel();
        panel.add(new JLabel("Input"));
        panel.add(new JScrollPane(plainText));
        panel.add(new JLabel("Key:"));
        panel.add(keyField);
        panel.add(new JLabel("Output"));
        panel.add(new JScrollPane(cipherText));
        panel.add(encryptButton);
        panel.add(decryptButton);
        add(panel);

        // Xử lý kí tự ở Input với key.
        encryptButton.addActionListener((ActionEvent e) -> {
            String input = plainText.getText();// plainText
            String key = keyField.getText();
            String selectedCipher = (String) cipherSelector.getSelectedItem();
            String result = encrypt(input, key, selectedCipher);
            cipherText.setText(result);
        });
        // Xử lý kí tự ở Output với key.
        decryptButton.addActionListener((ActionEvent e) -> {
            String input = plainText.getText(); // plainText
            String key = keyField.getText();
            String selectedCipher = (String) cipherSelector.getSelectedItem();
            String result = decrypt(input, key, selectedCipher);
            cipherText.setText(result);
        });
    }
        // Xử lý kí tự ở Input với key.
    private String encrypt(String input, String key, String cipher) {
        return switch (cipher) {
            case "Caesar" -> caesarEncrypt(input, Integer.parseInt(key));
            default -> "";
        };
    }
        // Xử lý kí tự ở Output với key.
    private String decrypt(String input, String key, String cipher) {
        return switch (cipher) {
            case "Caesar" -> caesarDecrypt(input, Integer.parseInt(key));
            default -> "";
        };
    }
    
 // ============ Thuật Toán Ceasar =================
    public static String caesarEncrypt(String text, int shift) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
        char c = text.charAt(i);
        if (Character.isUpperCase(c)) {
            char ch = (char)(((int) c + shift - 65) % 26 + 65);
            result.append(ch);
        } else if (Character.isLowerCase(c)) {
            char ch = (char)(((int) c + shift - 97) % 26 + 97);
            result.append(ch);
        } else {
            result.append(c);
        }
    }
    return result.toString();
}

public static String caesarDecrypt(String text, int shift) {
    return caesarEncrypt(text, 26 - shift);
}
 // ============= MAIN ================
    // Các hàm mã hóa, giải mã sẽ được định nghĩa bên dưới
    public static void main(String[] args) {
        CeasarCipher app = new CeasarCipher();
        app.setVisible(true);
    }
}
