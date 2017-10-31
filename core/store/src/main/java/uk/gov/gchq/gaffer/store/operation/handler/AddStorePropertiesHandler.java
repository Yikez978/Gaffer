/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.gaffer.store.operation.handler;

import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.store.Context;
import uk.gov.gchq.gaffer.store.Store;
import uk.gov.gchq.gaffer.store.StoreProperties;
import uk.gov.gchq.gaffer.store.library.GraphLibrary;
import uk.gov.gchq.gaffer.store.operation.add.AddStoreProperties;

import static uk.gov.gchq.gaffer.store.library.GraphLibrary.resolveStoreProperties;

public class AddStorePropertiesHandler implements OperationHandler<AddStoreProperties> {

    public static final String ERROR_ADDING_STORE_TO_STORE_S = "Error adding storeProperties to Store.%s";
    public static final String THE_STORE_DOES_NOT_HAVE_A_GRAPH_LIBRARY = " the store doesn't have a graphLibrary";

    @Override
    public Void doOperation(final AddStoreProperties operation, final Context context, final Store store) throws OperationException {
        GraphLibrary graphLibrary = store.getGraphLibrary();
        if (null == graphLibrary) {
            throw new OperationException(String.format(ERROR_ADDING_STORE_TO_STORE_S, THE_STORE_DOES_NOT_HAVE_A_GRAPH_LIBRARY));
        } else {
            StoreProperties properties;
            try {
                properties = resolveStoreProperties(store, operation.getStoreProperties(), operation.getParentPropertiesId());
            } catch (final Exception e) {
                throw new OperationException(String.format(ERROR_ADDING_STORE_TO_STORE_S, " storeProperties couldn't be resolved."), e);
            }
            try {
                graphLibrary.addProperties(properties);
            } catch (final Exception e) {
                throw new OperationException(ERROR_ADDING_STORE_TO_STORE_S, e);
            }
        }
        return null;
    }
}