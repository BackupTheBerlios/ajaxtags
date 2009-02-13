package net.sourceforge.ajaxtags.tags;

import javax.servlet.jsp.JspException;


public class BaseAjaxTag extends BaseAjaxBodyTag {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void initParameters() throws JspException {
        super.initParameters();
        skipBody();
    }
}
