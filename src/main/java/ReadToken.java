import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadToken {
    public static String readToken() {
        //Vars
        File tokenFile = new File("token.txt");
        Scanner s = null;
        try {
            s = new Scanner(tokenFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "Error";
        }

        String token = s.nextLine();
        return token;
    }
}
