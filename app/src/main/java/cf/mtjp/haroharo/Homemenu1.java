package cf.mtjp.haroharo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Homemenu1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Homemenu1 アクティビティで使用する XML レイアウトファイルを指定
        setContentView(R.layout.home_menu_1);

        // "EmergencyCallSystem" ボタンを追加
        Button emergencyCallSystemButton = findViewById(R.id.emergencyCallSystemButton);
        emergencyCallSystemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "EmergencyCallSystem" ボタンがクリックされたときの処理
                showConfirmationDialog();
            }
        });
        Button btnBack = findViewById(R.id.btnBack2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // アクティビティを終了して前の画面に戻る
                finish();
            }
        });
    }

    // 確認ダイアログを表示するメソッド
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("緊急速報システムセットアップ");
        builder.setIcon(R.drawable.attention);
        builder.setMessage("かんたん緊急速報システムを設定しますか？\nこの機能を使用すると、緊急時ワンタップで110番通報及び119番に通報することができます");
        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // "はい" ボタンがクリックされた場合、選択に応じてポップアップを表示
                showPopupForSelection("ウィジット設置方法");
            }
        });
        builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // "いいえ" ボタンがクリックされた場合、何もしない
            }
        });

        // ダイアログを表示
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    // 選択に応じてポップアップダイアログを表示するメソッド
    private void showPopupForSelection(String selection) {
        if ("ウィジット設置方法".equals(selection)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("緊急通報ウィジット設定方法");
            builder.setMessage("1.ホーム画面に移動します\n2.ホーム画面の空いているスペースを長押しします\n3.「ウィジット」をタップします\n4.ウィジット一覧から「SOS」を探し長押しします\n5.ウィジットをホームスクリーン状にドラック＆ドロップします");
            builder.setPositiveButton("閉じる", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // ダイアログを閉じる
                }
            });

            // ポップアップダイアログを表示
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        // 他の選択肢に対するポップアップダイアログの表示ロジックを追加できます
    }
}
