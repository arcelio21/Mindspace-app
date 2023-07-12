package com.mindspace.app.usecases.base;

import com.mindspace.app.model.user.DiarioGet;

import java.util.List;

public interface ListenerResponseFirabase {

    void notifyChange(Object response);

    void getData(List<?> data);
}
