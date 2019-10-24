package io.jenkins.plugins.simplenotification;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Utils {

    public final static int socketTimeout = 5000;
    public final static int connectTimeout = 5000;
    public final static int requestTimeout = 5000;
    
    public static Boolean isValidRegex(String value) {
        try {
            Pattern.compile(value);
        } catch (PatternSyntaxException e) {
            return false;
        }
        return true;
    }

    public static Boolean isRegexMatch(String regex, String value) {
        return Pattern.matches(regex, value);
    }

    public static Boolean isValidGlob(String value) {
        return isValidRegex(createRegexFromGlob(value));
    }

    public static Boolean isGlobMatch(String glob, String value) {
        return Pattern.matches(createRegexFromGlob(glob), value);
    }

    private static String createRegexFromGlob(String glob) {
        StringBuilder out = new StringBuilder("^");
        for (int i = 0; i < glob.length(); i++) {
            final char c = glob.charAt(i);
            switch (c) {
                case '*':
                    out.append(".*");
                    break;
                case '?':
                    out.append('.');
                    break;
                case '.':
                    out.append("\\.");
                    break;
                case '\\':
                    out.append("\\\\");
                    break;
                default:
                    out.append(c);
            }
        }
        out.append('$');
        return out.toString();
    }
}