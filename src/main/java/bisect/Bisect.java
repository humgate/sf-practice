package bisect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bisect {
    public static void main(String[] args) {
        String[] command = new String[] {"git", "rev-list", "--ancestry-path",
                "3ceeb3be2bda3e03d3908b64d65d7255a23ecec4..eb5fdfccea74bd34118726d8065f48698bb3c069"};

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.inheritIO().redirectOutput(ProcessBuilder.Redirect.PIPE);

        try {
            Process process = builder.start();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {

                reader.lines().forEach(line -> System.out.println("R: " + line));

            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
