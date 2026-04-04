package com.reloader.sqlserverdao;

import com.reloader.auth.DatabaseConnection;
import com.reloader.dao.LoginDAO;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class LoginSqlServerDAO implements LoginDAO {

    @Override
    public JSONObject login(String username, String password) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            JSONObject response = new JSONObject();
            response.put("isAuthenticated", 0);

            try (CallableStatement stmt = conn.prepareCall("{call auth.sp_LoginUser(?, ?)}")) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next() || rs.getInt("IsAuthenticated") != 1) {
                        return response;
                    }

                    long userId = rs.getLong("UserId");
                    response.put("isAuthenticated", 1);
                    response.put("user", mapUserFromLogin(rs));

                    JSONObject context = getPrimaryCharacterContext(conn, userId);
                    if (context.has("character")) {
                        response.put("character", context.getJSONObject("character"));
                    }
                    if (context.has("guild")) {
                        response.put("guild", context.getJSONObject("guild"));
                    }
                    if (context.has("equipment")) {
                        response.put("equipment", context.getJSONArray("equipment"));
                    }
                    if (context.has("sockets")) {
                        response.put("sockets", context.getJSONArray("sockets"));
                    }
                    if (context.has("baseStats")) {
                        response.put("baseStats", context.getJSONArray("baseStats"));
                    }
                    if (context.has("assignedStats")) {
                        response.put("assignedStats", context.getJSONArray("assignedStats"));
                    }
                    if (context.has("finalStats")) {
                        response.put("finalStats", context.getJSONArray("finalStats"));
                    }
                }
            }

            return response;
        }
    }

    private JSONObject mapUserFromLogin(ResultSet rs) throws Exception {
        JSONObject user = new JSONObject();
        user.put("userId", rs.getLong("UserId"));
        user.put("username", rs.getString("Username"));
        user.put("email", rs.getString("Email"));
        user.put("displayName", rs.getString("DisplayName"));
        user.put("role", rs.getString("RoleCode"));
        user.put("countryCode", rs.getString("CountryCode"));
        user.put("timeZone", rs.getString("TimeZone"));
        user.put("photoUrl", rs.getString("PhotoUrl"));
        return user;
    }

    private JSONObject getPrimaryCharacterContext(Connection conn, long userId) throws Exception {
        JSONObject context = new JSONObject();

        try (CallableStatement stmt = conn.prepareCall("{call core.sp_GetPrimaryCharacterContext(?)}")) {
            stmt.setLong(1, userId);

            boolean hasResults = stmt.execute();
            int resultIndex = 0;

            while (true) {
                if (hasResults) {
                    try (ResultSet rs = stmt.getResultSet()) {
                        if (resultIndex == 0 && rs != null && rs.next()) {
                            JSONObject character = new JSONObject();
                            character.put("characterId", rs.getLong("CharacterId"));
                            character.put("characterName", rs.getString("CharacterName"));
                            character.put("level", rs.getInt("Level"));
                            character.put("title", rs.getString("Title"));
                            character.put("mode", rs.getString("Mode"));
                            character.put("element", rs.getString("Element"));
                            character.put("isPrimary", rs.getBoolean("IsPrimary"));
                            character.put("factionId", rs.getInt("FactionId"));
                            character.put("factionCode", rs.getString("FactionCode"));
                            character.put("factionName", rs.getString("FactionName"));
                            character.put("classId", rs.getInt("ClassId"));
                            character.put("classCode", rs.getString("ClassCode"));
                            character.put("className", rs.getString("ClassName"));
                            context.put("character", character);
                        } else if (resultIndex == 1 && rs != null && rs.next()) {
                            JSONObject guild = new JSONObject();
                            guild.put("guildId", rs.getLong("GuildId"));
                            guild.put("guildName", rs.getString("GuildName"));
                            guild.put("rankId", rs.getInt("GuildRankId"));
                            guild.put("rankCode", rs.getString("RankCode"));
                            guild.put("rankName", rs.getString("RankName"));
                            context.put("guild", guild);
                        } else if (resultIndex == 2) {
                            context.put("equipment", ResultSetJsonMapper.toJsonArray(rs));
                        } else if (resultIndex == 3) {
                            context.put("sockets", ResultSetJsonMapper.toJsonArray(rs));
                        } else if (resultIndex == 4) {
                            context.put("baseStats", ResultSetJsonMapper.toJsonArray(rs));
                        } else if (resultIndex == 5) {
                            context.put("assignedStats", ResultSetJsonMapper.toJsonArray(rs));
                        } else if (resultIndex == 6) {
                            context.put("finalStats", ResultSetJsonMapper.toJsonArray(rs));
                        }
                    }
                    resultIndex++;
                }

                if (stmt.getMoreResults()) {
                    hasResults = true;
                    continue;
                }

                if (stmt.getUpdateCount() == -1) {
                    break;
                }

                hasResults = false;
            }
        }

        return context;
    }
}
