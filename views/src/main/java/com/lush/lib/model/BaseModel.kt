package com.lush.lib.model

import android.support.v7.util.DiffUtil

abstract class BaseModel<T>: DiffUtil.ItemCallback<T>()