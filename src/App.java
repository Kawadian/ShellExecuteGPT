
import OpenaiMessageHandler.MessageManager;
import OpenaiMessageHandler.OpenaiRequester;
import java.util.Scanner;

public class App {
    public static void app(){
        String scriptPath = "./tempScript.sh";
        OpenaiRequester openaiRequester = new OpenaiRequester();
        String apiKey = openaiRequester.getApiKey();
        Scanner scanner = new Scanner(System.in);
        //System.out.println("要望を入力");
        //String userPrompt = scanner.nextLine();
        //String systemPrompt = "これからあなたにはユーザーの入力、場合によっては追加でコマンドの実行結果が渡されます。あなたはユーザーの入力に答えるためにシェルスクリプトを生成してください。これ以上実行する操作がない場合は「done」と出力してください";
        String systemPrompt = "";
        //ScriptExecuter.executeScript(scriptPath,"echo 'Hello World'");
        String[] messages = {"こんにちは", "こんにちは！", "現在会話の履歴が正しく残っているかを確認しています。先ほどの会話を復唱してください。"};
        String[] openaiText = openaiRequester.requestOpenai(systemPrompt, messages,apiKey);
        if(openaiText!=null){
            MessageManager.displayMessages(openaiText);
        }else{
            System.out.println("openai apiへのリクエスト送信中にエラーが発生しました。");
        }

    }
}
