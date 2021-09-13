package org.frv;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Roman V. Fedorin
 */
public class Utils {

    public static String getRoleName(String find) {
        if ("admin".equals(find)) {
            return "ROLE_ADMIN";
        }
        return "ROLE_USER";
    }
}
