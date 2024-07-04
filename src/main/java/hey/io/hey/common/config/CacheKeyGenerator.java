package hey.io.hey.common.config;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

public class CacheKeyGenerator {

    public static String generateKey(Object statuses, int size, int page, Object visit) {
        Collection<?> statusesCollection = (statuses instanceof Collection) ?
                (Collection<?>) statuses : null;

        String statusesStr = statusesCollection != null ?
                StringUtils.collectionToCommaDelimitedString(statusesCollection) : "null";

        String visitStr = Objects.toString(visit, "null");

        return statusesStr + "," + size + "," + page + "," + visitStr;
    }
}
