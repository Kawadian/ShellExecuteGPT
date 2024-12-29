package ShellHandler;

import FileHandler.ScriptMaker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ScriptExecuter {
    public static String executeScript(String path,String script, boolean forceExecute) {
        if (!forceExecute) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("実行されるコマンドは以下の通りです。");
            System.out.println(script);
            boolean isContinue = false;
            while (!isContinue) {
                System.out.println("本当に実行しますか？ y/n");
                switch (scanner.nextLine().toLowerCase()) {
                    case "y":
                        isContinue = true;
                        continue;
                    case "n":
                        System.out.println("キャンセルされました。");
                        return "";
                    default:
                        System.out.println("yまたはnを入力してください。");
                }
            }
        }

        ScriptMaker scriptMaker = new ScriptMaker();
        scriptMaker.makeScript(path, script);
        String stdOut = null;
        try {
            //スクリプト実行
            ProcessBuilder pb = new ProcessBuilder("bash", path);
            Process process = pb.start();
            process.waitFor();
            //エラー出力
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                stdOut = buffer.lines().collect(Collectors.joining("\n"));
                System.out.println(stdOut);
            }

            //標準出力
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                stdOut = buffer.lines().collect(Collectors.joining("\n"));
                System.out.println(stdOut);
            }

        } catch (Exception e) {
            System.out.println("スクリプト実行中にエラーが発生しました" + e
            );
        } finally {
            scriptMaker.removeScript(path);
        }
        return stdOut;
    }
}
