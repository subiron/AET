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
package com.cognifide.aet.xmlparser.xml;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import com.cognifide.aet.model.ComparatorStep;
import com.cognifide.aet.model.DataModifierStep;
import com.cognifide.aet.xmlparser.xml.models.Compare;

import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompareConverter extends BasicPhaseConverter<Compare> {

  @Override
  public Compare read(InputNode node) throws Exception {
    Set<ComparatorStep> comparatorSteps = Sets.newHashSet();
    InputNode inputNode;
    while ((inputNode = node.getNext()) != null) {
      Map<String, String> parameters = getParameters(inputNode);

      String comparator = parameters.get("comparator");
      String name = parameters.get("name");
      String collectorName = parameters.get("collectorName");

      List<DataModifierStep> dataModifierSteps = Lists.newArrayList();
      InputNode childNode;
      while ((childNode = inputNode.getNext()) != null) {
        Map<String, String> dataModifierParameters = getParameters(childNode);
        dataModifierSteps.add(new DataModifierStep(childNode.getName(), dataModifierParameters));
      }

      ComparatorStep comparatorStep = new ComparatorStep(comparator, inputNode.getName(), name,
              collectorName, parameters, dataModifierSteps);
      comparatorSteps.add(comparatorStep);
    }
    return new Compare(comparatorSteps);
  }

  @Override
  public void write(OutputNode node, Compare value) throws Exception {
    // no write capability needed
  }

}
