package io.github.zeroone3010.geogpxparser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Iterator;

public class IterableSubElements implements Iterable<Element> {

    private final Element mainElement;

    public IterableSubElements(final Element mainElement) {
        this.mainElement = mainElement;
    }

    @Override
    public Iterator<Element> iterator() {
        return new SubElementIterator(mainElement);
    }

    private static class SubElementIterator implements Iterator<Element> {

        private final Element mainElement;
        private Node current;

        public SubElementIterator(final Element mainElement) {
            this.mainElement = mainElement;
            this.current = mainElement != null ? this.mainElement.getFirstChild() : null;
        }

        @Override
        public boolean hasNext() {
            while (current != null) {
                if (current instanceof Element) {
                    return true;
                }
                current = current.getNextSibling();
            }
            return false;
        }

        @Override
        public Element next() {
            Element returnValue = (Element) current;
            current = current.getNextSibling();
            return returnValue;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
