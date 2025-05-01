import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started. Waiting for client...");

        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String encrypted = in.readLine();
        String key = in.readLine();
        int originalLength = Integer.parseInt(in.readLine());

        String decrypted = decrypt(encrypted, key, originalLength);
        out.println(decrypted);

        System.out.println("Decrypted message: " + decrypted);

        clientSocket.close();
        serverSocket.close();
    }

    public static String decrypt(String encrypted, String key, int originalLength) {
        String upperKey = key.toUpperCase();
        int keyLength = upperKey.length();
        int encryptedLength = encrypted.length();
        int rows = encryptedLength / keyLength;

        char[][] matrix = new char[rows][keyLength];

        List<Character> keyChars = new ArrayList<>();
        for (char c : upperKey.toCharArray()) {
            keyChars.add(c);
        }

        List<Character> sortedKey = new ArrayList<>(keyChars);
        Collections.sort(sortedKey);

        int strIndex = 0;
        for (char c : sortedKey) {
            int col = keyChars.indexOf(c);
            for (int row = 0; row < rows; row++) {
                if (strIndex < encryptedLength) {
                    matrix[row][col] = encrypted.charAt(strIndex++);
                }
            }
        }

        StringBuilder decrypted = new StringBuilder();
        int count = 0;
        outer:
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < keyLength; j++) {
                if (count >= originalLength) {
                    break outer;
                }
                decrypted.append(matrix[i][j]);
                count++;
            }
        }

        return decrypted.toString();
    }
}