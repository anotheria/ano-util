package net.anotheria.util.content.template.processors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.anotheria.util.content.template.TemplateProcessor;
import net.anotheria.util.content.template.TemplateReplacementContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LoopProcessor — processing list parameter.
 *
 * @author ykalapusha
 * @since 24.09.2025
 */
public class LoopTemplateProcessor implements TemplateProcessor {
    /**
     * Utility instance for mapping different collection instances to map.
     */
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
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

        List<Map<String, String>> listData = collection.stream()
                .map(this::mapElement)
                .collect(Collectors.toList());

        StringBuilder result = new StringBuilder();
        for (Map<String, String> element: listData){
            String processed = aDefValue;
            for (String key: element.keySet())
                processed = processed.replace(aVariable + "." + key, element.get(key));

            result.append(processed).append("\n");
        }
        return result.toString().trim();
    }

    private Map<String, String> mapElement(Object obj) {
        Map<String, Object> rawMap = MAPPER.convertValue(obj, Map.class);
        return rawMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue() == null ? "" : e.getValue().toString()
                ));
    }
}