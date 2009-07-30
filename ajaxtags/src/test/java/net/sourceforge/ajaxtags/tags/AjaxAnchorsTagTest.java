/**
 *
 */
package net.sourceforge.ajaxtags.tags;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.xml.transform.TransformerException;

import net.sourceforge.ajaxtags.FakeBodyContent;
import net.sourceforge.ajaxtags.FakePageContext;
import net.sourceforge.ajaxtags.helpers.XMLUtils;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author В.Хомяков
 * 
 */
public class AjaxAnchorsTagTest {

    private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + IOUtils.LINE_SEPARATOR;

    private AjaxAnchorsTag tag;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        tag = new AjaxAnchorsTag();
        tag.setBodyContent(new FakeBodyContent());
        tag.setPageContext(new FakePageContext());
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        tag.release();
    }

    /**
     * Test method for {@link AjaxAnchorsTag#doEndTag()}.
     * 
     * @throws JspException
     *             on errors
     * @throws IOException
     *             on BodyContent errors
     * @throws SAXException
     * @throws TransformerException
     */
    @Test
    public void testDoEndTag() throws JspException, IOException, TransformerException, SAXException {
        final OptionsBuilder options = OptionsBuilder.getOptionsBuilder();
        final PageContext context = new FakePageContext();
        tag.setPageContext(context);
        // tag.setVar("ajaxAnchors");
        tag.setTarget("target");

        assertEquals("doStartTag() must return BodyTag.EVAL_BODY_BUFFERED",
                BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());

        final String baseURL = "http://localhost:8080/test/test.do";

        final String html = "<a href=\"" + baseURL + "\">test</a>";
        final String expected = HEADER
                + "<div>"
                // + IOUtils.LINE_SEPARATOR
                + "<a href=\"javascript://nop/\" " + "onclick=\"new AjaxJspTag.OnClick({"
                + "baseUrl: &quot;" + baseURL + "&quot;" + options.getOptionsDelimiter()
                + "eventBase: this" + options.getOptionsDelimiter()
                + "requestHeaders: ['x-request-target' , 'target']" + options.getOptionsDelimiter()
                + "target: &quot;target&quot;" + "});" + " return false;\">test</a>"
                + IOUtils.LINE_SEPARATOR + "</div>" + IOUtils.LINE_SEPARATOR;

        tag.getBodyContent().print(html);

        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());
        assertEquals("doEndTag() must return BodyTag.EVAL_PAGE", BodyTag.EVAL_PAGE, tag.doEndTag());
        final String content = ((FakeBodyContent) context.getOut()).getString();
        assertEquals("HTML with link", XMLUtils.format(expected), content);
    }

    /**
     * Test method for {@link AjaxAnchorsTag#ajaxAnchors(String, String, String)}.
     * 
     * @throws JspException
     *             on errors
     */
    @Test
    public void testAjaxAnchors() throws JspException {
        final OptionsBuilder options = OptionsBuilder.getOptionsBuilder();
        final String baseURL = "http://localhost:8080/test/test.do";

        String html = "HTML content";
        String expected = HEADER + "<div>HTML content</div>" + IOUtils.LINE_SEPARATOR;
        assertEquals("HTML w/o links", expected, tag.ajaxAnchors(html, "target", null));

        html = "html <a>link</a>";
        expected = HEADER + "<div>html <a>link</a>" + IOUtils.LINE_SEPARATOR + "</div>"
                + IOUtils.LINE_SEPARATOR;
        assertEquals("HTML with empty link", expected, tag.ajaxAnchors(html, "target", null));

        html = "html <a href=\"" + baseURL + "\">test</a>";
        expected = HEADER + "<div>html <a href=\"javascript://nop/\" "
                + "onclick=\"new AjaxJspTag.OnClick({" + "baseUrl: &quot;" + baseURL + "&quot;"
                + options.getOptionsDelimiter() + "eventBase: this" + options.getOptionsDelimiter()
                + "requestHeaders: ['x-request-target' , 'target']" + options.getOptionsDelimiter()
                + "target: &quot;target&quot;" + "});" + " return false;\">test</a>"
                + IOUtils.LINE_SEPARATOR + "</div>" + IOUtils.LINE_SEPARATOR;
        assertEquals("HTML with link", expected, tag.ajaxAnchors(html, "target", null));
    }
}
