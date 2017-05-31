package etwge.yu.linespaceextracompat;

import android.content.Context;
import android.util.AttributeSet;;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 在5.0以下，最后一行自动添加一个行间距的大小
 * 这个容器就是通过算出最后一行多出的行间距的高，然后用子view测量的总高度减去多余的行间距高作为该容器的高，子类多出部分不会显示出来
 */
public class LineSpaceExtraContainer extends ViewGroup {

	public LineSpaceExtraContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (getChildCount() < 1) {
			throw new IllegalStateException("must has one child view");
		}

		View view = getChildAt(0);
		if (!(view instanceof IGetLineSpaceExtra)) {
			throw new IllegalStateException("child view mast is child of DividerLineTextView");
		}

		view.measure(widthMeasureSpec, heightMeasureSpec);
		//总高度减去多余的行间距高作为该容器的高
		setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight() - ((IGetLineSpaceExtra) view).getSpaceExtra());
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (getChildCount() < 1) {
			throw new IllegalStateException("must has one child view");
		}

		//填充整个个容器，忽略padding属性
		getChildAt(0).layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
	}

}
