package com.test.stfalcontest.view.field

interface MutableFieldHolder<T : Any> : FieldHolder<T> {

    fun setValue(value: T?)
}