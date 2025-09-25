package net.anotheria.util.content.template.processors;

import net.anotheria.util.content.template.TemplateProcessor;
import net.anotheria.util.content.template.TemplateReplacementContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        Pattern pattern = Pattern.compile(aVariable + "\\.([a-zA-Z0-9_]+)");
        for (Object item : collection) {
            String processed = aDefValue;
            Matcher matcher = pattern.matcher(processed);

            StringBuilder sb = new StringBuilder();
            while (matcher.find()) {
                String fieldName = matcher.group(1);
                String replacement = resolveField(item, fieldName);
                matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
            }
            matcher.appendTail(sb);

            result.append(sb).append("\n");
        }

        return result.toString().trim();
    }

    private String resolveField(Object item, String fieldName) {
        try {
            String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            Method method = item.getClass().getMethod(getterName);
            Object value = method.invoke(item);
            return value != null ? value.toString() : "";
        } catch (Exception e) {
            try {
                Field field = item.getClass().getField(fieldName);
                Object value = field.get(item);
                return value != null ? value.toString() : "";
            } catch (Exception ignore) {
                return "";
            }
        }
    }
}
