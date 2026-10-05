package net.anotheria.util.content.template.processors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.anotheria.util.content.template.TemplateProcessor;
import net.anotheria.util.content.template.TemplateReplacementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * LoopProcessor — processing list parameter.
 *
 * <p>This is the only part of ano-util that needs jackson, which is therefore declared as an optional
 * dependency. Consumers using {@code {loop:...}} tags have to put jackson-databind on their classpath.</p>
 *
 * @author ykalapusha
 * @since 24.09.2025
 */
public class LoopTemplateProcessor implements TemplateProcessor {
    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(LoopTemplateProcessor.class);
    /**
     * Utility instance for mapping different collection instances to map.
     */
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        // Property inclusion stays at the default (ALWAYS) on purpose: NON_NULL drops null valued
        // properties from the converted map, which leaks their placeholders into the rendered output.
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public String replace(String aPrefix, String aVariable, String aDefValue, TemplateReplacementContext aContext) {
        Object data = aContext.getAttribute(aVariable);
        if (!(data instanceof Collection))
            return "";

        Collection<?> collection = (Collection<?>) data;
        if (collection.isEmpty())
            return "";

        List<String> properties = findReferencedProperties(aVariable, aDefValue);

        StringBuilder result = new StringBuilder();
        for (Object element : collection) {
            Map<String, String> values = mapElement(element);
            String processed = aDefValue;
            for (String property : properties)
                processed = processed.replace(aVariable + "." + property, values.getOrDefault(property, ""));

            result.append(processed).append("\n");
        }
        return result.toString().trim();
    }

    /**
     * Collects the distinct property names the loop body references, longest name first. Replacing in that
     * order guarantees that a short name can never consume the prefix of a longer one (name vs. nameId),
     * independently of the order in which the element properties happen to be enumerated.
     *
     * @param aVariable name of the loop variable.
     * @param aDefValue body of the loop tag.
     * @return referenced property names, longest first.
     */
    private List<String> findReferencedProperties(String aVariable, String aDefValue) {
        Matcher matcher = Pattern.compile(Pattern.quote(aVariable + ".") + "([\\w$]+)").matcher(aDefValue);
        Set<String> properties = new HashSet<>();
        while (matcher.find())
            properties.add(matcher.group(1));

        return properties.stream()
                .sorted(Comparator.comparingInt(String::length).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Flattens a single loop element into its property values. Elements that cannot be flattened degrade to
     * an empty map, so that one unsupported element renders empty values instead of aborting the whole template.
     *
     * @param obj element to flatten.
     * @return property name to value, never null.
     */
    private Map<String, String> mapElement(Object obj) {
        if (obj == null)
            return Map.of();

        Map<?, ?> rawMap;
        try {
            rawMap = MAPPER.convertValue(obj, Map.class);
        } catch (IllegalArgumentException e) {
            log.warn("Loop element of type {} has no properties to flatten, rendering empty values.",
                    obj.getClass().getName(), e);
            return Map.of();
        }

        Map<String, String> values = new LinkedHashMap<>();
        for (Map.Entry<?, ?> entry : rawMap.entrySet())
            values.put(String.valueOf(entry.getKey()), entry.getValue() == null ? "" : entry.getValue().toString());

        return values;
    }
}
