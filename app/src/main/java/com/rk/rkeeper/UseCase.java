package com.rk.rkeeper;

public abstract class UseCase<Q extends UseCase.RequestValues,P extends UseCase.ResponseValue> {

    private Q mRequestValues;

    private UseCaseCallback<P> mUseCaseCallback;

    public Q getRequestValues() {
        return mRequestValues;
    }

    public void setRequestValues(Q requestValues) {
        this.mRequestValues = requestValues;
    }

    public UseCaseCallback<P> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        this.mUseCaseCallback = useCaseCallback;
    }

    void run(){
        executeUseCase(mRequestValues);
    }

    protected abstract void executeUseCase(Q requestValues);

    public interface ResponseValue{

    }

    public interface RequestValues{

    }

    public interface UseCaseCallback<R>{
        void onSuccess(R response);
        void onError();
    }

}
