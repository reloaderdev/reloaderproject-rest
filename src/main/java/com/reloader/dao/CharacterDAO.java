package com.reloader.dao;

import org.json.JSONArray;
import org.json.JSONObject;

public interface CharacterDAO {
    JSONObject getCharacterScreen(long userId) throws Exception;
    JSONObject saveLapisConfig(long characterId, JSONArray config) throws Exception;
}
