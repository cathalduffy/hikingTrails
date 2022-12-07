package org.wit.hikingtrails.views.signIn

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.views.hikeList.HikeListView
import timber.log.Timber.i
import org.wit.hikingtrails.models.HikeFireStore


class SignInPresenter (val view: SignInView)  {
    private lateinit var loginIntentLauncher : ActivityResultLauncher<Intent>
    var app: MainApp = view.application as MainApp
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: HikeFireStore? = null

    init{
        registerLoginCallback()
        if (app.hikes is HikeFireStore) {
            fireStore = app.hikes as HikeFireStore
        }
    }


    fun doLogin(email: String, pass: String) {
//        view.showProgress()
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(view) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchHikes {
                        val launcherIntent = Intent(view, HikeListView::class.java)
                        loginIntentLauncher.launch(launcherIntent)
                        i("if:failure ${task.exception?.message}")
                    }
                } else {
//                    view?.hideProgress()
                    i("else1:failure ${task.exception?.message}")
                    val launcherIntent = Intent(view, HikeListView::class.java)
                    loginIntentLauncher.launch(launcherIntent)
                }
                } else {
                    i("signInWithCredential:failure ${task.exception?.message}")
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