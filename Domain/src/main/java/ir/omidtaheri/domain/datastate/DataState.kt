package ir.omidtaheri.domain.datastate

sealed class DataState<T> {

    class SUCCESS<T>(val data: T?, val stateMessage: StateMessage? = null) : DataState<T>()

    class ERROR<T>(val stateMessage: StateMessage? = null) : DataState<T>()
}
