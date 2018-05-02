package etwge.yu.linespaceextracompat;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;

public class LineSpaceExtraCompatTextView extends AppCompatTextView implements IGetLineSpaceExtra {

	private static final String TAG = LineSpaceExtraCompatTextView.class.getSimpleName();
	private Rect mLastLineShowRect;
	private Rect mLastLineActualIndexRect;

	public LineSpaceExtraCompatTextView(Context context) {
		this(context, null);
	}

	public LineSpaceExtraCompatTextView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LineSpaceExtraCompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mLastLineShowRect = new Rect();
		mLastLineActualIndexRect = new Rect();
	}

	@Override
	public int getSpaceExtra() {
		return calculateExtraSpace();
	}

	/**
	 *
	 * @return 算出最后一行多出的行间距的高
	 */
	public int calculateExtraSpace() {
		int result = 0;
		//界面显示的最后一行的index
		int lastLineShowIndex = Math.min(getMaxLines(), getLineCount()) - 1;
		//实际上的最后一行的index，当没设置maxLines时，跟lastLineShowIndex的值相等
		int lastLineActualIndex = getLineCount() - 1;

		if (lastLineShowIndex >= 0) {
			Layout layout = getLayout();
			int baseline = getLineBounds(lastLineShowIndex, mLastLineShowRect);
			getLineBounds(lastLineActualIndex, mLastLineActualIndexRect);
			//只有测量的高度跟，getLayout的高度相等时这种情况时最后一行才多出的行间距
			//因为有当设置maxLines时，通过实际最后一行的底部坐标减去显示最后一样的底部坐标得出看不见那部分的高度
			//然后判断测量的高度，跟文字的总高度减去看不见的那部分高度，相等才去算最后一行多出的行间距的高，不相等说明TextView没有底部空白间隙
			if (getMeasuredHeight() == getLayout().getHeight() - (mLastLineActualIndexRect.bottom - mLastLineShowRect.bottom)) {
				result = mLastLineShowRect.bottom - (baseline + layout.getPaint().getFontMetricsInt().descent);
			}

		}
		Log.i(TAG, "extra space:" + result);
		return result;
	}
}
