package pl.grzybiarze.gatherer.dialogs

import android.animation.ArgbEvaluator
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.ColorUtils
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pl.grzybiarze.gatherer.R

class ForgotPasswordDialog(context: Context) : AlertDialog(context) {

    val TAG = "ForgotPasswordDialog"

    private lateinit var auth: FirebaseAuth
    private lateinit var userInfo: TextView
    private lateinit var resetPassword: Button
    private lateinit var exit: Button
    private lateinit var email: EditText
    private lateinit var emailInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_forgot_password)
        setBackgroundColor()

        email = findViewById(R.id.email)
        emailInputLayout = findViewById(R.id.text_input_layout)
        resetPassword = findViewById(R.id.reset_password)
        exit = findViewById(R.id.exit)

        userInfo = findViewById(R.id.user_info)

        auth = Firebase.auth

        resetPassword.setOnClickListener {
            val emailText = email.text.toString()
            if (validateData(emailText)) {
                sendPassword(emailText)
            }
            else {
                Toast.makeText(context, context.getText(R.string.empty_email), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setBackgroundColor() {
        val dialogLayout = findViewById<LinearLayout>(R.id.dialog_layout)
        dialogLayout.setBackgroundColor(
            ColorUtils.blendARGB(
                context.getColor(R.color.md_theme_dark_inversePrimary),
                context.getColor(R.color.md_theme_light_surface),
                0.89F)
        )
    }

    private fun validateData(email: String) : Boolean{
        return email.isNotEmpty()
    }

    private fun sendPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "sendPassword() successfully sent password reset")
                    changeLayout()
                }
                else {
                    Log.w(TAG, "sendPassword() something went wrong")
                    Toast.makeText(context, context.getText(R.string.wrong_email), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun changeLayout() {
        userInfo.setText(R.string.mail_with_reseted_password)
        resetPassword.visibility = View.GONE
        email.visibility = View.GONE
        emailInputLayout.visibility = View.GONE
        exit.visibility = View.VISIBLE
        exit.setOnClickListener {
            this.cancel()
        }
    }

}