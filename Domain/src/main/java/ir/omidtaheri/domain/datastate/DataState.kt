package ir.omidtaheri.domain.datastate

sealed class DataState<T> {

    class SUCCESS<T>(data:T? , stateMessage:StateMessage?=null):DataState<T>()

    class ERROR<T>(stateMessage:StateMessage?=null): DataState<T>()
}