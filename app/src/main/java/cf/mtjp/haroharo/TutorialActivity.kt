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
                .page(TitlePage(R.drawable.image2, "静岡スマート\n情報ポータルへ\nようこそ"))
                .page(BasicPage(
                        R.drawable.image5,
                        "アプリについての\n簡単な紹介を致します。",
                        "「SKIP」ボタンを押すことで\nこのチュートリアルを終了できます。\nPeopleillustrationsbyStoryset")
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
                        .background(BackgroundColor(Color.GRAY)))
                .page(BasicPage(
                        R.drawable.image6,
                        "もちろん\n静岡県新型コロナ対策アプリ\nに搭載されていた全機能も\n引き続き搭載しています。",
                        "ログイン不要で\nお使いいただけます。")
                        .background(BackgroundColor(Color.GRAY)))
                .page(BasicPage(
                        R.drawable.image7,
                        "年中無休のチャットサポート",
                        "アプリ使用等に関して\nお困りになられた際は\nいつでも、どなたでも\n無料でお使い頂けます。")
                        .background(BackgroundColor(Color.GRAY)))
                .page(BasicPage(
                        R.drawable.image8,
                        "ユーザー間の\nコミュニティ機能",
                        "会員制コミュニティーで\n朝市等の開催情報を共有できます。\n他にも廃棄してしまう農作物やまだ使える家具など捨てる前に欲しい人がいるか確認してみましょう。")
                        .background(BackgroundColor(Color.GRAY)))
                .page(BasicPage(
                        R.drawable.image9,
                        "さぁ、はじめよう！",
                        "まだ見ぬ静岡の顔を発見しよう！\n静岡を100倍楽しむためのアプリ\nシズスマ！\n静岡スマート情報ポータル")
                        .background(BackgroundColor(Color.GRAY))
                )
                .swipeToDismiss(true)
                .build()
    }


    fun showIfNeeded(mainActivity: MainActivity, _savedInstanceState: Bundle) {

    }
}
