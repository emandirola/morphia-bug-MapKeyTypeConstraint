package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.HashMap;
import java.util.List;

@Entity
public class MapWithListBugged {
    @Id
    public String id;
    public MyHashMap<List<Integer>> map;

    public static class MyHashMap<V> extends HashMap<String, V> {

    }
}
