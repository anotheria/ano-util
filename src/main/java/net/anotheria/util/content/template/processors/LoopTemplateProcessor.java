package net.anotheria.util.content.template.processors;

import net.anotheria.util.content.template.TemplateProcessor;
import net.anotheria.util.content.template.TemplateReplacementContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
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
        if (!(data instanceof List))
            return "";

        List<?> list = (List<?>) data;
        if (list.isEmpty())
            return "";

        StringBuilder result = new StringBuilder();
        for (Object item : list) {
            String processed = aDefValue;
            Pattern pattern = Pattern.compile(aVariable + "\\.([a-zA-Z0-9_]+)");
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
                Field field = item.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(item);
                return value != null ? value.toString() : "";
            } catch (Exception ignore) {
                return "";
            }
        }
    }
}
