package net.anotheria.util.content.template;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static net.anotheria.util.content.template.TemplateUtility.replaceVariables;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertNotNull(replacedText, "Should not be null");
        assertTrue(replacedText.contains("Id->101, Name->Name101, Message->Message101"));

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
        assertNotNull(replacedText, "Should not be null");
        assertTrue(replacedText.contains("Id->101, Name->Name101, Message->Message101"));
    }

    @Test
    public void testLoopEmptyCollection(){
        Collection<ItemData> collection = new ArrayList<>();

        String replacementPart = "{loop:itemsData:Id->itemsData.id, Name->itemsData.name, Message->itemsData.message}";
        String text = "Hello World!";

        TemplateReplacementContext context = new TemplateReplacementContext();
        context.addAttribute("itemsData", collection);

        String replacedText = replaceVariables(context, text + replacementPart);
        assertNotNull(replacedText, "Should not be null");
        assertEquals(text, replacedText);
    }

    @Test
    public void testLoopNullCollection(){

        String replacementPart = "{loop:itemsData:Id->itemsData.id, Name->itemsData.name, Message->itemsData.message}";
        String text = "Hello World!";

        String replacedText = replaceVariables(new TemplateReplacementContext(), text + replacementPart);
        assertNotNull(replacedText, "Should not be null");
        assertEquals(text, replacedText);
    }

    @Test
    public void testLoopWithPrefixCollidingProperties(){
        List<NamedData> list = new ArrayList<>();
        list.add(new NamedData(1, "SHORT", "LONG"));

        String text = "Hello World!{loop:itemsData:Name->itemsData.name, NameId->itemsData.nameId}";

        TemplateReplacementContext context = new TemplateReplacementContext();
        context.addAttribute("itemsData", list);

        //  itemsData.name must not consume the prefix of itemsData.nameId, whatever order the properties come in.
        assertEquals("Hello World!Name->SHORT, NameId->LONG", replaceVariables(context, text));
    }

    @Test
    public void testLoopWithNullProperty(){
        List<NamedData> list = new ArrayList<>();
        list.add(new NamedData(1, null, "LONG"));

        String text = "{loop:itemsData:[itemsData.id|itemsData.name|itemsData.nameId]}";

        TemplateReplacementContext context = new TemplateReplacementContext();
        context.addAttribute("itemsData", list);

        //  A null property renders empty instead of leaking the literal placeholder text.
        assertEquals("[1||LONG]", replaceVariables(context, text));
    }

    @Test
    public void testLoopWithScalarElements(){
        String text = "{loop:itemsData:Value->itemsData.value}";

        TemplateReplacementContext context = new TemplateReplacementContext();
        context.addAttribute("itemsData", List.of("a", "b"));

        //  Elements without properties degrade to empty values instead of aborting the whole template.
        assertEquals("Value->\nValue->", replaceVariables(context, text));
    }

    @Test
    public void testLoopWithNullElement(){
        String text = "{loop:itemsData:[itemsData.nameId]}";

        TemplateReplacementContext context = new TemplateReplacementContext();
        context.addAttribute("itemsData", Arrays.asList(null, new NamedData(1, "SHORT", "LONG")));

        assertEquals("[]\n[LONG]", replaceVariables(context, text));
    }

    public static class NamedData{
        private int id;
        private String name;
        private String nameId;

        public NamedData(int id, String name, String nameId) {
            this.id = id;
            this.name = name;
            this.nameId = nameId;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getNameId() {
            return nameId;
        }
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
