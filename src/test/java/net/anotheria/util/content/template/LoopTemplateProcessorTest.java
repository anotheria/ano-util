package net.anotheria.util.content.template;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static net.anotheria.util.content.template.TemplateUtility.replaceVariables;

/**
 * LoopTemplateProcessorTest — tests for loop processor.
 *
 * @author ykalapusha
 * @since 24.09.2025
 */
public class LoopTemplateProcessorTest {

    @Test
    public void testLoopAsList(){
        List<ItemData> list = new ArrayList<>();
        list.add(new ItemData(101, "Name101", "Message101"));
        list.add(new ItemData(102, "Name102", "Message102"));
        list.add(new ItemData(103, "Name103", "Message103"));

        String replacementPart = "{loop:itemsData:Id->itemsData.id, Name->itemsData.name, Message->itemsData.message}";
        String text = "Hello World!" +  replacementPart;

        TemplateReplacementContext context = new TemplateReplacementContext();
        context.addAttribute("itemsData", list);

        String replacedText = replaceVariables(context, text);
        Assert.assertNotNull("Should not be null", replacedText);
        Assert.assertTrue(replacedText.contains("Id->101, Name->Name101, Message->Message101"));

    }

    @Test
    public void testLoopAsSet(){
        Set<ItemData> set = new HashSet<>();
        set.add(new ItemData(101, "Name101", "Message101"));
        set.add(new ItemData(102, "Name102", "Message102"));
        set.add(new ItemData(103, "Name103", "Message103"));

        String replacementPart = "{loop:itemsData:Id->itemsData.id, Name->itemsData.name, Message->itemsData.message}";
        String text = "Hello World!" +  replacementPart;

        TemplateReplacementContext context = new TemplateReplacementContext();
        context.addAttribute("itemsData", set);

        String replacedText = replaceVariables(context, text);
        Assert.assertNotNull("Should not be null", replacedText);
        Assert.assertTrue(replacedText.contains("Id->101, Name->Name101, Message->Message101"));
    }

    @Test
    public void testLoopEmptyCollection(){
        Collection<ItemData> collection = new ArrayList<>();

        String replacementPart = "{loop:itemsData:Id->itemsData.id, Name->itemsData.name, Message->itemsData.message}";
        String text = "Hello World!";

        TemplateReplacementContext context = new TemplateReplacementContext();
        context.addAttribute("itemsData", collection);

        String replacedText = replaceVariables(context, text + replacementPart);
        Assert.assertNotNull("Should not be null", replacedText);
        Assert.assertEquals(text, replacedText);
    }

    @Test
    public void testLoopNullCollection(){

        String replacementPart = "{loop:itemsData:Id->itemsData.id, Name->itemsData.name, Message->itemsData.message}";
        String text = "Hello World!";

        String replacedText = replaceVariables(new TemplateReplacementContext(), text + replacementPart);
        Assert.assertNotNull("Should not be null", replacedText);
        Assert.assertEquals(text, replacedText);
    }

    public static class ItemData{
        private int id;
        private String name;
        private String message;

        public ItemData(int id, String name, String message) {
            this.id = id;
            this.name = name;
            this.message = message;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            ItemData itemData = (ItemData) o;
            return id == itemData.id;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }
}
