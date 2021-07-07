package bisect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Utils {
    /**
     * Executes external (say git) command and returns its output.
     * External command must return to stdout some text as result or return error message to stdout or stderr.
     * For external command not returning text results, but just int exit code - see runSimpleExtCommand() method
     * If external command does not return anything into stdout, then stderr is read and all stderr lines added
     * to returned result starting with "ERROR:" in first line.
     * Callers can check the first line of returned List and if it is not equal to "ERROR:" it means command returned
     * the data, otherwise returned list contains the error message lines
     *
     * @param command - git command with options and arguments
     * @return collection of strings
     */
    public static ArrayList<String> runExtCommand(String... command) {
        ArrayList<String> result = new ArrayList<>();

        ProcessBuilder builder = new ProcessBuilder(command);
        //redirect builder process stdout and stderr to application streams
        builder.redirectOutput(ProcessBuilder.Redirect.PIPE);
        builder.redirectError(ProcessBuilder.Redirect.PIPE);

        try {
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //read stdout redirected from builder process to the application process
            reader.lines().forEach(result::add);

            //if nothing is in builder stdout so read its stderr
            if (result.size() == 0) {
                result.add("ERROR:");
                BufferedReader erroreader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                erroreader.lines().forEach(result::add);
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Executes simple external command and returns its int exit code. Simple - means generating no output except
     * int exit code
     * @param command - command with options and arguments
     * @return int exit code
     */
    public static int runSimpleExtCommand(String... command) {
        int exitCode = 0;
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectOutput(ProcessBuilder.Redirect.PIPE);
        try {
            Process process = builder.start();
            //must read stdout although we do not need it
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            process.waitFor();
            exitCode = process.exitValue();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return exitCode;
    }
}