package cf.mtjp.haroharo;


import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch javascriptSwitch;
    private Switch cookieSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settinglayout1);

        // レイアウトからスイッチを取得
        javascriptSwitch = findViewById(R.id.switchJavaScript);
        cookieSwitch = findViewById(R.id.switchCookie);

        // 以前の設定を読み込んでスイッチの状態を更新
        loadSettings();

        // スイッチが変更されたときの処理を設定
        javascriptSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cookieSwitch.setEnabled(isChecked);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 設定を保存
        saveSettings();
    }

    private void loadSettings() {
        // 以前の設定を読み込んでスイッチの状態を更新
        boolean javascriptEnabled = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
                .getBoolean(MainActivity.KEY_JAVASCRIPT_ENABLED, true);
        boolean cookieEnabled = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
                .getBoolean(MainActivity.KEY_COOKIE_ENABLED, true);

        javascriptSwitch.setChecked(javascriptEnabled);
        cookieSwitch.setChecked(cookieEnabled);

        cookieSwitch.setEnabled(javascriptEnabled);
    }

    private void saveSettings() {

        // スイッチの現在の状態を取得
        boolean javascriptEnabled = javascriptSwitch.isChecked();
        boolean cookieEnabled = cookieSwitch.isChecked();

        // 設定をSharedPreferencesに保存
        getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putBoolean(MainActivity.KEY_JAVASCRIPT_ENABLED, javascriptEnabled)
                .putBoolean(MainActivity.KEY_COOKIE_ENABLED, cookieEnabled)
                .apply();

    }
}
