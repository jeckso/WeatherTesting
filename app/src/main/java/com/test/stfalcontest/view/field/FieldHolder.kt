package com.test.stfalcontest.view.field

import kotlinx.coroutines.flow.Flow

interface FieldHolder<T> {

    val state: Flow<Result<T>>
}