package lt.dejavu.auth.security.configuration.properties;

import lt.dejavu.auth.model.Endpoint;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.regex.Pattern;

@Component
@ConfigurationPropertiesBinding
public class EndpointConverter implements Converter<String, Endpoint> {
    @Override
    public Endpoint convert(String source) {
        String[] parts = source.split(Pattern.quote(" "));
        Endpoint e = new Endpoint();
        e.setMethod(RequestMethod.valueOf(parts[0]));
        e.setPath(globToRegex(parts[1]));
        return e;
    }

    private String globToRegex(String glob) {
        StringBuilder sb = new StringBuilder("^");
        for (char c : glob.toCharArray()) {
            switch (c) {
                case '*':
                    sb.append(".*");
                    break;
                case '?':
                    sb.append('.');
                    break;
                case '.':
                    sb.append("\\.");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        sb.append("$");
        return sb.toString();
    }
}
