package de.hsfl.team46.campusflag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import de.hsfl.team46.campusflag.viewmodels.ViewModel

class MainActivity : AppCompatActivity() {

    val mainViewModel : ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}