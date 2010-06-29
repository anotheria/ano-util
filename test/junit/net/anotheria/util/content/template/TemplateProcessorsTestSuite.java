package net.anotheria.util.content.template;

import net.anotheria.util.content.template.processors.ConditionTemplateProcessorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite for TemplateProcessors.
 *
 * @author h3llka
 */
@RunWith(value = Suite.class)
@Suite.SuiteClasses(value = {ConditionTemplateProcessorTest.class,TemplateUtilityTest.class})
public class TemplateProcessorsTestSuite {
}
