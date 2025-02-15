package com.test.stfalcontest.view.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.withStarted
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.test.stfalcontest.R
import com.test.stfalcontest.view.base.util.BackState
import com.test.stfalcontest.view.base.util.Failure
import com.test.stfalcontest.view.base.util.NavigationState
import com.test.stfalcontest.view.base.util.NextScreenState
import com.test.stfalcontest.view.base.util.RequestPermissionsState
import com.test.stfalcontest.view.base.util.toBundle
import com.test.stfalcontest.view.implementation.ProgressDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {
    abstract val viewModel: VM

    protected lateinit var layout: VB
        private set

    private var baseSubscriptionJobs: Job? = null

    abstract fun inflateViewBinding(savedInstanceState: Bundle?): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = inflateViewBinding(savedInstanceState)
        setContentView(layout.root)
        baseSubscriptionJobs = lifecycleScope.launch {
            viewModel.navigationState
                .onEach { withStarted { onNavigationChanged(it) } }
                .launchIn(this)
            viewModel.errorState
                .onEach { withStarted { handleErrorMessage(it) } }
                .launchIn(this)
            viewModel.shouldShowProgress
                .onEach { withStarted { shouldShowProgress(it) } }
                .launchIn(this)
        }
    }

    override fun onDestroy() {
        baseSubscriptionJobs?.cancel()
        baseSubscriptionJobs = null
        shouldShowProgress(false)
        super.onDestroy()
    }

    protected fun <T> Flow<T>.asObserverJob(
        rootJob: Job? = null,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        block: suspend (T) -> Unit
    ): Job {
        val block: suspend (CoroutineScope.() -> Unit) = {
            repeatOnLifecycle(state) {
                collect(block)
            }
        }
        return when (rootJob) {
            null -> lifecycleScope.launch(block = block)
            else -> lifecycleScope.launch(rootJob, block = block)
        }
    }

    protected open fun onNavigationChanged(navigation: NavigationState) = when (navigation) {
        is BackState -> handleBackNavigation(navigation)
        is NextScreenState -> handleCustomNavigation(navigation)
        is RequestPermissionsState -> handlePermissionRequestState(navigation)
    }

    open fun handleBackNavigation(state: BackState) = with(state) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            setResult(code, Intent().putExtras(toBundle()))
            finish()
        }
    }

    open fun handleCustomNavigation(state: NextScreenState) = Unit

    open fun handlePermissionRequestState(requestPermissionsState: RequestPermissionsState) = Unit

    open fun handleError(error: Throwable) {
        val message = when (error) {
            else -> getString(
                R.string.placeholder_unknown_error,
                error.message ?: error.stackTraceToString()
            )
        }
        showErrorDialog(message)
    }

    open fun showErrorDialog(message: String) {
        MaterialDialog(this).show {
            title(R.string.title_error)
            message(text = message)
            positiveButton { it.dismiss() }
        }
    }

    private fun handleErrorMessage(failure: Failure) {
        when {
            failure.error != null -> handleError(failure.error)
            failure.message != null -> showErrorDialog(failure.message)
        }
    }

    fun shouldShowProgress(isVisible: Boolean) {
        when (isVisible) {
            true -> try {
                val lastProgressDialog = supportFragmentManager
                    .fragments
                    .filterIsInstance<ProgressDialogFragment>()
                    .firstOrNull()
                if (lastProgressDialog == null) {
                    val progressDialog = ProgressDialogFragment()
                    progressDialog.show(
                        supportFragmentManager,
                        ProgressDialogFragment.TAG
                    )
                }
            } catch (ex: Exception) {
                Timber.e(ex)
            }

            else -> supportFragmentManager
                .fragments
                .filterIsInstance<ProgressDialogFragment>()
                .firstOrNull()
                ?.dismiss()
        }
    }
}