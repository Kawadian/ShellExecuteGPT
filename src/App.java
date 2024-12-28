import FileHandler.ScriptMaker;
import WebRequestHandler.OpenaiRequester;

import java.nio.file.Path;
import java.util.Scanner;

public class App {
    public static void app(){
        String scriptPath = "./tempScript.sh";
        String apiKey = "sk-";
        Scanner scanner = new Scanner(System.in);
        System.out.println("要望を入力");
        String userPrompt = scanner.nextLine();
        //String systemPrompt = "これからあなたにはユーザーの入力、場合によっては追加でコマンドの実行結果が渡されます。あなたはユーザーの入力に答えるためにシェルスクリプトを生成してください。これ以上実行する操作がない場合は「done」と出力してください";
        String systemPrompt = "";
        ScriptExecuter.executeScript(scriptPath,"echo 'Hello Worrrrrld'");
        OpenaiRequester openaiRequester = new OpenaiRequester();
        String openaiText = openaiRequester.requestOpenai(systemPrompt, "こんにちは",apiKey);
        System.out.println(openaiText);
    }
}
