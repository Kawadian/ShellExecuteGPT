package FileHandler;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ScriptMaker {
    public void makeScript(String scriptPath, String script) {
        String[] scriptLine = script.split("\n");
        Path path = Path.of(scriptPath);
        try {
            if (!(Files.exists(path))) {
                Files.createFile(path);
            }
        } catch (Exception e) {
            System.out.println("ファイルの作成中にエラーが発生しました: " + e.getMessage());
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.append("#!/bin/bash" + "\n");
            for (String line : scriptLine) {
                writer.append(line);
            }
        } catch (Exception e) {
            System.out.println("ファイルへの書き込み中にエラーが発生しました: " + e.getMessage());
        }
    }

    public void removeScript(String scriptPath){
        Path path = Path.of(scriptPath);
        try {
            Files.deleteIfExists(path);
        }catch (Exception e){
            System.out.println("ファイルの削除中にエラーが発生しました");
        }
    }
}
