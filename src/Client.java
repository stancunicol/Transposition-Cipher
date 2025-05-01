import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter message to encrypt: ");
        String message = stdIn.readLine();

        String key;
        boolean validKey;
        do {
            System.out.print("Enter key (distinct letters only): ");
            key = stdIn.readLine();
            validKey = isValidKey(key);
            if (!validKey) {
                System.out.println("Invalid key! Key must contain only distinct letters.");
            }
        } while (!validKey);

        String encrypted = encrypt(message, key);
        System.out.println("Encrypted text: " + encrypted);

        out.println(encrypted);
        out.println(key);
        out.println(message.length());

        String decrypted = in.readLine();
        System.out.println("Decrypted message from server: " + decrypted);

        socket.close();
    }

    private static boolean isValidKey(String key) {
        if (key == null || key.isEmpty()) {
            return false;
        }

        Set<Character> seen = new HashSet<>();
        for (char c : key.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
            char lowerC = Character.toLowerCase(c);
            if (seen.contains(lowerC)) {
                return false;
            }
            seen.add(lowerC);
        }
        return true;
    }

    public static String encrypt(String message, String key) {
        String upperKey = key.toUpperCase();
        int keyLength = upperKey.length();
        int messageLength = message.length();
        int rows = (int) Math.ceil((double) messageLength / keyLength);

        char[][] matrix = new char[rows][keyLength];
        int charIndex = 0;
        char fillChar = 'a';

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < keyLength; j++) {
                if (charIndex < messageLength) {
                    matrix[i][j] = message.charAt(charIndex++);
                } else {
                    matrix[i][j] = fillChar++;
                }
            }
        }

        List<Character> keyChars = new ArrayList<>();
        for (char c : upperKey.toCharArray()) {
            keyChars.add(c);
        }

        List<Character> sortedKey = new ArrayList<>(keyChars);
        Collections.sort(sortedKey);

        StringBuilder encrypted = new StringBuilder();
        for (char c : sortedKey) {
            int col = keyChars.indexOf(c);
            for (int row = 0; row < rows; row++) {
                encrypted.append(matrix[row][col]);
            }
        }

        return encrypted.toString();
    }
}