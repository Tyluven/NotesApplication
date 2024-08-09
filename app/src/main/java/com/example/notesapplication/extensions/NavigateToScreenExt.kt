package com.example.notesapplication.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.notesapplication.R

fun Fragment.navigateToScreen(
    @IdRes destinationId: Int,
    bundle: Bundle? = null,
    isSingleTop: Boolean = true,
    navOptions: NavOptions? = null
) {
    findNavController().navigateToScreen(
        destinationId = destinationId,
        bundle = bundle,
        isSingleTop = isSingleTop,
        navOptions = navOptions
    )
}

fun NavController.navigateToScreen(
    @IdRes destinationId: Int,
    bundle: Bundle? = null,
    isSingleTop: Boolean = true,
    navOptions: NavOptions? = null
) {
    navigate(
        resId = destinationId,
        args = bundle,
        navOptions = navOptions ?: NavOptions.Builder().apply {
            setLaunchSingleTop(isSingleTop)
            setEnterAnim(R.anim.slide_in_right)
            setExitAnim(R.anim.slide_out_left)
            setPopEnterAnim(R.anim.pop_slide_out_right)
            setPopExitAnim(R.anim.pop_slide_in_left)
        }.build()
    )
}

fun Fragment.navigateToScreen(
    @IdRes navHost: Int? = null,
    @IdRes destinationId: Int,
    bundle: Bundle? = null,
    isSingleTop: Boolean = true,
    navOptions: NavOptions? = null
) {
    navHost?.let {
        activity?.findNavController(navHost)?.navigateToScreen(
            destinationId = destinationId,
            bundle = bundle,
            isSingleTop = isSingleTop,
            navOptions = navOptions
        )
    } ?: navigateToScreen(
        destinationId, bundle, isSingleTop, navOptions
    )

}