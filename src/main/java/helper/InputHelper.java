package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

public class InputHelper {

    private final File databaseFile;
    private final File resetFile;
    private final Properties properties;

    public InputHelper() throws FileNotFoundException, IOException {

        resetFile = new File("sql", "initial-values.sql");

        databaseFile = new File("db", "milkshakes-app.db");

        properties = new Properties();

        properties.load(new FileInputStream("src/main/java/properties/strings.properties"));
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    public String getPropertyWithValue(String name, Object value) {
        return properties.getProperty(name).replaceAll("#", String.valueOf(value));
    }

    public List<String> readResetFile() throws IOException {
        return Files.readAllLines(resetFile.toPath());
    }

    public String readResetFile(String concat) throws IOException {
        return readFile(resetFile, concat);
    }

    public String readFile(File file, String concat) throws IOException {

        // TODO: Use Java's forEach for this.
        List<String> lines = Files.readAllLines(file.toPath());

        String output = "";

        for (int i = 0; i < lines.size(); i++) {
            output += lines.get(i) + concat;
        }

        return output;
    }

    public String getDatabaseFilePath() {
        return databaseFile.getAbsolutePath();
    }

}
