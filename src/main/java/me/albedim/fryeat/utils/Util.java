package me.albedim.fryeat.utils;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class Util
{
    // App consts
    public static final String URL = "/api/v_1_7_0";
    public static final String NOT_ENOUGH_PERMISSIONS = "You don't have enough permissions to do this";
    public static final String INVALID_REQUEST = "Invalid request";

    // User consts
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_SUCCESSFULLY_CHANGED = "User successfully changed";
    public static final String USER_PASSWORD_SUCCESSFULLY_CHANGED = "Password successfully changed";
    public static final String SIGNUP_MAIL_SUBJECT = "Welcome to Fryeat!";
    public static final String SIGNUP_MAIL_TEXT =
            """
                <html>
                    <body>
                        <h2>Hi {name}, Welcome to our platform</h2>
                    </body>
                </html>
            """;
    public static final String USER_SUCCESSFULLY_CREATED = "User was successfully created";
    public static final String USER_ALREADY_EXISTS = "A user with this username, already exists";

    // PollFood consts
    public static final String POLLFOOD_ALREADY_EXISTS = "This food is already in this poll";
    public static final String POLLFOOD_SUCCESSFULLY_DELETED = "This food was successfully deleted";
    public static final String POLLFOOD_SUCCESSFULLY_CREATED = "Food successfully created and added to this poll";

    // Participations consts
    public static final String PARTICIPATION_SUCCESSFULLY_DELETED = "Participation successfully deleted";
    public static final String PARTICIPATION_SUCCESSFULLY_ADDED = "Participation successfully added to your poll";
    public static final String PARTICIPATION_ALREADY_EXISTS = "This participation already exists";

    // Vote consts
    public static final String VOTE_SUCCESSFULLY_SET = "You have successfully set the vote";
    public static final String VOTE_SUCCESSFULLY_ADDED = "Your vote has been added";

    // Poll consts
    public static final String POLL_SUCCESSFULLY_CLOSED = "Poll successfully closed";
    public static final String POLL_SUCCESSFULLY_CREATED = "Poll successfully created";
    public static final String POLL_SUCCESSFULLY_DELETED = "Poll successfully deleted";

    // Food consts
    public static final String FOOD_SUCCESSFULLY_CREATED = "Food successfully created";

    // Mail consts
    public static final String NOREPLY_EMAIL = "noreply.fryeat@gmail.com";
    public static final String NOREPLY_NAME = "Fryeat";
    public static final String NOREPLY_PASSWORD = "qobcjeukowzdmwfb";
    public static final String EMAIL = "noreply.fryeat@gmail.com";
    public static final String MAIL_SUBJECT = "New Invite for you!";
    public static final String MAIL_TEXT =
        """
            <html>
              <head>
                <title>Hello, World!</title>
                <link rel="stylesheet" href="styles.css" />
              </head>
              <body>
                <div class="a">
                  <div>
                    <h3>Hey {name}, You have just got invited for a poll!</h3>
                    <h3>What are you waiting for winning this</h3>
                    <h3>and make your friends eta what you want!</h3>
                  </div>
                  <a href="https://localhost/poll/{pollId}"><button>Vote Now</button></a>
                </div>
              </body>
            </html>
        """;

    // Recovery link consts
    public static final String RECOVERY_LINK_SUCCESSFULLY_CREATED = "A link was successfully created";
    public static final String RECOVERY_LINK_SUCCESSFULLY_DELETED = "A link was successfully deleted";
    public static final String RECOVERY_LINK_MAIL_SUBJECT = "Change your password";
    public static final String RECOVERY_LINK_MAIL_TEXT =
        """
            <html>
                <body>
                    <h2>Hi {name}, This is the link to change your password</h2>
                    <a href="http://localhost:3000/changepassword/change?l={link}"><button>Change</button></a>
                </body>
            </html>
        """;



    public static HashMap createResponse(Boolean success, String param)
    {
        HashMap response = new HashMap<>();
        response.put("date", String.valueOf(LocalDateTime.now()));
        response.put("success", success);
        response.put("param", param);
        response.put("code", 200);
        return response;
    }

    public static HashMap createResponse(Boolean success, boolean param)
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

    public static String hash(String password)
    {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String hashedPassword = "";
        String encryptedChars = "C0yZEIipDF23djS5muGMfnV6HtcW4q9BJLXlPakrghNeK1AsU8xRwQbzYO7Tov";
        for(int i = 0; i < password.length(); i++)
            for(int j = 0; j < chars.length(); j++)
                if(String.valueOf(password.charAt(i)).equals(String.valueOf(chars.charAt(j)))) {
                    hashedPassword += String.valueOf(encryptedChars.charAt(j));
                    break;
                }
        return hashedPassword;
    }

    public static MimeMessage getMessage() throws MessagingException
    {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, NOREPLY_PASSWORD);
            }
        });
        MimeMessage msg = new MimeMessage(session);
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");
        return msg;
    }

    public static String unHash(String password)
    {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String unHashedPassword = "";
        String encryptedChars = "C0yZEIipDF23djS5muGMfnV6HtcW4q9BJLXlPakrghNeK1AsU8xRwQbzYO7Tov";
        for(int i = 0; i < password.length(); i++)
            for(int j = 0; j < encryptedChars.length(); j++)
                if(String.valueOf(password.charAt(i)).equals(String.valueOf(encryptedChars.charAt(j)))) {
                    unHashedPassword += String.valueOf(chars.charAt(j));
                    break;
                }
        return unHashedPassword;
    }
}
