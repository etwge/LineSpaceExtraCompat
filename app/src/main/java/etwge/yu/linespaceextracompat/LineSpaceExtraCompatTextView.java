package etwge.yu.linespaceextracompat;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;

public class LineSpaceExtraCompatTextView extends AppCompatTextView implements IGetLineSpaceExtra{

	private static final String TAG = LineSpaceExtraCompatTextView.class.getSimpleName();
	private Rect mRect;

	public LineSpaceExtraCompatTextView(Context context) {
		this(context, null);
	}

	public LineSpaceExtraCompatTextView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LineSpaceExtraCompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mRect = new Rect();
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
		int lastLineIndex = getLineCount() - 1;

		if (lastLineIndex >= 0) {
			Layout layout = getLayout();
			int baseline = getLineBounds(lastLineIndex, mRect);
			if (getMeasuredHeight() == getLayout().getHeight()) {
				result = mRect.bottom - (baseline + layout.getPaint().getFontMetricsInt().descent);
			}

		}
		Log.i(TAG, "extra space:" + result);
		return result;
	}
}
