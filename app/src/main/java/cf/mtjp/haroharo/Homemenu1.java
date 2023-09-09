package cf.mtjp.haroharo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class Homemenu1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Homemenu1 アクティビティで使用する XML レイアウトファイルを指定
        setContentView(R.layout.home_menu_1);

        // アクティビティが開始された瞬間に準備中ダイアログを表示
        showPreparingDialog();
    }

    private void showPreparingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("準備中");
        builder.setMessage("この機能は現在準備中です。");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // ダイアログを閉じた後に一つ前の画面に戻る
                onBackPressed();
            }
        });

        // ダイアログを表示
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 一つ前の画面に戻る
    }
}
