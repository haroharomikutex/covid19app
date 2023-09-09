package cf.mtjp.haroharo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Homemenu1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Homemenu1 アクティビティで使用する XML レイアウトファイルを指定
        setContentView(R.layout.home_menu_1);
    }

    public void onShowAllFeaturesButtonClick(View view) {
        // 別のアクティビティを開くためのインテントを作成
        Intent intent = new Intent(this, MainActivity.class);
        // インテントを実行して別のアクティビティを開く
        startActivity(intent);
    }
}
