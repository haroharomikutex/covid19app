package cf.mtjp.haroharo;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.constraintlayout.widget.Group;
import es.dmoral.toasty.Toasty;


public class SettingsActivity extends AppCompatActivity {
    private Switch javascriptSwitch;
    private Switch cookieSwitch;
    private Button notificationButton;
    private Switch switchStealthMode;
    private Button notificationtest;
    private Group settingsGroup;

    private static final int REQUEST_NOTIFICATION_PERMISSION = 123;
    public static final String CHANNEL_ID = "113"; // 通知チャネルID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settinglayout1);
        // 全ての機能ボタンがクリックされたときの処理を設定
        Button showAllFeaturesButton = findViewById(R.id.showAllFeaturesButton);
        showAllFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Homemenu1 アクティビティに遷移
                Intent intent = new Intent(SettingsActivity.this, Homemenu1.class);
                startActivity(intent);
            }
        });
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // アクティビティを終了して前の画面に戻る
                saveSettings();
                finish();
            }
        });
        Button btnBack2 = findViewById(R.id.btnBack2);

        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // アクティビティを終了して前の画面に戻る
                Toasty.success(getApplicationContext(), "設定を保存せず終了しました", Toast.LENGTH_SHORT, false).show();
                finish();
            }
        });


        // レイアウトからスイッチとボタンを取得
        javascriptSwitch = findViewById(R.id.switchJavaScript);
        cookieSwitch = findViewById(R.id.switchCookie);
        switchStealthMode = findViewById(R.id.switchStealthMode);
        notificationButton = findViewById(R.id.switchNotification); // 通知のボタンに変更
        settingsGroup = findViewById(R.id.settingsGroup);
        notificationtest = findViewById(R.id.testNotificationReceiveButton);

        // スイッチが変更されたときの処理を設定
        javascriptSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cookieSwitch.setEnabled(isChecked);
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


        // 通知関連のチャネルを作成
        createNotificationChannel();
        // 設定の読み込み
        loadSettings();

        // 通知ボタンがクリックされたときの処理を設定
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationPermissionDialog();

                // switchStealthModeの状態をSharedPreferencesに保存
                saveStealthModeSetting(switchStealthMode.isChecked());
            }

        });


        notificationtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通知のパーミッションが許可されているかチェック
                if (!isNotificationPermissionGranted()) {
                    // パーミッションが許可されていない場合、リクエストダイアログを表示
                    showNotificationPermissionDialog();
                } else {
                    // パーミッションが許可されている場合の処理
                    sendTestNotification();
                }
            }
        });
    }

    // 通知ボタンの状態を設定
    private void setNotificationButtonState(boolean enabled) {
        notificationtest.setSelected(enabled);
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
        Toasty.success(getApplicationContext(), "設定を保存しました\nアプリを再度起動するとデフォルトに戻ります", Toast.LENGTH_SHORT, false).show();
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

    // トーストを表示するメソッド
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // 通知許可が付与されているかを確認するメソッド
    private boolean isNotificationPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android 8.0以上の場合はチャネルの作成を確認
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                return notificationManager.getNotificationChannel(CHANNEL_ID) != null;
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

    // 通知を送信するメソッド
    private void sendTestNotification() {
        // 許可が付与されているかどうかを確認
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            // 許可が付与されている場合、通知を送信できます
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentTitle("緊急速報通知")
                    .setContentText("緊急速報通知設定が完了しました。\nMHSCSystemは現在正常に稼働しています。\n震度５弱以上の地震・大津波警報・テロ情報等を受信した際には国内最速レベルでお知らせします。")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            // サウンドファイルを設定
            Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.soudai4sec);
            builder.setSound(soundUri);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(9999, builder.build());
        } else {
            // 許可が付与されていない場合、ユーザーにリクエストを行います
            showNotificationPermissionDialog();
        }
    }

    // 許可リクエストの結果を処理
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 許可が付与された場合、通知の送信を続行できます
                sendTestNotification();
            } else {
                showPermissionDeniedDialog();
            }
        }
    }

    // 許可が拒否された場合に表示するダイアログ
    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("許可が拒否されました")
                .setMessage("通知を送信するためには許可が必要です。設定画面から許可を設定してください。")
                .setPositiveButton("設定を開く", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openNotificationSettings(); // 設定画面を開く処理を呼び出す
                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ダイアログがキャンセルされた場合の処理を追加
                    }
                })
                .show();
    }
    private void saveStealthModeSetting(boolean isChecked) {
        // SharedPreferencesに設定を保存
        getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putBoolean(MainActivity.KEY_SWITCH_STEALTH_MODE, isChecked)
                .apply();
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

    // 通知チャネルを作成するメソッド
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "緊急速報通知・MHSCS",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("震度５弱以上の地震・大津波警報・テロ情報等を受信した際には国内最速レベルでお知らせします");
            // サウンドファイルを設定
            Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.soudai4sec);
            channel.setSound(soundUri, null); // サウンドを設定
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
    @Override
    public void onBackPressed() {
        Toasty.success(getApplicationContext(), "設定を保存せず終了しました", Toast.LENGTH_SHORT, false).show();
        super.onBackPressed();
    }
}