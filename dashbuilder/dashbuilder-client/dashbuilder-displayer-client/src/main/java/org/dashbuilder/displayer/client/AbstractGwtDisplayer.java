/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.displayer.client;

import org.uberfire.client.mvp.UberView;

/**
 * Base class for implementing custom displayers where the view is implemented using GWT widgets.
 */
public abstract class AbstractGwtDisplayer<V extends AbstractGwtDisplayer.View> extends AbstractDisplayer<V> {

    public interface View<P extends AbstractGwtDisplayer> extends AbstractDisplayer.View, UberView<P> {

    }
}