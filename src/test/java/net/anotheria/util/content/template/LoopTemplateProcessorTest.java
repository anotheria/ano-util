package net.anotheria.util.content.template;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static net.anotheria.util.content.template.TemplateUtility.replaceVariables;

/**
 * LoopTemplateProcessorTest — TODO.
 *
 * @author ykalapusha
 * @since 24.09.2025
 */
public class LoopTemplateProcessorTest {

    @Test
    public void testLoop(){
        List<ItemData>  list = new ArrayList<ItemData>();
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


    private static class ItemData{
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
    }
}
