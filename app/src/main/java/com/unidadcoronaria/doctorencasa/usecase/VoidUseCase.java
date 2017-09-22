package com.unidadcoronaria.doctorencasa.usecase;


import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import org.reactivestreams.Subscription;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public abstract class VoidUseCase {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private Disposable subscription = Disposables.empty();


    protected VoidUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract Completable buildUseCaseObservable();

    /**
     * Executes the current use case.
     *
     */
    @SuppressWarnings("unchecked")
    public void execute(Action completableAction, Consumer<? super Throwable> errorConsumer) {
        // We need to unsuscribe any
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
        this.subscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler()).subscribe(completableAction, errorConsumer);
    }

    @SuppressWarnings("unchecked")
    public void execute(Consumer<? super Throwable> errorConsumer) {
        // We need to unsuscribe any
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
        this.subscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler()).subscribe(() -> {}, errorConsumer);
    }

    /**
     * Unsubscribes from current {@link Subscription}.
     */
    public void unsubscribe() {
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
    }

}
