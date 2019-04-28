package com.github.mfamador.callcost.extension

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long) = this.map(selector).sum()
