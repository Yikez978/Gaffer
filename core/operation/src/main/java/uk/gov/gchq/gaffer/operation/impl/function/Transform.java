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
package uk.gov.gchq.gaffer.operation.impl.function;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.exception.CloneFailedException;

import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.function.ElementTransformer;
import uk.gov.gchq.gaffer.operation.Operation;
import uk.gov.gchq.gaffer.operation.io.InputOutput;
import uk.gov.gchq.gaffer.operation.io.MultiInput;
import uk.gov.gchq.gaffer.operation.serialisation.TypeReferenceImpl.IterableElement;

import java.util.Map;

/**
 * A <code>Transform</code> operation applies a provided {@link ElementTransformer} to the provided {@link Iterable} of {@link Element}s,
 * and returns an {@link Iterable}.
 */
public class Transform implements
        Operation,
        InputOutput<Iterable<? extends Element>, Iterable<? extends Element>>,
        MultiInput<Element> {

    private Iterable<? extends Element> input;
    private Map<String, String> options;

    public ElementTransformer getElementTransformer() {
        return elementTransformer;
    }

    private ElementTransformer elementTransformer;

    @Override
    public Iterable<? extends Element> getInput() {
        return input;
    }

    @Override
    public void setInput(final Iterable<? extends Element> input) {
        this.input = input;
    }

    @Override
    public TypeReference<Iterable<? extends Element>> getOutputTypeReference() {
        return new IterableElement();
    }

    @Override
    public Operation shallowClone() throws CloneFailedException {
        return new Transform.Builder()
                .input(input)
                .elementTransformer(elementTransformer)
                .options(options)
                .build();
    }

    @Override
    public Map<String, String> getOptions() {
        return options;
    }

    @Override
    public void setOptions(final Map<String, String> options) {
        this.options = options;
    }

    public void setElementTransformer(final ElementTransformer elementTransformer) {
        this.elementTransformer = elementTransformer;
    }

    public static final class Builder
            extends Operation.BaseBuilder<Transform, Builder>
            implements InputOutput.Builder<Transform, Iterable<? extends Element>, Iterable<? extends Element>, Builder>,
            MultiInput.Builder<Transform, Element, Builder> {
        public Builder() {
            super(new Transform());
        }

        public Builder elementTransformer(final ElementTransformer elementTransformer) {
            _getOp().setElementTransformer(elementTransformer);
            return _self();
        }
    }
}
