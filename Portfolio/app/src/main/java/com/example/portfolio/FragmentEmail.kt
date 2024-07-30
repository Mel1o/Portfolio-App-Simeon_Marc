package com.example.portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView

class FragmentEmail : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scrollView = view.findViewById<ScrollView>(R.id.scroll)
        val button = view.findViewById<Button>(R.id.scroll_button)

        button.setOnClickListener {
            // Convert 1000dp to pixels
            val scale = resources.displayMetrics.density
            val y = (1000 * scale + 0.5f).toInt()

            scrollView.post {
                scrollView.scrollTo(0, 100)
            }
        }
    }

}