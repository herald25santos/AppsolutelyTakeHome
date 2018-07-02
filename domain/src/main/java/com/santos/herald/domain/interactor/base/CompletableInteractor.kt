package com.santos.herald.domain.interactor.base

import com.petrulak.cleankotlin.domain.executor.SchedulerProvider
import io.reactivex.Completable

abstract class CompletableInteractor<Parameters>(
    private val schedulerProvider: SchedulerProvider
) : BaseInteractor<Void>() {

    abstract fun buildUseCase(parameters: Parameters): Completable

    fun execute(onCompleted: () -> Unit, onError: (Throwable?) -> Unit = {}, params: Parameters) {
        val completable = buildUseCase(params).subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
        val disposable = completable.subscribeWith(getDisposableCompletableObserver(onCompleted, onError))
        disposables.add(disposable)
    }
}
