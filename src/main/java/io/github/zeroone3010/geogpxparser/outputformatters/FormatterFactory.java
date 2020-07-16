package io.github.zeroone3010.geogpxparser.outputformatters;

import io.github.zeroone3010.geogpxparser.tabular.TableData;

public final class FormatterFactory {
    private FormatterFactory() { /* prevent */}

    public static AbstractTabularDataFormatter createFormatter(final TableData tableData, final String outputType) {
        final AbstractTabularDataFormatter result;
        switch (outputType) {
            case "xml":
                result = new XmlFormatter(tableData);
                break;
            case "html":
                result = new HtmlFormatter(tableData);
                break;
            default:
                result = new TabSeparatedValuesFormatter(tableData);
                break;
        }
        return result;
    }
}
