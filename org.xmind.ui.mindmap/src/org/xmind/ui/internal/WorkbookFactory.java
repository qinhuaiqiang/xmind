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
package org.xmind.ui.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.eclipse.osgi.util.NLS;
import org.xmind.core.Core;
import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.style.IStyle;
import org.xmind.ui.mindmap.MindMapUI;

public class WorkbookFactory {

    public static IWorkbook createEmptyWorkbook() {
        return createEmptyWorkbook(null);
    }

    public static IWorkbook createEmptyWorkbook(String initialPath) {
        IWorkbook workbook = Core.getWorkbookBuilder().createWorkbook(
                initialPath);
        ISheet sheet = workbook.getPrimarySheet();
        sheet.setTitleText(NLS.bind(MindMapMessages.TitleText_Sheet, workbook
                .getSheets().size()));
        ITopic rootTopic = sheet.getRootTopic();
        rootTopic.setTitleText(MindMapMessages.TitleText_CentralTopic);

        IStyle theme = MindMapUI.getResourceManager().getDefaultTheme();
        IStyle importStyle = workbook.getStyleSheet().importStyle(theme);
        if (importStyle != null)
            sheet.setThemeId(importStyle.getId());

        return workbook;
    }

    public static InputStream createEmptyWorkbookStream() {
        IWorkbook workbook = createEmptyWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        try {
            workbook.save(out);
        } catch (Throwable e) {
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

}