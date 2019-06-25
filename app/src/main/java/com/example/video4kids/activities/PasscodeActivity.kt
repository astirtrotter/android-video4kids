package com.example.video4kids.activities

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.Toast
import com.example.video4kids.R
import com.example.video4kids.activities.interfaces.IMultiLangScreen
import com.example.video4kids.common.Pref
import com.example.video4kids.common.extensions.getMultiLangString
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.showSoftKeyboard
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayed
import kotlinx.android.synthetic.main.activity_passcode_screen.*
import kotlinx.android.synthetic.main.inside_common_toolbar.*

class PasscodeActivity : AppCompatActivity(), IMultiLangScreen {

    companion object {
        private var step = -1
        private var isCreate = false
        private var newPasscode = ""

        fun initStep(isChange: Boolean = false) {
            step = -1
            isCreate = isChange || Pref.passcode == null
            newPasscode = ""
        }
    }

    private var isNeedToInputOldPasscode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passcode_screen)
        icLeft.hide()
        icRight.hide()

        isNeedToInputOldPasscode = isCreate && Pref.passcode != null
        gotoNextStep()
        configureEvents()
    }

    override fun configureMultiLangViews() {
        val onInputOldPasscode = {
            setpassid.text = getMultiLangString(R.string.next, R.string.perisan_next)
            tv1.text = ""
            tv2.text = getMultiLangString(R.string.old_pwd, R.string.perisan_old_pwd)
        }
        val onInputNewPasscode = {
            setpassid.text = getMultiLangString(R.string.change_passcode, R.string.perisan_change_passcode)
            tv1.text = getMultiLangString(R.string.grown_ups_only, R.string.perisan_grown_ups_only)
            tv2.text = getMultiLangString(R.string.to_access_please_enter_the_numbers_seven_five_seven_five, R.string.perisan_to_access_please_enter_the_numbers_seven_five_seven_five)
        }
        val onInputConfirmPasscode = {
            setpassid.text = getMultiLangString(R.string.con_change_passcode, R.string.perisan_change_passcode)
            tv1.text = getMultiLangString(R.string.grown_ups_only, R.string.perisan_grown_ups_only)
            tv2.text = getMultiLangString(R.string.to_access_please_enter_the_numbers_seven_five_seven_five, R.string.perisan_to_access_please_enter_the_numbers_seven_five_seven_five)
        }
        val onInputPasscode = {
            setpassid.text = getMultiLangString(R.string.next, R.string.perisan_next)
            tv1.text = ""
            tv2.text = getMultiLangString(R.string.to_access_please_enter_the_numbers_seven_five_seven_five, R.string.perisan_to_access_please_enter_the_numbers_seven_five_seven_five)
        }
        if (isCreate) {
            if (isNeedToInputOldPasscode) {
                if (step == 0) {
                    onInputOldPasscode()
                } else if (step == 1) {
                    onInputNewPasscode()
                } else if (step == 2) {
                    onInputConfirmPasscode()
                }
            } else {
                if (step == 0) {
                    onInputNewPasscode()
                } else if (step == 1) {
                    onInputConfirmPasscode()
                }
            }
        } else {
            if (step == 0) {
                onInputPasscode()
            }
        }
    }

    private fun configureEvents() {
        setpassid.onClick { gotoNextStep() }
        firsttextid.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString().isNotEmpty()) {
                    s.delete(1, s.length)
                    if (!TextUtils.isDigitsOnly(s.toString())) {
                        s.delete(0, 1)
                        return
                    }
                    secondtextid.requestFocus()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        firsttextid.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                firsttextid.setText("")
            }
            false
        }
        secondtextid.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString().isNotEmpty()) {
                    s.delete(1, s.length)
                    if (!TextUtils.isDigitsOnly(s.toString())) {
                        s.delete(0, 1)
                        return
                    }
                    thirdtextid.requestFocus()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        secondtextid.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                if (secondtextid.text.toString().isEmpty()) {
                    firsttextid.setText("")
                    firsttextid.requestFocus()
                } else {
                    secondtextid.setText("")
                }
            }
            false
        }
        thirdtextid.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString().isNotEmpty()) {
                    s.delete(1, s.length)
                    if (!TextUtils.isDigitsOnly(s.toString())) {
                        s.delete(0, 1)
                        return
                    }
                    forthtextid.requestFocus()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        thirdtextid.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                if (thirdtextid.text.toString().isEmpty()) {
                    secondtextid.setText("")
                    secondtextid.requestFocus()
                } else {
                    thirdtextid.setText("")
                }
            }
            false
        }
        forthtextid.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString().isNotEmpty()) {
                    s.delete(1, s.length)
                    if (!TextUtils.isDigitsOnly(s.toString())) {
                        s.delete(0, 1)
                        return
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        forthtextid.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                if (forthtextid.text.toString().isEmpty()) {
                    thirdtextid.setText("")
                    thirdtextid.requestFocus()
                } else {
                    forthtextid.setText("")
                }
            }
            false
        }
    }

    private fun gotoNextStep() {
        val inputPasscode = firsttextid.text.toString() +
                secondtextid.text.toString() +
                thirdtextid.text.toString() +
                forthtextid.text.toString()
        if (step != -1 && inputPasscode.length != 4) {
            Toast.makeText(this@PasscodeActivity, "Invalid Input!", Toast.LENGTH_LONG).show()
            return
        }

        step++

        val checkWithPrefPasscode = {
            if (inputPasscode != Pref.passcode) {
                Toast.makeText(this@PasscodeActivity, "Invalid Passcode", Toast.LENGTH_LONG).show()
                false
            } else {
                true
            }
        }
        val storeIntoNewPasscode = {
            newPasscode = inputPasscode
        }
        val checkWithNewPasscodeAndStoreIntoPrefPasscode = {
            if (inputPasscode != newPasscode) {
                Toast.makeText(this@PasscodeActivity, "Invalid Passcode", Toast.LENGTH_LONG).show()
                false
            } else {
                Pref.passcode = newPasscode
                true
            }
        }
        if (isCreate) {
            if (isNeedToInputOldPasscode) {
                if (step == 1) {
                    if (!checkWithPrefPasscode()) {
                        step--
                    }
                } else if (step == 2) {
                    storeIntoNewPasscode()
                } else if (step == 3) {
                    if (checkWithNewPasscodeAndStoreIntoPrefPasscode()) {
                        setResult(Activity.RESULT_OK)
                        finish()
                        return
                    } else {
                        step--
                    }
                }
            } else {
                if (step == 1) {
                    storeIntoNewPasscode()
                } else if (step == 2) {
                    if (checkWithNewPasscodeAndStoreIntoPrefPasscode()) {
                        setResult(Activity.RESULT_OK)
                        finish()
                        return
                    } else {
                        step--
                    }
                }
            }
        } else {
            if (step == 1) {
                if (checkWithPrefPasscode()) {
                    setResult(Activity.RESULT_OK)
                    finish()
                    return
                } else {
                    step--
                }
            }
        }

        configureMultiLangViews()
        firsttextid.setText("")
        secondtextid.setText("")
        thirdtextid.setText("")
        forthtextid.setText("")
        firsttextid.showSoftKeyboard()
    }

}
