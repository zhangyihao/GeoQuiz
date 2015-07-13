package com.zhangyihao.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private TextView mQuestionTextView;
	private Button mTrueButton;
	private Button mFalseButton;
	private ImageButton mNextButton;
	private ImageButton mPrevButton;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[]{
			new TrueFalse(R.string.quiz_activity_question_oceans, true),
			new TrueFalse(R.string.quiz_activity_question_mideast, false),
			new TrueFalse(R.string.quiz_activity_question_africa, false),
			new TrueFalse(R.string.quiz_activity_question_americas, true),
			new TrueFalse(R.string.quiz_activity_question_asia, true)
	};
	
	private int mCurrentIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		initView();
	}
	
	private void initView() {
		mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
		mTrueButton = (Button)findViewById(R.id.true_button);
		mFalseButton = (Button)findViewById(R.id.false_button);
		mNextButton = (ImageButton)findViewById(R.id.next_button);
		mPrevButton = (ImageButton)findViewById(R.id.prev_button);
		
		updateQuestion();
		mTrueButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
		
		mFalseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
		
		mPrevButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				prevQuestion();
			}
		});
		
		mNextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				nextQuestion();
			}
		});
		
		mQuestionTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				nextQuestion();
			}
		});
	}
	
	private void prevQuestion() {
		if(mCurrentIndex==0) {
			mCurrentIndex = mQuestionBank.length-1;
		} else {
			mCurrentIndex = (mCurrentIndex-1)%mQuestionBank.length;
		}
		updateQuestion();
	}
	
	private void nextQuestion() {
		mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
		updateQuestion();
	}
	
	private void checkAnswer(boolean usePressdTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int messageId = 0;
		if(answerIsTrue==usePressdTrue) {
			messageId = R.string.correct_toast;
		} else {
			messageId = R.string.incorrect_toast;
		}
		Toast.makeText(QuizActivity.this, messageId, Toast.LENGTH_SHORT).show();;
	}
	
	private void updateQuestion() {
		mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getQuestion());
	}
}
