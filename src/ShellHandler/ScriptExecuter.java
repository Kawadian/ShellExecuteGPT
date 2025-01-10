package ShellHandler;

import FileHandler.ScriptMaker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ScriptExecuter {

    public static String executeScript(String path, String script, boolean forceExecute) {
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
        String stdOut = "";

        try {
            // スクリプトを実行
            ProcessBuilder pb = new ProcessBuilder("bash", path);
            Process process = pb.start();

            // 標準出力のリアルタイム処理
            Thread stdOutThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("[標準出力] " + line);
                    }
                } catch (Exception e) {
                    System.out.println("[標準出力処理中にエラー発生] " + e.getMessage());
                }
            });

            // エラー出力のリアルタイム処理
            Thread errOutThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println("[エラー出力] " + line);
                    }
                } catch (Exception e) {
                    System.err.println("[エラー出力処理中にエラー発生] " + e.getMessage());
                }
            });

            stdOutThread.start();
            errOutThread.start();

            // プロセスの終了を待機
            int exitCode = process.waitFor();
            stdOutThread.join();  // 標準出力スレッドの終了を待機
            errOutThread.join();  // エラー出力スレッドの終了を待機

            System.out.println("プロセス終了 (コード: " + exitCode + ")");

        } catch (Exception e) {
            System.err.println("スクリプト実行中にエラーが発生しました: " + e.getMessage());
        } finally {
            // 一時スクリプトを削除
            scriptMaker.removeScript(path);
        }

        return stdOut;
    }
}