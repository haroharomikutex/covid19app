package cf.mtjp.haroharo;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.NotificationManagerCompat;

public class SettingsActivity extends AppCompatActivity {
    private Switch javascriptSwitch;
    private Switch cookieSwitch;
    private Button notificationButton;
    private Group settingsGroup;
    private static final int REQUEST_NOTIFICATION_PERMISSION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settinglayout1);

        // レイアウトからスイッチとボタンを取得
        javascriptSwitch = findViewById(R.id.switchJavaScript);
        cookieSwitch = findViewById(R.id.switchCookie);
        notificationButton = findViewById(R.id.switchNotification); // 通知のボタンに変更
        settingsGroup = findViewById(R.id.settingsGroup);

        // スイッチが変更されたときの処理を設定
        javascriptSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cookieSwitch.setEnabled(isChecked);
        });

        // 通知ボタンがクリックされたときの処理を設定
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通知ボタンがクリックされたときに通知許可を確認するダイアログを表示
                showNotificationPermissionDialog();
            }
        });

        Button showSettingsButton = findViewById(R.id.showSettingsButton);
        showSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // settingsGroup の visibility を切り替えて表示・非表示を切り替える
                if (settingsGroup.getVisibility() == View.VISIBLE) {
                    settingsGroup.setVisibility(View.GONE); // 非表示
                } else {
                    settingsGroup.setVisibility(View.VISIBLE); // 表示
                }
            }
        });

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // アクティビティを終了して前の画面に戻る
                finish();
            }
        });

        // 設定の読み込み
        loadSettings();
    }

    // 通知ボタンの状態を設定
    private void setNotificationButtonState(boolean enabled) {
        notificationButton.setSelected(enabled);
    }

    // 設定を保存するメソッド
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

    // 設定を読み込むメソッド
    private void loadSettings() {
        // 以前の設定を読み込んでスイッチの状態を更新
        boolean javascriptEnabled = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
                .getBoolean(MainActivity.KEY_JAVASCRIPT_ENABLED, true);
        boolean cookieEnabled = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
                .getBoolean(MainActivity.KEY_COOKIE_ENABLED, true);

        // 通知ボタンの状態を設定
        boolean notificationEnabled = isNotificationPermissionGranted();
        setNotificationButtonState(notificationEnabled);

        javascriptSwitch.setChecked(javascriptEnabled);
        cookieSwitch.setChecked(cookieEnabled);
        cookieSwitch.setEnabled(javascriptEnabled);
    }

    // 通知許可が付与されているかを確認するメソッド
    private boolean isNotificationPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android 8.0以上の場合はチャネルの作成を確認
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                return notificationManager.getNotificationChannel("channel_id") != null;
            }
        } else {
            // Android 8.0未満の場合は通知権限のチェック
            return NotificationManagerCompat.from(this).areNotificationsEnabled();
        }
        return false;
    }

    // 通知許可ダイアログを表示するメソッド
    private void showNotificationPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("MHSC設定")
                .setMessage("緊急災害通知を受け取るためには、通知の許可が必要です。設定画面を開きますか？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openNotificationSettings();
                    }
                })
                .setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 通知ボタンをOFFに戻す（許可しない場合）
                        notificationButton.setSelected(false);
                    }
                })
                .show();
    }

    // 通知設定画面を開くメソッド
    private void openNotificationSettings() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        } else {
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.fromParts("package", getPackageName(), null));
        }
        startActivityForResult(intent, REQUEST_NOTIFICATION_PERMISSION);
    }
}
