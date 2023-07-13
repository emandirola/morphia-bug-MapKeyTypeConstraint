package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.HashMap;

@Entity
public class MapWithAnythingBugged {
    @Id
    public String id;
    public MyHashMap<Integer> map;

    public static class MyHashMap<V> extends HashMap<String, V> {

    }
}
