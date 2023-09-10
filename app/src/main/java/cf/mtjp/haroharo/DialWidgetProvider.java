package cf.mtjp.haroharo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class DialWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            // ウィジットのレイアウトを設定
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.dial_widget_layout);

            // ダイヤルボタンがクリックされたときのアクションを設定
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:110"));
            views.setOnClickPendingIntent(R.id.dialButton, PendingIntent.getActivity(context, 0, dialIntent, 0));

            // ウィジットを更新
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
