package com.reloader.services;

import com.reloader.sqlserverdao.CharacterSqlServerDAO;
import org.json.JSONArray;
import org.json.JSONObject;

public class CharacterService {

    private final CharacterSqlServerDAO dao = new CharacterSqlServerDAO();

    public JSONObject getCharacterScreen(long userId) throws Exception {
        return dao.getCharacterScreen(userId);
    }

    public JSONObject saveLapisConfig(long characterId, JSONArray config) throws Exception {
        return dao.saveLapisConfig(characterId, config);
    }
}
