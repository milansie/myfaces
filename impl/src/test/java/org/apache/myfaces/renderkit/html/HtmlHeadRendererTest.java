/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.renderkit.html;

import java.io.StringWriter;

import jakarta.faces.component.html.HtmlHead;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.base.AbstractJsfTestCase;
import org.apache.myfaces.test.mock.MockRenderKitFactory;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;

/**
 * @author Leonardo Uribe
 */
public class HtmlHeadRendererTest extends AbstractJsfTestCase
{

    private MockResponseWriter writer ;
    private HtmlHead head;

    public HtmlHeadRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() {
        return new TestSuite(HtmlHeadRendererTest.class);
    }

    public void setUp() throws Exception
    {
        super.setUp();

        head = new HtmlHead();

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);

        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                head.getFamily(),
                head.getRendererType(),
                new HtmlHeadRenderer());
        
        facesContext.getAttributes().put("org.apache.myfaces.RENDERED_JSF_JS", Boolean.TRUE);
    }

    public void tearDown()throws Exception
    {
        super.tearDown();
        writer = null;
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    { 
        HtmlRenderedAttr[] attrs = {
                new HtmlRenderedAttr("xmlns"),
                new HtmlRenderedAttr("dir"),
                new HtmlRenderedAttr("lang"),
        };

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                head, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
