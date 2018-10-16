import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class HashAttack {
    private ClassLoader classLoader = getClass().getClassLoader();
    private Map<String, String> commonPasswordHashes = new HashMap<>();
    private LinkedList<String> hashedPasswords = new LinkedList<>();


    void run() {

        try {
            loadCommonPasswordHashes();
            loadHashedPasswords();
            findMatchingPasswords();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void loadCommonPasswordHashes() throws IOException {
        File file = new File(classLoader.getResource("commonpasswords.txt").getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String password;
        while ((password = br.readLine()) != null) {
            String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
            commonPasswordHashes.put(password, sha256hex);
        }
    }

    private void loadHashedPasswords() throws IOException {
        File file = new File(classLoader.getResource("hashedpasswords.txt").getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String hash;
        while ((hash = br.readLine()) != null) {
            hashedPasswords.add(hash);
        }
    }

    private void findMatchingPasswords() {
        for (String hash : hashedPasswords) {
            for (String password : commonPasswordHashes.keySet()) {
                if (hash.equals(commonPasswordHashes.get(password))) {
                    System.out.println("Password cracked: " + password + " equals: " + hash);
                }
            }
        }
    }
}

