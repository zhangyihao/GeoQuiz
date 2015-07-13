package com.zhangyihao.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	public static final String EXTRA_ANSWER_IS_TRUE = "com.zhangyihao.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.zhangyihao.geoquiz.answer_shown";
	
	private TextView mAnswerTextView;
	private Button mShowAnswerButton;
	
	private boolean mAnswerIsTrue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
		mShowAnswerButton = (Button)findViewById(R.id.showAnswerButton);
		
		mShowAnswerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
				setAnswerShowResult(true);
			}
		});
		
	}
	
	private void setAnswerShowResult(boolean isAnswerShown) {
		Intent intent = new Intent();
		intent.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, intent);
		
	}
}
