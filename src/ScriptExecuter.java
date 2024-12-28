import FileHandler.ScriptMaker;
import FileHandler.ScriptReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ScriptExecuter {
    public static void executeScript(String path,String script){
        ScriptMaker scriptMaker = new ScriptMaker();
        scriptMaker.makeScript(path, script);
        try {
            //スクリプト実行
            ProcessBuilder pb = new ProcessBuilder("bash", path);
            Process process = pb.start();
            process.waitFor();
            //エラー出力
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                System.out.println(buffer.lines().collect(Collectors.joining("\n")));
            }

            //標準出力
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                System.out.println(buffer.lines().collect(Collectors.joining("\n")));
            }

        }catch (Exception e){
            System.out.println("スクリプト実行中にエラーが発生しました" + e
            );
        }finally {
            scriptMaker.removeScript(path);
        }
    }
}
