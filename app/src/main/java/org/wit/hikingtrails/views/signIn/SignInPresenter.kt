package org.wit.hikingtrails.views.signIn

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import org.wit.hikingtrails.views.hikeList.HikeListView
import timber.log.Timber.i

//import org.wit.hikingtrails.views.placemarklist.PlacemarkListView



class SignInPresenter (val view: SignInView)  {
    private lateinit var loginIntentLauncher : ActivityResultLauncher<Intent>

    init{
        registerLoginCallback()
    }
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun doLogin(email: String, pass: String) {
//        view.showProgress()
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                val launcherIntent = Intent(view, HikeListView::class.java)
                loginIntentLauncher.launch(launcherIntent)
            } else {
                i("Login failed:")
            }
//            view.hideProgress()
        }

    }

//    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
//        if (it.isSuccessful) {
//            val intent = Intent(this, HikeListView::class.java)
//            startActivity(intent)
//        } else {
//            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
//
//        }
//    }


    private fun registerLoginCallback(){
        loginIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}