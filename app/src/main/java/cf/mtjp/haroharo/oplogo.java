package cf.mtjp.haroharo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class oplogo extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 6000; // 6秒

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oplogo);

        // LottieAnimationViewを取得
        LottieAnimationView animationView = findViewById(R.id.animationView);

        // JSON アニメーションファイルを読み込む
        animationView.setAnimation(R.raw.opanime1);

        // アニメーションを再生
        animationView.playAnimation();

        // スプラッシュ画面を一定時間表示後にメイン画面に遷移する
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // メイン画面（MainActivity）に遷移するためのIntentを作成
                Intent mainIntent = new Intent(oplogo.this, MainActivity.class);
                startActivity(mainIntent);
                // スプラッシュ画面を終了
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
