package zeroone3010.geogpxparser;

import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class IterableSubElements implements Iterable<Element> {

    private final Element mainElement;

    public IterableSubElements(Element mainElement) {
        this.mainElement = mainElement;
    }

    @Override
    public Iterator<Element> iterator() {
        return new SubElementIterator(mainElement);
    }

    private static class SubElementIterator implements Iterator<Element> {

        private final Element mainElement;
        private Node current;

        public SubElementIterator(Element mainElement) {
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
