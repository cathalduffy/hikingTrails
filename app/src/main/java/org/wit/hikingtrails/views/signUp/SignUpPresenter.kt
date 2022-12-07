package org.wit.hikingtrails.views.signUp

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import org.wit.hikingtrails.activities.SignInActivity
import org.wit.hikingtrails.views.hikeList.HikeListView
import timber.log.Timber.i

class SignUpPresenter(val view: SignUpView)  {
    private lateinit var loginIntentLauncher : ActivityResultLauncher<Intent>

    init{
        registerSignUpCallback()
    }

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun doSignup(email: String, pass: String) {
//        view.showProgress()
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                val launcherIntent = Intent(view, HikeListView::class.java)
                loginIntentLauncher.launch(launcherIntent)
            } else {

            }
//            view.hideProgress()
        }
    }

    private fun registerSignUpCallback(){
        loginIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}