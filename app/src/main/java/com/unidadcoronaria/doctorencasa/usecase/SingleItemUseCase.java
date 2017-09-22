package com.unidadcoronaria.doctorencasa.usecase;


import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import org.reactivestreams.Subscription;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public abstract class SingleItemUseCase {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private Disposable subscription = Disposables.empty();


    protected SingleItemUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract Single buildUseCaseObservable();

    /**
     * Executes the current use case.
     *
     * @param itemConsumer The guy who will be listen to the observable build with {@link #buildUseCaseObservable()}.
     */
    @SuppressWarnings("unchecked")
    public void execute(Consumer itemConsumer, Consumer<? super Throwable>  errorConsumer) {
        // We need to unsuscribe any
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
        this.subscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler()).subscribe(itemConsumer, errorConsumer);
    }

    @SuppressWarnings("unchecked")
    public void execute(Consumer itemConsumer) {
        // We need to unsuscribe any
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
        this.subscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler()).subscribe(itemConsumer);
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
