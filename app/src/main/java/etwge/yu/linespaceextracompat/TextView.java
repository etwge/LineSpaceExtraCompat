package etwge.yu.linespaceextracompat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


public class TextView extends AppCompatTextView {

	private Paint mPaint;
	private Rect  mRect;

	private int baseLineColor;
	private int boundsColor;
	private int textBottomColor;

	public TextView(Context context) {
		this(context, null);
	}

	public TextView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);

		mRect = new Rect();
		baseLineColor = getResources().getColor(R.color.baseline);
		boundsColor = getResources().getColor(R.color.bounds);
		textBottomColor = getResources().getColor(R.color.text_bottom_line);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int count = getLineCount();

		for (int i = 0; i < count; i++) {
			int baseline = getLineBounds(i, mRect);

			mPaint.setColor(baseLineColor);
			canvas.drawLine(mRect.left, baseline, mRect.right, baseline, mPaint);

			mPaint.setColor(textBottomColor);
			canvas.drawLine(mRect.left, baseline + getPaint().getFontMetricsInt().descent, mRect.right, baseline + getPaint().getFontMetricsInt().descent, mPaint);

			mPaint.setColor(boundsColor);
			canvas.drawRect(mRect, mPaint);
		}

	}
}
