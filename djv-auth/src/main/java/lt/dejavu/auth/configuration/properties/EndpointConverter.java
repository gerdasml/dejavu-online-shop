package lt.dejavu.auth.configuration.properties;

import lt.dejavu.auth.model.token.Endpoint;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@ConfigurationPropertiesBinding
public class EndpointConverter implements Converter<String, Endpoint> {
    @Override
    public Endpoint convert(String source) {
        String[] parts = source.split(" ");
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
            }
        }
        sb.append("$");
        return sb.toString();
    }
}
