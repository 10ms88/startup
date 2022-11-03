package com.company.startup.core.client.impl;

import com.company.startup.core.client.impl.utils.JsonWrapper;

@FunctionalInterface
public interface RestApiJsonParser<T> {

  T parseJson(JsonWrapper json);
}
