package cf.mtjp.haroharo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import cf.mtjp.haroharo.R.drawable;
import com.stephentuso.welcome.BackgroundColor;
import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;
import com.stephentuso.welcome.WelcomeHelper;
public final class TutorialActivity extends WelcomeActivity {

    /**
     * 表示するチュートリアル画面を定義する
     */
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(new BackgroundColor(Color.GRAY))
                .page(new TitlePage(drawable.image2, "静岡スマート\n情報ポータルへ\nようこそ"))
                .page((new BasicPage(drawable.image5, "アプリについての\n簡単な紹介を致します。", "「SKIP」ボタンを押すことで\nこのチュートリアルを終了できます。"))
                        .background(new BackgroundColor(Color.GRAY)))
                .page((new BasicPage(drawable.image3, "静岡県内のお店で使える\nお得なクーポンを配信中", "掲載希望店舗も募集中です。"))
                        .background(new BackgroundColor(Color.GRAY)))
                .page((new BasicPage(drawable.image4, "静岡県民おすすめの\n観光スポットも紹介中", "  "))
                        .background(new BackgroundColor(Color.GRAY)))
                .page((new BasicPage(drawable.image6, "もちろん\n静岡県新型コロナ対策アプリ\nに搭載されていた全機能も\n引き続き搭載しています。", "ログイン不要で\nお使いいただけます。"))
                        .background(new BackgroundColor(Color.GRAY)))
                .page((new BasicPage(drawable.image7, "年中無休のチャットサポート", "アプリ使用等に関して\nお困りになられた際は\nいつでも、どなたでも\n無料でお使い頂けます。"))
                        .background(new BackgroundColor(Color.GRAY)))
                .page((new BasicPage(drawable.image8, "ユーザー間の\nコミュニティ機能", "会員制コミュニティーで\n朝市等の開催情報を共有できます。\n他にも廃棄してしまう農作物やまだ使える家具など捨てる前に欲しい人がいるか確認してみましょう。"))
                        .background(new BackgroundColor(Color.GRAY)))
                .page((new BasicPage(drawable.image9, "さぁ、はじめよう！", "まだ見ぬ静岡の顔を発見しよう！\n静岡を100倍楽しむためのアプリ\nシズスマ！\n静岡スマート情報ポータル"))
                        .background(new BackgroundColor(Color.GRAY)))
                .swipeToDismiss(true)
                .build();
    }

    public static final class Companion {
        /**
         * まだ表示していなかったらチュートリアルを表示
         * SharedPreferencesの管理に関しては内部でよしなにやってくれているので普通に呼ぶだけで良い
         */
        public void showIfNeeded(Activity activity, Bundle savedInstanceState) {
            (new WelcomeHelper(activity, TutorialActivity.class)).show(savedInstanceState);
        }

        /**
         * 強制的にチュートリアルを表示したい時にはこちらを呼ぶ
         */
        public void showForcibly(Activity activity) {
            (new WelcomeHelper(activity, TutorialActivity.class)).forceShow();
        }
    }
}
