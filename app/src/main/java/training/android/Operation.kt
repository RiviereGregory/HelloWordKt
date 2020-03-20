package training.android

import android.media.VolumeShaper

sealed class Operation {
    class Add(val value: Int): Operation()
    class Subtract(val value: Int): Operation()
    object Increment : Operation()
    object Decrement : Operation()
}

fun execute(x: Int, operation: Operation) = when(operation){
    is Operation.Add -> x + operation.value
    is Operation.Subtract -> x - operation.value
    Operation.Increment -> x + 1
    Operation.Decrement -> x - 1
}