package bisect;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Contains only main method which tests if HEAD in git repository contains Lib/bisect.py file
 * Exits with code 0 if yes or with code 1 if no.
 * Command must be run in the git folder
 */
public class Command {
    public static void main(String[] args) {
        if (Files.exists(Paths.get("Lib/bisect.py"))) {
            System.exit(0);
        } else {
            System.exit(1);
        }
    }
}
