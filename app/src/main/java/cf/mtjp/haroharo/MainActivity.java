package cf.mtjp.haroharo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;


public class MainActivity extends AppCompatActivity {

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
	private boolean isPlaySelected = false; // 選択結果を保持するフラグ

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setTheme(R.style.AppTheme);
		setContentView(R.layout.main);
		animationView = findViewById(R.id.sc_tov_pb_progress_bar2);
		progressBar = findViewById(R.id.sc_tov_pb_progress_bar);
		if (progressBar != null) {
			progressBar.setVisibility(View.INVISIBLE);
		}
		initialize(_savedInstanceState);
		showPlayConfirmationDialog2();
		initializeLogic();
		progressBar.setVisibility(View.INVISIBLE);
		webview1 = findViewById(R.id.sc_tov_wv_tos);
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

	private void showPlayConfirmationDialog2() {
		// SharedPreferencesから選択結果を読み出す
		SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
		isPlaySelected = preferences.getBoolean("isPlaySelected", false);

		if (!isPlaySelected) { // isPlaySelectedがfalseの場合にのみダイアログを表示
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("再生確認");
			builder.setMessage("オーディオを再生しますか？");
			// ユーザーが選択した結果を保存
			builder.setPositiveButton("再生する", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					// 選択を保存（"isPlaySelected"というキーでtrueを保存）
					SharedPreferences.Editor editor = preferences.edit();
					editor.putBoolean("isPlaySelected", true);
					editor.apply();
					isPlaySelected = true; // フラグも更新

					// MediaPlayerを初期化してOGGオーディオファイルを再生
					mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.soundlogo);
					mediaPlayer.start();
				}
			});
			builder.setNegativeButton("再生しない", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					// ユーザーが再生しないを選択した場合の処理（何もしない）
					isPlaySelected = true; // フラグを更新し、再生を選択した場合のみtrueにする

					// 選択を保存（"isPlaySelected"というキーでfalseを保存）
					SharedPreferences.Editor editor = preferences.edit();
					editor.putBoolean("isPlaySelected", true);
					editor.apply();
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		// それ以外の場合は何もしない
	}






	private void initialize(Bundle _savedInstanceState) {
		_fab = findViewById(R.id._fab);
		webview1 = findViewById(R.id.sc_tov_wv_tos);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		pop = new AlertDialog.Builder(this);

		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				super.onPageStarted(_param1, _param2, _param3);
				//progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				super.onPageFinished(_param1, _param2);
				//progressBar.setVisibility(View.INVISIBLE);
			}

		});

		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				webview1.loadUrl("https://appshizuoka.gq");
				Toasty.success(getApplicationContext(), "ホームに戻っています", Toast.LENGTH_SHORT, true).show();
				Toasty.warning(getApplicationContext(), "読み込みに時間がかかる場合があります", Toast.LENGTH_SHORT, true).show();
			}
		});
	}

	private void initializeLogic() {
		webview1.loadUrl("https://appshizuoka.gq");
		vib.vibrate((long) (100));
		pop.setTitle("ようこそ");
		pop.setIcon(R.drawable.image_10);
		pop.setMessage("【開発者からのお知らせ】\n静岡県のアプリコンテストで見事、本アプリが優秀賞を受賞しました！\n今後本格的な実用化に向けて様々な機関と連携して開発して参ります！\n==============\nこの度はシズスマ！(静岡スマート情報ポータルアプリ)をダウンロード頂きありがとうございます。\nこのアプリは新型コロナウイルスによって打撃を受けた静岡県の経済活動を活性化するとともに食品ロスの削減や自然豊かな静岡県の魅力を県内外にPRするアプリです。\n今後アプリに関するアナウンスはこちらのTwitterアカウント（ https://mobile.twitter.com/Shizuoka_COVID ）より配信いたしますのでフォローの程よろしくお願い致します。\n\n==============\nバージョン11.0.0 \n");
		pop.setPositiveButton("スタート", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				Toasty.info(getApplicationContext(), "データベース接続立証中\nしばらくお待ちください...", Toast.LENGTH_SHORT, true).show();
			}
		});
		pop.create().show();
	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);

		switch (_requestCode) {

			default:
				break;
		}
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
	}

}
