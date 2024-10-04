import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class SubstitutionCipher extends JFrame {
    // Bảng chữ cái
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public SubstitutionCipher() {
        // Tạo giao diện Swing
        setTitle("Substitution Cipher");
        setSize(450, 195);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo các thành phần
        JTextArea inputText = new JTextArea(5, 20);
        JLabel label2 = new JLabel("Key");
        JTextField keyText = new JTextField(25);
        JButton encryptButton = new JButton("Encryption");
        JButton decryptButton = new JButton("Decryption");
        JButton randomKeyButton = new JButton("Random Key");
        JTextArea resultText = new JTextArea(5, 20);
        
        // Bố trí giao diện
        JPanel panel = new JPanel();
        panel.add(inputText);
        panel.add(new JScrollPane(resultText));
        panel.add(label2);
        panel.add(keyText);
        panel.add(randomKeyButton); // Nút tạo key ngẫu nhiên
        panel.add(encryptButton);
        panel.add(decryptButton); // Nút giải mã
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

        // Xử lý khi nhấn nút tạo key ngẫu nhiên
        randomKeyButton.addActionListener((ActionEvent e) -> {
            String randomKey = generateRandomKey(); // Tạo key ngẫu nhiên
            keyText.setText(randomKey);
        });
    }

    // Hàm tạo key ngẫu nhiên
    private static String generateRandomKey() {
        char[] alphabetArray = ALPHABET.toCharArray();
        Random random = new Random();

        // Hoán đổi ngẫu nhiên các ký tự trong mảng
        for (int i = 0; i < alphabetArray.length; i++) {
            int randomIndex = random.nextInt(alphabetArray.length);
            // Đổi vị trí ký tự tại i với ký tự tại randomIndex
            char temp = alphabetArray[i];
            alphabetArray[i] = alphabetArray[randomIndex];
            alphabetArray[randomIndex] = temp;
        }

        return new String(alphabetArray); // Trả về key đã hoán đổi
    }

    // Hàm mã hóa Bảng chữ đơn
    private static String encrypt(String text, String key) {
        StringBuilder encryptedText = new StringBuilder();

        // Mã hóa từng ký tự
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            // Nếu ký tự là chữ cái, mã hóa
            if (ALPHABET.indexOf(currentChar) != -1) {
                int charIndex = ALPHABET.indexOf(currentChar);
                encryptedText.append(key.charAt(charIndex));
            } else {
                // Nếu không phải chữ cái, giữ nguyên
                encryptedText.append(currentChar);
            }
        }
        return encryptedText.toString();
    }

    // Hàm giải mã Bảng chữ đơn
    private static String decrypt(String text, String key) {
        StringBuilder decryptedText = new StringBuilder();

        // Giải mã từng ký tự
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            // Nếu ký tự là chữ cái, giải mã
            if (key.indexOf(currentChar) != -1) {
                int charIndex = key.indexOf(currentChar);
                decryptedText.append(ALPHABET.charAt(charIndex));
            } else {
                // Nếu không phải chữ cái, giữ nguyên
                decryptedText.append(currentChar);
            }
        }
        return decryptedText.toString();
    }
// ======================= MAIN ==================
    public static void main(String[] args) {
        // Chạy giao diện
        SwingUtilities.invokeLater(() -> {
            SubstitutionCipher cipher = new SubstitutionCipher();
            cipher.setVisible(true);
        });
    }
}
