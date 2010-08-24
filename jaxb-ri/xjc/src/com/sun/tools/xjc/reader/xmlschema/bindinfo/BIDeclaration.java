/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.sun.tools.xjc.reader.xmlschema.bindinfo;

import java.util.Collection;

import javax.xml.namespace.QName;

import com.sun.tools.xjc.reader.xmlschema.BGMBuilder;

import org.xml.sax.Locator;

/**
 * Base interface for all binding customization declarations.
 * 
 * <p>
 * Because of the setParent method, one customization declaration
 * can be attached to one component alone.
 * 
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public interface BIDeclaration {
    
    /**
     * Sets the parent BindInfo object of this declaration.
     * A declaration object can use this pointer to access
     * context information, such as other customizations.
     *
     * <p>
     * This method can be only called from {@link BindInfo},
     * and only once. This is a good opportunity to do some
     * follow-up initialization after JAXB unmarshalling
     * populated {@link BIDeclaration}.
     */
    void setParent( BindInfo parent );
    
    /**
     * Gets the name of this binding declaration,
     * which is the same as the tag name of the binding element.
     */
    QName getName();
    
    /**
     * Gets the source location where this declaration was written.
     * For declarations that are generated by XJC itself,
     * this method returns null.
     */
    Locator getLocation();
    
    /**
     * Marks this declaration to be acknowledged -- either actually
     * used or the existence is admitted (for example when
     * a property customization is given at the point of definition.)
     * 
     * <p>
     * Declarations that are not acknowledged will be considered
     * as an error.
     */
    void markAsAcknowledged();
    
    /**
     * Checks if this declaration was acknowledged.
     */
    boolean isAcknowledged();

    /**
     * Called when the parent {@link BindInfo} got its owner set.
     *
     * This is when declarations are connected to {@link BGMBuilder} and
     * its sibling components.
     */
    void onSetOwner();

    /**
     * Gets child {@link BIDeclaration}s if any.
     *
     * @return
     *      can be empty but always non-null. read-only.
     */
    Collection<BIDeclaration> getChildren();
}

