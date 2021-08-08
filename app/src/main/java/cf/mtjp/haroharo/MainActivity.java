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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {


	private Toolbar _toolbar;
	private FloatingActionButton _fab;

	private WebView webview1;
	private Vibrator vib;
	private MediaPlayer mp;
	private AlertDialog.Builder pop;
	private ProgressBar progressBar;
	private TutorialActivity Companion;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		setTheme(R.style.AppTheme);
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
		TutorialActivity.Companion.showIfNeeded(MainActivity.this,_savedInstanceState);
		progressBar = (ProgressBar) findViewById(R.id.sc_tov_pb_progress_bar);
		progressBar.setVisibility(View.INVISIBLE);
		webview1 = (WebView) findViewById(R.id.sc_tov_wv_tos);
		webview1.setWebViewClient(new WebViewClient() {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			progressBar.setVisibility(View.VISIBLE);
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view,url);
			progressBar.setVisibility(View.INVISIBLE);
		}
	});
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
			}
		});
	}

	private void initializeLogic() {
		webview1.loadUrl("https://appshizuoka.gq");
		vib.vibrate((long) (100));
		pop.setTitle("ようこそ");
		pop.setIcon(R.drawable.app_icon);
		pop.setMessage("この度は静岡県新型コロナウイルス対策アプリをダウンロード頂きありがとうございます。\nこのアプリは静岡県民の皆様に新型コロナウイルスに対する正しい知識を得て頂きたく作成いたしました。\n今後アップデートなどのアナウンスはこちらのTwitterアカウント（ https://mobile.twitter.com/Shizuoka_COVID ）より配信いたしますのでフォローの程よろしくお願い致します。\n\n==============\nこのアプリは静岡県公式のものではありません。\nバージョン1.0.0 \n");
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
}