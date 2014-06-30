<?xml version="1.0" encoding="UTF-8"?>
<!--
    Description:
        Transforms a simple XML document (describing a table of data) into an HTML document.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"
                doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
                doctype-system="http://www.w3.org/TR/html4/loose.dtd"/>
    <xsl:template match="/table">
        <html>
            <head>
                <link rel="shortcut icon" href="data:image/png;base64,
iVBORw0KGgoAAAANSUhEUgAAABAAAAAQBAMAAADt3eJSAAAACXBIWXMAAAsTAAALEwEAmp
wYAAAAElBMVEV/f3/////5oToAo7P/8QAwtFdRtwOOAAAAT0lEQVR4Xj3OsQ2AMBDFUH8I
/d0GUSZAygIUGYAm+6+CTl/C1StNBoCSYPQ5ETEKU2DAYdwFMLriNDJxmW09XPtVGBuWsV
sBA8IQQQH9Gx/7kA+1nVSxTwAAAABJRU5ErkJggg==" />
                <title><xsl:value-of select="/table/@identifier"/></title>
                <style type="text/css">
                    body {
                        font-family: sans-serif;
                        font-size: 11pt;
                    }
                    table {
                        border-collapse: collapse;
                        counter-reset: rownumber;
                    }
                    span.rowNumber:before {
                        counter-increment:rownumber;
                        content:counter(rownumber) ".";
                    }
                    th {
                        background-color: #cacaca;
                    }
                    td, th {
                        border: 1px solid #999;
                        padding: 2px 4px;
                    }
                    th.header {
                        background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAALCAMAAACah1cpAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAAZQTFRFNDQ0////Edqr1gAAAAJ0Uk5T/wDltzBKAAAAKklEQVR42mJghAEGFIKBgQHCYmAAMxkgDCCTAUkHkhhcHZJeZAIMAAIMAA7TAERTuPPmAAAAAElFTkSuQmCC);
                        background-repeat: no-repeat;
                        background-position: center right 2px;
                        cursor: pointer;
                        padding: 2px 13px 2px 4px;
                    }
                    th.headerSortUp {
                        background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAALCAMAAACah1cpAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAAlQTFRFNDQ0gICA////76SQuwAAAAN0Uk5T//8A18oNQQAAADJJREFUeNpMy0kOAEAIAkF6/v9oR4hRDqTiojdRFyMgAlPBp86Hkp4F3hq523JKAAEGACHUAJfFdxBFAAAAAElFTkSuQmCC);
                    }
                    th.headerSortDown {
                        background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAALCAMAAACah1cpAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAAlQTFRFNDQ0gICA////76SQuwAAAAN0Uk5T//8A18oNQQAAADFJREFUeNpiYIIBBhSCgYEBwmJgADMZIAwgkwFJByMEgMQgDLAsmAFRxwg3FAwAAgwAH3wAl1zU290AAAAASUVORK5CYII=);
                    }

                    tr:nth-child(odd) {
                        background-color: #e0e0e0;
                    }
                    tr:nth-child(even) {
                        background-color: #ffffff;
                    }

                    td.traditional {
                        background-color: #e0eee0 !important;
                    }
                    td.multi {
                        background-color: #eeeee0 !important;
                    }
                    td.mystery {
                        background-color: #e0e0ee !important;
                    }
                </style>
                <script type="text/javascript" src="jquery-1.9.1.min.js"></script>
                <script type="text/javascript" src="jquery.tablesorter.min.js"></script>
                <script type="text/javascript" src="jquery-ui.min.js"></script>
                <script type="text/javascript">
                <xsl:if test="boolean(/table/@identifier = 'caches')">
                $.tablesorter.addParser({
                    id: 'cacheSizes',
                    is: function(s) {
                       return false;
                    },
                    format: function(s) {
                        var size = s.toLowerCase();
                        size = size.replace(/micro/,0);
                        size = size.replace(/small/,1);
                        size = size.replace(/regular/,2);
                        size = size.replace(/large/,3);
                        size = size.replace(/virtual/,4);
                        size = size.replace(/other/,5);
                        size = size.replace(/not chosen/,6);
                        return size;
                    },
                    type: 'numeric'
                });
                </xsl:if>
                $(document).ready(function() {
                    $("table").tablesorter({
                        headers: {
                            0: { sorter: false },
                        <xsl:if test="boolean(/table/@identifier = 'caches')">
                            6: {
                                sorter:'cacheSizes'
                            }
                        </xsl:if>
                        }
                    });
                    colorCacheTypes();
                    $("tbody").sortable({axis: "y"});
                });
                function colorCacheTypes() {
                    $("table#caches td:nth-child(2):contains('Mystery')").addClass('mystery');
                    $("table#caches td:nth-child(2):contains('Traditional')").addClass('traditional');
                    $("table#caches td:nth-child(2):contains('Multi')").addClass('multi');
                }
                </script>
            </head>
            <body>
                <table id="{@identifier}">
                    <thead>
                        <xsl:apply-templates select="row[@header='true']" />
                    </thead>
                    <tbody>
                        <xsl:apply-templates select="row[@header='false']" />
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="row">
        <tr>
            <xsl:choose>
                <xsl:when test="@header='true'">
                    <th>#</th>
                </xsl:when>
                <xsl:otherwise>
                    <td><span class="rowNumber"></span></td>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:apply-templates select="cell" />
        </tr>
    </xsl:template>

    <xsl:template match="cell">
        <xsl:choose>
            <xsl:when test="@url">
                <td><a href="{@url}" target="_blank"><xsl:value-of select="." /></a></td>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="../@header='true'">
                        <th><xsl:value-of select="." /></th>
                    </xsl:when>
                    <xsl:otherwise>
                        <td><xsl:value-of select="." /></td>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
