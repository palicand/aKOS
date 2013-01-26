package cz.cvut.fit.palicand.akos.views;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cz.cvut.fit.palicand.akos.R;

public class PairedTextView extends RelativeLayout {
	TextView label;
	TextView content;
	
	public PairedTextView(Context context) {
		super(context);
		Log.d("custom view", "we got here somehow");
	}
	public PairedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("PairedTextView", "we're in the right constructor");
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_paired_text, this, true);
		label = (TextView)findViewById(R.id.view_paired_text_label);
		content = (TextView)findViewById(R.id.view_paired_text_content);
		
		assignAttributes(context, attrs);
	}

	private void assignAttributes(Context context, AttributeSet attrs) {
		TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.PairedTextView);

        try {
            content.setText(arr.getText(R.styleable.PairedTextView_contentText));
            label.setText(arr.getText(R.styleable.PairedTextView_labelText));
            resolvePosition(arr.getInteger(R.styleable.PairedTextView_contentPosition, 2));
        } finally {
            arr.recycle();
        }
	}

    private void resolvePosition(int pos) {
        LayoutParams params = (LayoutParams)content.getLayoutParams();
        params.removeRule(BELOW);
        params.removeRule(ABOVE);
        params.removeRule(LEFT_OF);
        params.removeRule(RIGHT_OF);
        switch (pos) {
            case 0:
                params.addRule(BELOW, R.id.view_paired_text_label);
                break;
            case 1:
                params.addRule(ABOVE, R.id.view_paired_text_label);
                break;
            case 2:
                params.addRule(RIGHT_OF, R.id.view_paired_text_label);
                break;
            case 3:
                params.addRule(LEFT_OF, R.id.view_paired_text_label);
                break;
        }
    }

    public CharSequence getLabelText() {
		return label.getText();
	}
	
	public CharSequence getContentText() {
		return content.getText();
	}
	
	public void setLabelText(CharSequence text) {
		label.setText(text);
	}
	
	public void setContentText(CharSequence text) {
		content.setText(text);
	}
}
