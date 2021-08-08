package cf.mtjp.haroharo

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.stephentuso.welcome.*

class TutorialActivity : WelcomeActivity() {

    companion object {
        /**
         * まだ表示していなかったらチュートリアルを表示
         * SharedPreferencesの管理に関しては内部でよしなにやってくれているので普通に呼ぶだけで良い
         */
        fun showIfNeeded(activity: Activity, savedInstanceState: Bundle?) {
            WelcomeHelper(activity, TutorialActivity::class.java).show(savedInstanceState)
        }

        /**
         * 強制的にチュートリアルを表示したい時にはこちらを呼ぶ
         */
        fun showForcibly(activity: Activity) {
            WelcomeHelper(activity, TutorialActivity::class.java).forceShow()
        }
    }

    /**
     * 表示するチュートリアル画面を定義する
     */
    override fun configuration(): WelcomeConfiguration {
        return WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(BackgroundColor(Color.GRAY))
                .page(TitlePage(R.drawable.image2, "静岡スマート情報ポータルへ\nようこそ"))
                .page(BasicPage(
                        R.drawable.image5,
                        "アプリについての簡単な紹介を致します。",
                        "「SKIP」ボタンを押すことでこのチュートリアルを終了できます。")
                        .background(BackgroundColor(Color.GRAY)))
                .page(BasicPage(
                        R.drawable.image3,
                        "静岡県内のお店で使える\nお得なクーポンを配信中",
                        "掲載希望店舗も募集中です。")
                        .background(BackgroundColor(Color.GRAY)))
                .page(BasicPage(
                        R.drawable.image4,
                        "静岡県民おすすめの\n観光スポットも紹介中",
                        "  ")
                        .background(BackgroundColor(Color.GRAY))
                )
                .swipeToDismiss(true)
                .build()
    }


    fun showIfNeeded(mainActivity: MainActivity, _savedInstanceState: Bundle) {

    }
}
