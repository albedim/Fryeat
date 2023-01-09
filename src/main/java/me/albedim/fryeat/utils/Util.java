package me.albedim.fryeat.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;

public class Util
{
    // App consts

    public static final String URL = "/api/v_1_0_0";

    public static final String NOT_ENOUGH_PERMISSIONS = "You don't have enough permissions to do this";
    public static final String INVALID_REQUEST = "Invalid request";

    // User consts

    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_SUCCEFULLY_CREATED = "User was successfully created";
    public static final String USER_ALREADY_EXISTS = "A user with this username, already exists";

    // Poll consts

    public static final String POLL_SUCCESSFULLY_CREATED = "Poll successfully created";


    public static HashMap createResponse(Boolean success, String param)
    {
        HashMap response = new HashMap<>();
        response.put("date", String.valueOf(LocalDateTime.now()));
        response.put("success", success);
        response.put("param", param);
        response.put("code", 200);
        return response;
    }

    public static HashMap createResponse(Boolean success, String error, Integer code)
    {
        HashMap response = new HashMap<>();
        response.put("date", String.valueOf(LocalDateTime.now()));
        response.put("success", success);
        response.put("error", error);
        response.put("code", code);
        return response;
    }

    public static HashMap createLoginResponse(Long id)
    {
        HashMap response = new HashMap();
        response.put("success", true);
        response.put("id", id);
        return response;
    }
}
