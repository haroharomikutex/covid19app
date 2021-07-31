package com.haroharo.shizuoka;

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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import static android.widget.ProgressBar.*;


public class MainActivity extends AppCompatActivity {


	private Toolbar _toolbar;
	private FloatingActionButton _fab;

	private WebView webview1;
	private Vibrator vib;
	private MediaPlayer mp;
	private AlertDialog.Builder pop;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		setTheme(R.style.AppTheme);
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();


	}


	private void initialize(Bundle _savedInstanceState) {

		_fab = (FloatingActionButton) findViewById(R.id._fab);

		webview1 = (WebView) findViewById(R.id.sc_tov_wv_tos);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		pop = new AlertDialog.Builder(this);

		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				super.onPageStarted(_param1, _param2, _param3);

			}

			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				super.onPageFinished(_param1, _param2);

			}

		});

		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				webview1.loadUrl("https://appshizuoka.gq");
			}
		});
	}

	private void initializeLogic() {
		webview1.loadUrl("https://appshizuoka.gq");
		vib.vibrate((long) (100));
		pop.setTitle("ようこそ");
		pop.setIcon(R.drawable.app_icon);
		pop.setMessage("この度は静岡県新型コロナウイルス対策アプリをダウンロード頂きありがとうございます。\nこのアプリは静岡県民の皆様に新型コロナウイルスに対する正しい知識を得て頂きたく作成いたしました。\n今後アップデートなどのアナウンスはこちらのTwitterアカウント（ https://mobile.twitter.com/Shizuoka_COVID ）より配信いたしますのでフォローの程よろしくお願い致します。\n\n==============\nこのアプリは静岡県公式のものではありません。\nバージョン3.0.0 \n");
		pop.setPositiveButton("スタート", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				SketchwareUtil.showMessage(getApplicationContext(), "読み込み中...");
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

	public interface AppWebViewClientCallback {
		/**
		 * ページ表示直前ハンドル
		 * <p>
		 * Webページの表示直前に行う処理
		 * </p>
		 */
		void onPageCommitVisibleHandle();
	}

	@Deprecated
	public class AppWebViewClient extends WebViewClient {

		/**
		 * コールバック
		 */
		private final AppWebViewClientCallback callback;

		/**
		 * コンストラクタ
		 *
		 * @param callback コールバック
		 */
		public AppWebViewClient(AppWebViewClientCallback callback) {
			this.callback = callback;
		}

		/**
		 * ページ表示直前
		 *
		 * @param view WebView
		 * @param url  URL
		 */
		@Override
		public void onPageCommitVisible(WebView view, String url) {
			super.onPageCommitVisible(view, url);

			// Webページ表示直前処理を実施
			callback.onPageCommitVisibleHandle();
		}
	}

	public class MyWebView extends AppCompatActivity {

		private ProgressBar mProgressBar;

		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);

			final WebView webView = findViewById(R.id.sc_tov_wv_tos);
			mProgressBar = findViewById(R.id.sc_tov_pb_progress_bar);


			webView.setWebChromeClient(new WebChromeClient() {

				/* 読み込み状況を取得してプログレスバーにセット、表示 */
				@Override
				public void onProgressChanged(WebView wv, int progress) {
					super.onProgressChanged(wv, progress);

					mProgressBar.setProgress(progress);
				}
			});

			webView.setWebViewClient(new WebViewClient() {

				/* 読み込みを開始すると呼ばれるメソッド */
				@Override
				public void onPageStarted(WebView wb, String url, Bitmap bm) {
					super.onPageStarted(wb, url, bm);

					/* プログレスバーを表示 */
					mProgressBar.setVisibility(View.VISIBLE);
				}

				/* 読み込みが完了すると呼ばれるメソッド */
				@Override
				public void onPageFinished(WebView wb, String url) {
					super.onPageFinished(wb, url);

					//消す
					mProgressBar.setVisibility(View.INVISIBLE);
				}
			});
		}
	}
}