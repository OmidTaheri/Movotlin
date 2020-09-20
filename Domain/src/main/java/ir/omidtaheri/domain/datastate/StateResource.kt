package ir.omidtaheri.domain.datastate

data class StateMessage(
    val message: MessageHolder,
    val uiComponentType: UiComponentType,
    val messageType: MessageType
)

sealed class MessageType {
    object SUCCESS : MessageType()
    object ERROR : MessageType()
    object INFO : MessageType()
    object NONE : MessageType()
}

sealed class UiComponentType {
    object TOAST : UiComponentType()
    object SNACKBAR : UiComponentType()
    object DIALOG : UiComponentType()
    object NONE : UiComponentType()
    class CallBack(val callback: CustomCallback) : UiComponentType()
}

interface CustomCallback {
    fun process()
    fun cancel()
}

sealed class MessageHolder {
    class MESSAGE(val message: String) : MessageHolder()
    class Res(val resId: Int) : MessageHolder()
    object NONE : MessageHolder()
}
