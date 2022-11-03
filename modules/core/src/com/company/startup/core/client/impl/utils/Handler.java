package com.company.startup.core.client.impl.utils;

@FunctionalInterface
public interface Handler<T> {

  void handle(T t);
}
