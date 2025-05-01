package io.seekankan.github.kansky.util;

import de.tr7zw.changeme.nbtapi.handler.NBTHandlers;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import de.tr7zw.changeme.nbtapi.wrapper.NBTProxy;
import de.tr7zw.changeme.nbtapi.wrapper.NBTTarget;

public interface ItemNBTProxy extends NBTProxy {
    @Override
    default void init() {
        registerHandler(ReadableNBT.class, NBTHandlers.STORE_READABLE_TAG);
        registerHandler(ReadWriteNBT.class, NBTHandlers.STORE_READWRITE_TAG);
    }

    @NBTTarget(type = NBTTarget.Type.HAS, value = "Owner")
    boolean hasOwner();
    @NBTTarget(type = NBTTarget.Type.GET, value = "Owner")
    String getOwner();
    @NBTTarget(type = NBTTarget.Type.SET, value = "Owner")
    void setOwner(String owner);
    @NBTTarget(type = NBTTarget.Type.HAS, value = "Thrower")
    boolean hasThrower();
    @NBTTarget(type = NBTTarget.Type.GET, value = "Thrower")
    String getThrower();
    @NBTTarget(type = NBTTarget.Type.SET, value = "Thrower")
    void setThrower(String thrower);
}
