/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.client.core.edm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.olingo.client.api.edm.xml.v4.BindingTarget;
import org.apache.olingo.client.api.edm.xml.v4.NavigationPropertyBinding;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmBindingTarget;
import org.apache.olingo.commons.api.edm.EdmEntityContainer;
import org.apache.olingo.commons.api.edm.EdmException;
import org.apache.olingo.commons.api.edm.EdmNavigationPropertyBinding;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.Target;
import org.apache.olingo.commons.core.edm.AbstractEdmBindingTarget;
import org.apache.olingo.commons.core.edm.EdmNavigationPropertyBindingImpl;

public abstract class EdmBindingTargetImpl extends AbstractEdmBindingTarget {

  private final BindingTarget target;
  private List<EdmNavigationPropertyBinding> navigationPropertyBindings;

  public EdmBindingTargetImpl(final Edm edm, final EdmEntityContainer container,
      final String name, final FullQualifiedName type, final BindingTarget target) {

    super(edm, container, name, type);
    this.target = target;
  }

  @Override
  public EdmBindingTarget getRelatedBindingTarget(final String path) {
    EdmBindingTarget bindingTarget = null;

    final List<? extends NavigationPropertyBinding> navigationPropertyBindings = target.getNavigationPropertyBindings();
    boolean found = false;
    for (final Iterator<? extends NavigationPropertyBinding> itor = navigationPropertyBindings.iterator(); itor
        .hasNext()
        && !found;) {

      final NavigationPropertyBinding binding = itor.next();
      if (binding.getPath().equals(path)) {
        final Target edmTarget = new Target.Builder(binding.getTarget(), container).build();

        final EdmEntityContainer entityContainer = edm.getEntityContainer(edmTarget.getEntityContainer());
        if (entityContainer == null) {
          throw new EdmException("Cannot find entity container with name: " + edmTarget.getEntityContainer());
        }
        bindingTarget = entityContainer.getEntitySet(edmTarget.getTargetName());
        if (bindingTarget == null) {
          bindingTarget = entityContainer.getSingleton(edmTarget.getTargetName());
          if (bindingTarget == null) {
            throw new EdmException("Cannot find target with name: " + edmTarget.getTargetName());
          }

          found = true;
        } else {
          found = true;
        }
      }
    }

    return bindingTarget;
  }

  @Override
  public List<EdmNavigationPropertyBinding> getNavigationPropertyBindings() {
    if (navigationPropertyBindings == null) {
      List<? extends NavigationPropertyBinding> providerBindings = target.getNavigationPropertyBindings();
      navigationPropertyBindings = new ArrayList<EdmNavigationPropertyBinding>();
      if (providerBindings != null) {
        for (NavigationPropertyBinding binding : providerBindings) {
          navigationPropertyBindings.add(new EdmNavigationPropertyBindingImpl(binding.getPath(), binding.getTarget()));
        }
      }
    }
    return navigationPropertyBindings;
  }

}
