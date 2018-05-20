package lt.dejavu.utils.common;

import org.springframework.util.StringUtils;

public class FormattingUtils {
    public static String toUrlFriendlyString(String input) {
        return StringUtils.isEmpty(input)
                ? ""
                : input.toLowerCase()
                       .replaceAll("[^a-z0-9_]+", "-")
                       .replaceAll("^-|-$", "");
    }
}
