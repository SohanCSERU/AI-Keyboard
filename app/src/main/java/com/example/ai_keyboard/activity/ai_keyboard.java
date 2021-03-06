package com.example.ai_keyboard.activity;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.provider.UserDictionary;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ai_keyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener{

    //    private KeyboardView kv;
    private KeyboardView banglaKeyboardView;
    private Keyboard keyboard;
    private boolean isCaps = false;
    UserDictionary userDictionary;

     CandidatesView mCandidateView;

    @Override
    public View onCreateInputView() {
        banglaKeyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard,null);
        keyboard = new Keyboard(this,R.xml.qwerty);
        banglaKeyboardView.setKeyboard(keyboard);
        banglaKeyboardView.setOnKeyboardActionListener(this);
        return banglaKeyboardView;
    }

    @Override
    public View onCreateCandidatesView() {
        mCandidateView = new CandidatesView(this);
        mCandidateView.setService(this);
        setCandidatesViewShown(true);
        return mCandidateView;
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {
        InputConnection ic =getCurrentInputConnection();
        playClick(i);
        switch (i)
        {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                isCaps = !isCaps;
                keyboard.setShifted(isCaps);
                banglaKeyboardView.invalidateAllKeys();
                break;
            case -123:
                keyboard = new Keyboard(this,R.xml.bangla_wordlist_1);
                banglaKeyboardView.setKeyboard(keyboard);
                banglaKeyboardView.setOnKeyboardActionListener(this);
                break;
            case -456:
                keyboard = new Keyboard(this,R.xml.english_number);
                banglaKeyboardView.setKeyboard(keyboard);
                banglaKeyboardView.setOnKeyboardActionListener(this);
                break;
            case -789:
                keyboard = new Keyboard(this,R.xml.avro_layout);
                banglaKeyboardView.setKeyboard(keyboard);
                banglaKeyboardView.setOnKeyboardActionListener(this);
                break;
            case -147:
                keyboard = new Keyboard(this,R.xml.bangla_world_list_2);
                banglaKeyboardView.setKeyboard(keyboard);
                banglaKeyboardView.setOnKeyboardActionListener(this);
                break;
            case -258:
                keyboard = new Keyboard(this,R.xml.bangla_number);
                banglaKeyboardView.setKeyboard(keyboard);
                banglaKeyboardView.setOnKeyboardActionListener(this);
                break;
            case -789456:
                 keyboard = new Keyboard(this,R.xml.extra_character);
                 banglaKeyboardView.setKeyboard(keyboard);
                 banglaKeyboardView.setOnKeyboardActionListener(this);
                 break;
            case -8520:
                 keyboard = new Keyboard(this,R.xml.bangla_extra_char);
                 banglaKeyboardView.setKeyboard(keyboard);
                 banglaKeyboardView.setOnKeyboardActionListener(this);
                 break;
            case -123456789:
                keyboard = new Keyboard(this,R.xml.qwerty);
                banglaKeyboardView.setKeyboard(keyboard);
                banglaKeyboardView.setOnKeyboardActionListener(this);
                break;

            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char)i;
                if(Character.isLetter(code) && isCaps){
                    code = Character.toUpperCase(code);
                    isCaps = !isCaps;
                    keyboard.setShifted(isCaps);
                    banglaKeyboardView.invalidateAllKeys();
                }
                ic.commitText(String.valueOf(code),i);
        }
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);

        InputConnection ic =getCurrentInputConnection();
    }

    private void playClick(int i) {
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (i){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);

        }
    }




    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public void pickSuggestionManually(int mSelectedIndex) {
    }
}