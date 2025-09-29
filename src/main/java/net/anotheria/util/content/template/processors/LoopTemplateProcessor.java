package net.anotheria.util.content.template.processors;

import net.anotheria.util.content.template.TemplateProcessor;
import net.anotheria.util.content.template.TemplateReplacementContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LoopProcessor — processing list parameter.
 *
 * @author ykalapusha
 * @since 24.09.2025
 */
public class LoopTemplateProcessor implements TemplateProcessor {
    @Override
    public String replace(String aPrefix, String aVariable, String aDefValue, TemplateReplacementContext aContext) {
        Object data = aContext.getAttribute(aVariable);
        if (!(data instanceof Collection))
            return "";

        Collection<?> collection = (Collection<?>) data;
        if (collection.isEmpty())
            return "";

        StringBuilder result = new StringBuilder();
        List<Map<String, String>> listData = buildData(collection);
        for (Map<String, String> element: listData){
            String processed = aDefValue;
            for (String key: element.keySet()) {
                processed = processed.replaceAll(aVariable + "." + key, element.get(key));
            }
            result.append(processed).append("\n");
        }
        return result.toString().trim();
    }

    private List<Map<String, String>> buildData(Collection<?> collection) {
        List<Map<String, String>> ret = new ArrayList<>();
        for (Object item : collection) {
            Map<String, String> map = new HashMap<>();
            for (Field field : item.getClass().getDeclaredFields()) {
                try {
                    String getterName = "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                    Method method = item.getClass().getMethod(getterName);
                    Object value = method.invoke(item);
                    map.put(field.getName(), value != null ? value.toString() : null);
                } catch (Exception e) {
                    try {
                        Object value = field.get(item);
                        map.put(field.getName(), value != null ? value.toString() : null);
                    } catch (Exception ignore) {}
                }
            }
            ret.add(map);
        }
        return ret;
    }
}