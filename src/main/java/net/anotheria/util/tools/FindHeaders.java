package net.anotheria.util.tools;

import net.anotheria.util.IOUtils;

import java.io.File;
import java.io.IOException;

/**
 * <p>FindHeaders class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public class FindHeaders {
    /**
     * <p>main.</p>
     *
     * @param a a {@link java.lang.String} object.
     */
    public static void main(String... a) {
        Walker w = new Walker(new Worker() {
            private int counter = 0;

            @Override
            public void processFile(File file) {
                if (!file.getName().endsWith(".java"))
                    return;
                if (file.getAbsoluteFile().toString().contains("/gen/"))
                    return;
                try {
                    String content = IOUtils.readFileAtOnceAsString(file);
                    content = content.trim();
                    if (content.indexOf("package") > 0) {
                        counter++;
                        System.out.println("Found header in " + file.getAbsoluteFile() + " (" + counter + ')');
                    }
                } catch (IOException e) {
                   // ignore this exception
                }
            }
        });
        w.start();
    }
}

