package kim.hsl.kb

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife

class MainActivity : Activity() {

    @BindView(R.id.tv_1)
    lateinit var tv_1 : TextView

    @JvmField
    @BindView(R.id.tv_2)
    var tv_2 : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        Log.i("TAG", tv_1?.text.toString())
        Log.i("TAG", tv_2?.text.toString())
    }
}