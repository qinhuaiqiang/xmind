/* ******************************************************************************
 * Copyright (c) 2006-2008 XMind Ltd. and others.
 * 
 * This file is a part of XMind 3. XMind releases 3 and
 * above are dual-licensed under the Eclipse Public License (EPL),
 * which is available at http://www.eclipse.org/legal/epl-v10.html
 * and the GNU Lesser General Public License (LGPL), 
 * which is available at http://www.gnu.org/licenses/lgpl.html
 * See http://www.xmind.net/license.html for details.
 * 
 * Contributors:
 *     XMind Ltd. - initial API and implementation
 *******************************************************************************/
package org.xmind.core.io;

import org.xmind.core.CoreException;

/**
 * @author frankshaka
 * 
 */
public interface IStorage {

    /**
     * 
     * @return
     * @throws CoreException
     */
    IInputSource getInputSource() throws CoreException;

    /**
     * 
     * @return
     * @throws CoreException
     */
    IOutputTarget getOutputTarget() throws CoreException;

    /**
     * 
     * @return The name of this archive
     */
    String getName();

    /**
     * @return
     */
    String getFullPath();

    /**
     * 
     */
    void clear();

}