package org.wit.hikingtrails.views.signIn

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import org.wit.hikingtrails.views.hikeList.HikeListView
import timber.log.Timber
import timber.log.Timber.i

class SignInPresenter (val view: SignInView)  {
    private lateinit var loginIntentLauncher : ActivityResultLauncher<Intent>

    init{
        registerLoginCallback()
    }
    var auth: FirebaseAuth = FirebaseAuth.getInstance()


    fun doLogin(email: String, pass: String) {
//        view.showProgress()
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(view) { task ->
            if (task.isSuccessful) {
                val launcherIntent = Intent(view, HikeListView::class.java)
                loginIntentLauncher.launch(launcherIntent)
            } else {
                Timber.i( "signInWithCredential:failure ${task.exception?.message}")
                i("Login failed:")
//                val launcherIntent = Intent(view, HikeListView::class.java)
//                loginIntentLauncher.launch(launcherIntent)
            }
//            view.hideProgress()
        }
    }

    private fun registerLoginCallback(){
        loginIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }

}