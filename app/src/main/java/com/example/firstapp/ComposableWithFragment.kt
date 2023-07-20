package com.example.firstapp

import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

@Composable
fun ComposableWithFragment(
    modifier: Modifier = Modifier,
    tag: String = "ReusableFragmentTag"
) {
    // Use the AndroidView composable to embed the Fragment
    AndroidView(
        modifier = modifier,
        factory = { context ->
            FrameLayout(context).apply {
                id = ViewCompat.generateViewId()
            }
        },
        update = {
//            val fragmentAlreadyAdded = fragmentManager.findFragmentByTag(tag) != null
//
//            if (!fragmentAlreadyAdded) {
//                fragmentManager.commit {
//                    add(it.id, MyFragment(), tag)
//                }
//            }
        }
    )
}