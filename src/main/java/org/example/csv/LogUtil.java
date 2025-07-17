package org.example.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogUtil {
    private static final String LOG_FILE_PATH = "target/test-logs/test-output.txt";

    static {
        // Ensure the directory exists
        File logDir = new File("target/test-logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
    }

    public static void log(String format, Object... args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.printf(format, args);
            writer.println();
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }

    public static void clearLog() {
        try (PrintWriter writer = new PrintWriter(LOG_FILE_PATH)) {
            writer.print(""); // Clear file
        } catch (IOException e) {
            System.err.println("Failed to clear log: " + e.getMessage());
        }
    }

    public static String getLogFilePath() {
        return LOG_FILE_PATH;
    }
}
