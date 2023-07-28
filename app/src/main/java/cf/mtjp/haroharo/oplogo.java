package cf.mtjp.haroharo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import es.dmoral.toasty.Toasty;

import android.content.SharedPreferences;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
public class oplogo extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private Toolbar _toolbar;
    private FloatingActionButton _fab;
    private LottieAnimationView animationView;
    private WebView webview1;
    private Vibrator vib;
    private AlertDialog.Builder pop;
    private ProgressBar progressBar; // 修正された行
    private TutorialActivity Companion;
    private Toasty Toasty;
    private MediaPlayer mediaPlayer;


    // スプラッシュ画面の表示時間（ミリ秒単位）
    private static final int SPLASH_DISPLAY_LENGTH = 3000; // 3秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oplogo);

        // 動画再生の準備
		playerView = findViewById(R.id.videoView);
		player = new SimpleExoPlayer.Builder(this).build();
		playerView.setPlayer(player);
        PlayerView videoView = findViewById(R.id.videoView);
        videoView.setUseController(false);


        // 動画ファイルのURIを取得
		Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/raw/hellov1");

        // MediaItemに変換
		MediaItem mediaItem = MediaItem.fromUri(videoUri);

        // プレーヤーに動画ソースをセット
		player.setMediaItem(mediaItem);
		player.prepare();
		player.play();
        // スプラッシュ画面を一定時間表示後にメイン画面に遷移する
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // メイン画面（MainActivity）に遷移するためのIntentを作成
                Intent mainIntent = new Intent(oplogo.this, MainActivity.class);
                startActivity(mainIntent);
                finish(); // スプラッシュ画面を終了
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
