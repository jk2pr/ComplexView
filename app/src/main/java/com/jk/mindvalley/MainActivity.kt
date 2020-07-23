package com.jk.mindvalley

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jk.mindvalley.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
    }
}