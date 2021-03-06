/**
 * Automated Exploratory Tests
 *
 * Copyright (C) 2013 Cognifide Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cognifide.aet.job.common.collectors.accessibility;

import com.cognifide.aet.job.api.collector.CollectorFactory;
import com.cognifide.aet.job.api.collector.CollectorJob;
import com.cognifide.aet.job.api.collector.CollectorProperties;
import com.cognifide.aet.job.api.collector.WebCommunicationWrapper;
import com.cognifide.aet.job.api.exceptions.ParametersException;
import com.cognifide.aet.vs.ArtifactsDAO;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.BundleContext;

import java.util.Map;

@Component
@Service
public class AccessibilityCollectorFactory implements CollectorFactory {

  @Reference
  private ArtifactsDAO artifactsDAO;

  private BundleContext context;

  @Override
  public String getName() {
    return AccessibilityCollector.NAME;
  }

  @Override
  public CollectorJob createInstance(CollectorProperties properties, Map<String, String> parameters, WebCommunicationWrapper webCommunicationWrapper) throws ParametersException {
    AccessibilityCollector collector = new AccessibilityCollector(artifactsDAO, properties,
            webCommunicationWrapper.getWebDriver(), context);
    collector.setParameters(parameters);
    return collector;
  }

  @Activate
  public void activate(BundleContext context) {
    this.context = context;
  }
}
