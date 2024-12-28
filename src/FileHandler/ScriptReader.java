package FileHandler;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScriptReader {
    public String readScript(String scriptPath) {
        Path path = Path.of(scriptPath);
        System.out.println("Reading script from: " + path.toAbsolutePath());
        String script = "";
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            for (String line; (line = reader.readLine()) != null; ) {
                script += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("ファイルの読み込み中にエラーが発生しました: " + e.getMessage());
        }
        return script;
    }
}
