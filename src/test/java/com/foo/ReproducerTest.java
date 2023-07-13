package com.foo;

import com.antwerkz.bottlerocket.BottleRocket;
import com.antwerkz.bottlerocket.BottleRocketTest;
import com.github.zafarkhaja.semver.Version;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;

public class ReproducerTest extends BottleRocketTest {
    private Datastore datastore;

    public ReproducerTest() {
        MongoClient mongo = getMongoClient();
        MongoDatabase database = getDatabase();
        database.drop();
        datastore = Morphia.createDatastore(mongo, getDatabase().getName());
    }

    @NotNull
    @Override
    public String databaseName() {
        return "morphia_repro";
    }

    @Nullable
    @Override
    public Version version() {
        return BottleRocket.DEFAULT_VERSION;
    }

    @Test
    public void brokenSaveWithList() {
        var entity = new MapWithListBugged();
        entity.map = new MapWithListBugged.MyHashMap<>();
        entity.map.put("key", List.of(1, 2, 3));
        datastore.insert(entity);
        var saved = datastore.find(MapWithListBugged.class).first();
        assert saved != null;
        assert Objects.equals(entity.map.get("key"), saved.map.get("key"));
    }

    @Test
    public void brokenReadWithAnything() {
        var entity = new MapWithAnythingBugged();
        entity.map = new MapWithAnythingBugged.MyHashMap<>();
        entity.map.put("key", 1);
        datastore.insert(entity);
        var saved = datastore.find(MapWithAnythingBugged.class).first();
        assert saved != null;
        assert Objects.equals(entity.map.get("key"), saved.map.get("key"));
    }

}
