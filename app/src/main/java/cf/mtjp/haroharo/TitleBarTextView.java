package cf.mtjp.haroharo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TitleBarTextView extends AppCompatTextView {
    private CharSequence[] titles;
    private int currentIndex = 0;
    private ConstraintLayout.LayoutParams layoutParams;

    public TitleBarTextView(Context context) {
        super(context);
        init();
    }

    public TitleBarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBarTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        titles = new CharSequence[]{"シズスマ・システムメニュー", "通知設定もこちらから"};
        setText(titles[currentIndex]);

        // 背景の緑のバーの設定
        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 8);
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        setLayoutParams(layoutParams);
        setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark)); // 緑の色

        // タイトル切り替えを開始
        postDelayed(this::switchTitle, 1100); // 1.1秒ごとに切り替え
    }

    public void switchTitle() {
        currentIndex = (currentIndex + 1) % titles.length;
        setText(titles[currentIndex]);

        // タイトル切り替えを再度スケジュール
        postDelayed(this::switchTitle, 1100); // 1.1秒ごとに切り替え
    }
}
