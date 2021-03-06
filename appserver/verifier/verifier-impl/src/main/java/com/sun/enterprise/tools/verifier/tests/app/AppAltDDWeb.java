/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.enterprise.tools.verifier.tests.app;

import java.io.*;
import java.util.*;
import com.sun.enterprise.deployment.*;
import com.sun.enterprise.tools.verifier.*;
import com.sun.enterprise.deploy.shared.FileArchive;

/**
 * The alt-dd element specifies a URI to the post-assembly deployment descriptor
 * relative to the root of the application 
 */

public class AppAltDDWeb extends ApplicationTest implements AppCheck { 


    /** 
     * The alt-dd element specifies a URI to the post-assembly deployment descriptor
     * relative to the root of the application 
     *
     * @param descriptor the Application deployment descriptor
     *   
     * @return <code>Result</code> the results for this assertion
     */
    public Result check(Application descriptor) {

	Result result = getInitializedResult();

 
	if (descriptor.getBundleDescriptors(WebBundleDescriptor.class).size() > 0) {
	    boolean oneFailed = false;
            int na = 0;
	    for (Iterator itr = descriptor.getBundleDescriptors(WebBundleDescriptor.class).iterator(); itr.hasNext();) {
		WebBundleDescriptor wbd = (WebBundleDescriptor) itr.next();

		if (wbd.getModuleDescriptor().getAlternateDescriptor()!=null) {
		    if (!(wbd.getModuleDescriptor().getAlternateDescriptor().equals(""))) {
                        InputStream deploymentEntry=null;
//                        File f = null;
//                        if (Verifier.getEarFile() != null)
//                            f = new File(Verifier.getEarFile());
                        
			try {
//                            if (f==null){
                                String uri = getAbstractArchiveUri(descriptor);
//                                try {
                                    FileArchive arch = new FileArchive();
                                    arch.open(uri);
                                    deploymentEntry = arch.getEntry(wbd.getModuleDescriptor().getAlternateDescriptor());
//                                }catch (Exception e) { }
//                            }else{
//
//                                jarFile = new JarFile(f);
//                                ZipEntry deploymentEntry1 = jarFile.getEntry(wbd.getModuleDescriptor().getAlternateDescriptor());
//                                if (deploymentEntry1 != null)
//                                    deploymentEntry = jarFile.getInputStream(deploymentEntry1);
//                            }

			    if (deploymentEntry != null) {
				result.addGoodDetails(smh.getLocalString
						      (getClass().getName() + ".passed",
						       "Found alternate web deployment descriptor URI file [ {0} ] within [ {1} ]",
						       new Object[] {wbd.getModuleDescriptor().getAlternateDescriptor(),wbd.getName()}));
			    } else { 
                                if (!oneFailed) {
                                    oneFailed = true;
                                }
				result.addErrorDetails(smh.getLocalString
						       (getClass().getName() + ".failed",
							"Error: No alternate web deployment descriptor URI file found, looking for [{0} ] within [ {1} ]",
							new Object[] {wbd.getModuleDescriptor().getAlternateDescriptor(), wbd.getName()}));
			    }
			    //jarFile.close();
        
			} catch (FileNotFoundException ex) {
			    Verifier.debug(ex);
                            if (!oneFailed) {
                                oneFailed = true;
                            }
			    
		result.failed(smh.getLocalString
					  (getClass().getName() + ".failedException",
					   "Error: File not found trying to read deployment descriptor file [ {0} ] within [ {1} ]",
					   new Object[] {wbd.getModuleDescriptor().getAlternateDescriptor(), wbd.getName()}));
			} catch (IOException ex) {
			    Verifier.debug(ex);
                            if (!oneFailed) {
                                oneFailed = true;
                            }
			    
		result.failed(smh.getLocalString
					  (getClass().getName() + ".failedException1",
					   " Error: IO Error trying to read deployment descriptor file [ {0} ] within [ {1} ]",
					   new Object[] {wbd.getModuleDescriptor().getAlternateDescriptor(), wbd.getName()}));
	                } finally {
                            try {
                                if (deploymentEntry != null)
                                    deploymentEntry.close();
                            } catch (Exception x) {}
                        }

		    }
		} else {
                    na++;
		    result.notApplicable(smh.getLocalString
					 (getClass().getName() + ".notApplicable1",
					  "There is no java web alternative deployment descriptor in [ {0} ]",
					  new Object[] {wbd.getName()}));
		}
	    }
            if (oneFailed) {
                result.setStatus(Result.FAILED);
            } else if (na == descriptor.getBundleDescriptors(WebBundleDescriptor.class).size()) {
                result.setStatus(Result.NOT_APPLICABLE);
            } else {
                result.setStatus(Result.PASSED);
            }

	} else {
	    result.notApplicable(smh.getLocalString
				 (getClass().getName() + ".notApplicable",
				  "There are no web components in application [ {0} ]",
				  new Object[] {descriptor.getName()}));
	}

 
	return result;
    }
}
