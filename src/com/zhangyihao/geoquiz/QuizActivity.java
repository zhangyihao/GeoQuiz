package com.zhangyihao.geoquiz;

import android.app.Activity;
import android.content.Intent;
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
	private Button mCheatButton;
	
	private int mCurrentIndex = 0;
	private static final String KEY_INDEX = "index";
	private boolean mIsCheater;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[]{
			new TrueFalse(R.string.quiz_activity_question_oceans, true),
			new TrueFalse(R.string.quiz_activity_question_mideast, false),
			new TrueFalse(R.string.quiz_activity_question_africa, false),
			new TrueFalse(R.string.quiz_activity_question_americas, true),
			new TrueFalse(R.string.quiz_activity_question_asia, true)
	};
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		if(savedInstanceState!=null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
		}
		initView();
	}
	
	private void initView() {
		mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
		mTrueButton = (Button)findViewById(R.id.true_button);
		mFalseButton = (Button)findViewById(R.id.false_button);
		mNextButton = (ImageButton)findViewById(R.id.next_button);
		mPrevButton = (ImageButton)findViewById(R.id.prev_button);
		mCheatButton = (Button)findViewById(R.id.cheat_button);
		
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
		
		mCheatButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivityForResult(intent, 0);
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
		mIsCheater = false;
		updateQuestion();
	}
	
	private void checkAnswer(boolean usePressdTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int messageId = 0;
		if(mIsCheater) {
			messageId = R.string.judgment_toast;
		} else {
			if(answerIsTrue==usePressdTrue) {
				messageId = R.string.correct_toast;
			} else {
				messageId = R.string.incorrect_toast;
			}
		}
		Toast.makeText(QuizActivity.this, messageId, Toast.LENGTH_SHORT).show();;
	}
	
	private void updateQuestion() {
		mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getQuestion());
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data==null) {
			return;
		}
		mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
	}
	
	
}
