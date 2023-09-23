package cf.mtjp.haroharo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class noticesoundset extends AppCompatActivity {

    private SharedPreferences preferences;
    private String selectedRingtoneUri; // 選択された通知音のURI

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settinglayout1);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        selectedRingtoneUri = preferences.getString("selected_ringtone_uri", null);

        Button notificationSoundButton = findViewById(R.id.notificationSoundButton);
        notificationSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRingtoneSelectionDialog();
            }
        });
    }

    private void showRingtoneSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.noticesoundsetdialog, null);
        builder.setView(dialogView);

        ListView soundListView = dialogView.findViewById(R.id.soundListView);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
// リングトーンのリストを取得するためのカーソルを取得
        Cursor cursor = RingtoneManager.getCursor(this);

        final List<String> ringtoneTitles = new ArrayList<>();
        final List<String> ringtoneUris = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(RingtoneManager.TITLE));
                Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(RingtoneManager.URI)) + "/" + cursor.getString(cursor.getColumnIndex(RingtoneManager.ID)));

                ringtoneTitles.add(title);
                ringtoneUris.add(uri.toString());
            }
            cursor.close();
        }


        int selectedRingtoneIndex = -1;
        if (selectedRingtoneUri != null) {
            for (int i = 0; i < ringtoneUris.size(); i++) {
                if (selectedRingtoneUri.equals(ringtoneUris.get(i))) {
                    selectedRingtoneIndex = i;
                    break;
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, android.R.id.text1, ringtoneTitles);
        soundListView.setAdapter(adapter);
        soundListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        soundListView.setItemChecked(selectedRingtoneIndex, true);

        final AlertDialog dialog = builder.create();

        soundListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int selectedIndex, long l) {
                selectedRingtoneUri = ringtoneUris.get(selectedIndex);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                preferences.edit().putString("selected_ringtone_uri", selectedRingtoneUri).apply();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
