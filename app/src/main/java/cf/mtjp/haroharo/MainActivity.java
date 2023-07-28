package cf.mtjp.haroharo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Random;
import es.dmoral.toasty.Toasty;
import android.content.SharedPreferences;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;






// ビューのインスタンス



public class MainActivity extends AppCompatActivity {

	private PlayerView playerView;
	private SimpleExoPlayer player;
	private Toolbar _toolbar;
	private FloatingActionButton _fab;
	private LottieAnimationView animationView;
	private WebView webview1;
	private Vibrator vib;
	private AlertDialog.Builder pop;
	private ProgressBar progressBar;

	private TutorialActivity Companion;
	private Toasty Toasty;
	private MediaPlayer mediaPlayer;
	private boolean isPlaySelected = false;
	private static final int REQUEST_SETTINGS = 1;

	public static final String PREFS_NAME = "MyPrefs";
	public static final String KEY_JAVASCRIPT_ENABLED = "javascript_enabled";
	public static final String KEY_COOKIE_ENABLED = "cookie_enabled";

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setTheme(R.style.AppTheme);
		setContentView(R.layout.main);
		_fab = findViewById(R.id._fab);
		webview1 = findViewById(R.id.sc_tov_wv_tos);
		MaterialButton btnTutorial = findViewById(R.id.btnTutorial);
		vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		pop = new AlertDialog.Builder(this);
		animationView = findViewById(R.id.sc_tov_pb_progress_bar);
		initialize(_savedInstanceState);
		loadSettings();
		initializeLogic();
		btnTutorial.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
				startActivity(intent);
			}
		});
		MaterialButton btnSettings = findViewById(R.id.btnSettings);
		btnSettings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 設定画面を表示
				showSettings();
			}
		});



		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// 読み込みが開始したらアニメーションを表示
				animationView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// 読み込みが完了したらアニメーションを非表示に
				animationView.setVisibility(View.GONE);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
										String description, String url) {
				// オフライン時の処理
				Toasty.error(getApplicationContext(), "ネットワークを確認してください", Toast.LENGTH_SHORT, true).show();
				pop.setTitle("読み込みに失敗しました\n(サポートコード002)");
				pop.setIcon(R.drawable.attention);
				pop.setCancelable(false);
				pop.setInverseBackgroundForced(false);
				pop.setMessage("ネットワークの状況が悪い或いは技術的なエラーが発生しています。\n端末がネットワークに接続されている事を確認し\n再読み込みボタンを押して再読み込みを行ってください。\n");
				pop.setPositiveButton("再読み込み", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						webview1.reload();
						Toasty.success(getApplicationContext(), "接続中です。\nしばらくお待ちください...", Toast.LENGTH_SHORT, false).show();
					}
				});
				pop.setNegativeButton("前の画面に戻る", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						webview1.goBack();
						Toasty.success(getApplicationContext(), "前ページへ転送中です。\nしばらくお待ちください...", Toast.LENGTH_SHORT, false).show();
					}
				});
				pop.setNeutralButton("ホームへ戻る", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						webview1.loadUrl("https://appshizuoka.gq");
						Toasty.success(getApplicationContext(), "ホームへ転送中です。\nしばらくお待ちください...", Toast.LENGTH_SHORT, false).show();
					}
				});
				pop.create().show();
			}

		});
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_SETTINGS) {
			if (resultCode == RESULT_OK) {
				boolean javascriptEnabled = data.getBooleanExtra("javascript_enabled", true);
				boolean cookieEnabled = data.getBooleanExtra("cookie_enabled", true);
				applyWebViewSettings(javascriptEnabled, cookieEnabled);
			}
		}
	}

	private void showSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivityForResult(intent, REQUEST_SETTINGS);
	}

	private void applyWebViewSettings(boolean javascriptEnabled, boolean cookieEnabled) {
		webview1.getSettings().setJavaScriptEnabled(javascriptEnabled);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(cookieEnabled);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			cookieManager.setAcceptThirdPartyCookies(webview1, cookieEnabled);
		}
		// SharedPreferencesに設定を保存
		saveSettings(javascriptEnabled, cookieEnabled);
	}

	private void saveSettings(boolean javascriptEnabled, boolean cookieEnabled) {
		SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(KEY_JAVASCRIPT_ENABLED, javascriptEnabled);
		editor.putBoolean(KEY_COOKIE_ENABLED, cookieEnabled);
		editor.apply();
	}

	private void loadSettings() {
		SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		boolean javascriptEnabled = preferences.getBoolean(KEY_JAVASCRIPT_ENABLED, true);
		boolean cookieEnabled = preferences.getBoolean(KEY_COOKIE_ENABLED, true);
		applyWebViewSettings(javascriptEnabled, cookieEnabled);
	}



	private void initializeLogic() {
		webview1.loadUrl("https://appshizuoka.gq");
		vib.vibrate((long) (100));
		pop.setTitle("ようこそ");
		pop.setIcon(R.drawable.image_10);
		pop.setMessage("【開発者からのお知らせ】\n静岡県のアプリコンテストで見事、本アプリが優秀賞を受賞しました！\n今後本格的な実用化に向けて様々な機関と連携して開発して参ります！\n==============\nこの度はシズスマ！(静岡スマート情報ポータルアプリ)をダウンロード頂きありがとうございます。\nこのアプリは静岡県の経済活動を活性化するとともに食品ロスの削減や自然豊かな静岡県の魅力を県内外にPRするアプリです。\n今後アプリに関するアナウンスはこちらのXアカウント（ https://mobile.twitter.com/shizusuma_jp ）より配信いたしますのでフォローの程よろしくお願い致します。\n\n==============\nバージョン14.0.0 \n");
		pop.setPositiveButton("スタート", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toasty.info(getApplicationContext(), "データベース接続立証中\nしばらくお待ちください...", Toast.LENGTH_SHORT, true).show();
			}
		});
	}
	private void initialize(Bundle _savedInstanceState) {
		_fab = findViewById(R.id._fab);
		webview1 = findViewById(R.id.sc_tov_wv_tos);
		animationView = findViewById(R.id.sc_tov_pb_progress_bar);
		applyWebViewSettings(true, true);
		webview1.getSettings().setSupportZoom(true);
		vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		pop = new AlertDialog.Builder(this);


		View rootLayout = findViewById(R.id.linearLayout);
		progressBar = rootLayout.findViewById(R.id.sc_tov_pb_progress_bar);
		if(progressBar != null) {
			progressBar.setVisibility(View.INVISIBLE);
		}


		_fab.setOnClickListener(_view -> {
			webview1.loadUrl("https://appshizuoka.gq");
			Toasty.success(getApplicationContext(), "ホームに戻っています", Toast.LENGTH_SHORT, true).show();
			Toasty.warning(getApplicationContext(), "読み込みに時間がかかる場合があります", Toast.LENGTH_SHORT, true).show();
		});

	}





	@Override
	public void onBackPressed() {
		webview1.goBack();
	}

	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}

	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}

	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}

	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}

	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
				_result.add((double) _arr.keyAt(_iIdx));
		}
		return _result;
	}

	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}

	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}

	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}

	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if (url.startsWith("http:") || url.startsWith("https:")) {
			return false;
		}

		// Otherwise allow the OS to handle it
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(intent);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();//サウンド関係のresource開放
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		// プレーヤーのリソースを解放
		if (player != null) {
			player.release();
		}
	}


}